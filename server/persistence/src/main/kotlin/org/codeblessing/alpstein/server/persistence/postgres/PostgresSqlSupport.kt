package org.codeblessing.alpstein.server.persistence.postgres

import org.springframework.jdbc.core.SqlParameterValue
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jacksonObjectMapper
import tools.jackson.module.kotlin.readValue
import java.sql.ResultSet
import java.sql.Types
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

internal val jsonbObjectMapper: ObjectMapper = jacksonObjectMapper()

internal fun toJsonb(value: Any?): String? = value?.let { jsonbObjectMapper.writeValueAsString(it) }

internal inline fun <reified T : Any> ResultSet.getJsonb(columnLabel: String): T =
    jsonbObjectMapper.readValue<T>(getString(columnLabel))

internal inline fun <reified T : Any> ResultSet.getJsonbOrNull(columnLabel: String): T? =
    getString(columnLabel)?.let { jsonbObjectMapper.readValue<T>(it) }

/**
 * Reads one column as the type the caller expects it in, so that every generated row mapper
 * line looks the same regardless of the column's type or nullability.
 *
 * [T] is taken from the business object's constructor parameter, and it is the same class for
 * `String` and `String?`, therefore nullability needs no case of its own: a SQL NULL in a
 * column the business object declares non-null fails on the cast, which is what should happen.
 * A nested item has no flat relational representation and is therefore stored as `jsonb`, and
 * so is a list of nested items; every other list is a SQL array of its element type.
 *
 * An enum value is read through the database spelling of its values, so that a column holding
 * `PG-13` becomes `MpaaRating.P_G13`.
 *
 * A `Double` is read as a `BigDecimal` rather than with `getObject(_, Double::class)`, because
 * the driver refuses that conversion for a `numeric` column ("conversion to class
 * java.lang.Double from numeric not supported") — and `numeric` is exactly what an existing
 * schema spells a decimal column as. `getBigDecimal` accepts every numeric column type and
 * returns `null` for a SQL NULL.
 */
@Suppress("UNCHECKED_CAST")
internal inline fun <reified T> ResultSet.columnValue(columnLabel: String): T {
    // resolving the full KType is only worth its cost for the one class that can be an array
    val arrayElementClass = if (T::class == List::class) sqlArrayElementClass(typeOf<T>()) else null

    return when {
        T::class == UUID::class -> getObject(columnLabel, UUID::class.java)
        T::class == String::class -> getString(columnLabel)
        T::class == Int::class -> getObject(columnLabel, Int::class.javaObjectType)
        T::class == Double::class -> getBigDecimal(columnLabel)?.toDouble()
        T::class == Boolean::class -> getObject(columnLabel, Boolean::class.javaObjectType)
        T::class.java.isEnum -> getString(columnLabel)?.let { enumValueOf(T::class.java, it) }
        arrayElementClass != null -> arrayColumnValue(columnLabel, arrayElementClass)
        // reading it through the reified T keeps the full generic type, e.g. List<ArticulusInteriorBO>
        else -> getString(columnLabel)?.let { jsonbObjectMapper.readValue<T>(it) }
    } as T
}

/**
 * The counterpart of [columnValue]: turns a business object value into the JDBC parameter it is
 * stored as.
 *
 * [T] is reified because an *empty* list reveals nothing about its elements at runtime, while
 * the empty text of a `jsonb` column (`[]`) and that of an array column (`{}`) differ.
 *
 * An enum value is passed as its database spelling with an unspecified SQL type, which lets
 * PostgreSQL take the type from the column it is written to: a SQL enum type rejects a parameter
 * sent as a string ("column is of type mpaa_rating but expression is of type character varying"),
 * while a `text` column accepts the very same parameter.
 */
internal inline fun <reified T> paramValue(value: T): Any? = when (value) {
    null, is String, is Int, is Double, is Boolean, is UUID -> value
    is Enum<*> -> SqlParameterValue(Types.OTHER, databaseValueOf(value))
    is List<*> -> if (sqlArrayElementClass(typeOf<T>()) != null) toSqlArrayLiteral(value) else toJsonb(value)
    else -> toJsonb(value)
}

private val sqlArrayElementClasses: Set<KClass<*>> =
    setOf(String::class, Int::class, Double::class, Boolean::class, UUID::class)

/**
 * The class the elements of [type] are stored as in a SQL array, or `null` if [type] is not a
 * list of values with a flat relational representation. This is the single place on the Kotlin
 * side that decides what becomes an array column rather than a `jsonb` one, and it has to agree
 * with `DbSqlType`/`DbColumnModel.isArray` of the code generator, which decides the same for the
 * DDL. A divergence is invisible to the compiler and shows up as a malformed array literal.
 *
 * Only the classifiers are compared, never the [KType]s: `List<String>` and `List<String>?` are
 * different types but the same column.
 */
internal fun sqlArrayElementClass(type: KType): KClass<*>? {
    if (type.classifier != List::class) {
        return null
    }
    val elementClass = type.arguments.singleOrNull()?.type?.classifier as? KClass<*> ?: return null
    return elementClass.takeIf { it in sqlArrayElementClasses || it.java.isEnum }
}

/**
 * The PostgreSQL array literal of [list], e.g. `{"Trailers","Commentaries"}`. It is a plain
 * string and relies on the generated INSERT casting it to the column's type, exactly the way
 * [toJsonb] relies on `CAST(... AS jsonb)`. Quoting every element keeps one form for all types
 * and makes a value containing a comma, a brace or the word `NULL` harmless.
 */
internal fun toSqlArrayLiteral(list: List<*>): String = list.joinToString(",", "{", "}") { element ->
    when (element) {
        null -> "NULL"
        is Enum<*> -> quoteArrayElement(databaseValueOf(element))
        else -> quoteArrayElement(element.toString())
    }
}

private fun quoteArrayElement(value: String): String =
    "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""

/**
 * Reads an array column as the list of [elementClass] values it holds. The driver hands out the
 * elements already typed for the column types this project generates, but an array of an enum
 * type arrives as strings, and a `numeric[]` column as `BigDecimal`s.
 */
internal fun ResultSet.arrayColumnValue(columnLabel: String, elementClass: KClass<*>): List<Any?>? =
    getArray(columnLabel)?.let { array ->
        (array.array as Array<*>).map { element -> arrayElementValue(element, elementClass) }
    }

private fun arrayElementValue(element: Any?, elementClass: KClass<*>): Any? = when {
    element == null -> null
    elementClass.java.isEnum -> enumValueOf(elementClass.java, element.toString())
    elementClass == UUID::class && element !is UUID -> UUID.fromString(element.toString())
    elementClass == Double::class && element is Number -> element.toDouble()
    elementClass == Int::class && element is Number -> element.toInt()
    else -> element
}

private val databaseEnumByEnumClass: Map<Class<*>, DatabaseEnum> =
    (DATABASE_ENUMS + GENERATED_DATABASE_ENUMS).associateBy { it.enumClass }

private val databaseValueByEnumValue: Map<Enum<*>, String> = databaseEnumByEnumClass.values
    .flatMap { it.databaseValueByEnumValue.entries }
    .associate { (enumValue, databaseValue) -> enumValue to databaseValue }

/**
 * How [enumValue] is spelled in the database. An enum that is declared nowhere — a test fixture,
 * as every enum of the model is generated into `GENERATED_DATABASE_ENUMS` — is stored as its name.
 * It is internal, not private, because the inline [paramValue] calls it.
 */
internal fun databaseValueOf(enumValue: Enum<*>): String = databaseValueByEnumValue[enumValue] ?: enumValue.name

/**
 * The counterpart of [databaseValueOf]: the value of [enumClass] that is stored as [databaseValue].
 * It is internal, not private, because the inline [columnValue] calls it.
 */
internal fun enumValueOf(enumClass: Class<*>, databaseValue: String): Enum<*> {
    val databaseEnum = databaseEnumByEnumClass[enumClass]
        ?: return enumClass.enumConstants.map { it as Enum<*> }.single { it.name == databaseValue }

    return databaseEnum.enumValueByDatabaseValue[databaseValue]
        ?: error(
            "The value '$databaseValue' is stored for no value of the enum '${enumClass.simpleName}'. " +
                    "Stored are ${databaseEnum.enumValueByDatabaseValue.keys}."
        )
}

/**
 * Turns free text into the literal part of an `ILIKE` pattern, so that a query containing
 * `%`, `_` or the escape character itself matches those characters instead of acting as a wildcard.
 */
internal fun String.escapeForLikePattern(): String =
    replace("""\""", """\\""")
        .replace("%", """\%""")
        .replace("_", """\_""")
