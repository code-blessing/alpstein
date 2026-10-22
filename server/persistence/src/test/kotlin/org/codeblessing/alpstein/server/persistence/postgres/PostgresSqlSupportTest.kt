package org.codeblessing.alpstein.server.persistence.postgres

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.jdbc.core.SqlParameterValue
import org.codeblessing.alpstein.server.service.bo.AppellatioComis
import java.lang.reflect.Proxy
import java.math.BigDecimal
import java.sql.ResultSet
import java.sql.Types
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

private enum class Salutation { MR, MS }

private data class Address(val street: String, val town: String)

/**
 * A [ResultSet] backed by a map, built through a proxy because only the few getters
 * [columnValue] uses have to answer and [ResultSet] declares far too many methods to implement.
 *
 * A column holding an array is written as the element array the driver would hand out.
 */
private fun resultSetOf(vararg columns: Pair<String, Any?>): ResultSet {
    val values = columns.toMap()
    return Proxy.newProxyInstance(
        ResultSet::class.java.classLoader,
        arrayOf(ResultSet::class.java),
    ) { _, method, args ->
        val columnLabel = args[0] as String
        require(values.containsKey(columnLabel)) { "No column '$columnLabel' in the result set." }
        when (method.name) {
            "getString" -> values[columnLabel]
            "getObject" -> values[columnLabel]
            "getArray" -> (values[columnLabel] as Array<*>?)?.let { sqlArrayOf(it) }
            else -> throw UnsupportedOperationException(method.name)
        }
    } as ResultSet
}

private fun sqlArrayOf(elements: Array<*>): java.sql.Array = Proxy.newProxyInstance(
    java.sql.Array::class.java.classLoader,
    arrayOf(java.sql.Array::class.java),
) { _, method, _ ->
    when (method.name) {
        "getArray" -> elements
        else -> throw UnsupportedOperationException(method.name)
    }
} as java.sql.Array

class ColumnValueTest {

    @Test
    fun `reads the scalar types a column can be declared with`() {
        val id = UUID.randomUUID()
        val resultSet = resultSetOf(
            "ID" to id,
            "NAME" to "Ada",
            "AGE" to 36,
            "VEGETARIAN" to true,
        )

        assertEquals(id, resultSet.columnValue<UUID>("ID"))
        assertEquals("Ada", resultSet.columnValue<String>("NAME"))
        assertEquals(36, resultSet.columnValue<Int>("AGE"))
        assertEquals(true, resultSet.columnValue<Boolean>("VEGETARIAN"))
    }

    @Test
    fun `reads a null of every nullable scalar type`() {
        val resultSet = resultSetOf("ID" to null, "NAME" to null, "AGE" to null, "VEGETARIAN" to null)

        assertNull(resultSet.columnValue<UUID?>("ID"))
        assertNull(resultSet.columnValue<String?>("NAME"))
        assertNull(resultSet.columnValue<Int?>("AGE"))
        assertNull(resultSet.columnValue<Boolean?>("VEGETARIAN"))
    }

    @Test
    fun `reads an enum from its name`() {
        assertEquals(Salutation.MS, resultSetOf("SALUTATION" to "MS").columnValue<Salutation>("SALUTATION"))
        assertNull(resultSetOf("SALUTATION" to null).columnValue<Salutation?>("SALUTATION"))
    }

    @Test
    fun `reads an enum from the database spelling declared for it`() {
        val resultSet = resultSetOf("APPELLATIO" to "vir-honoratus")

        assertEquals(AppellatioComis.VIR_HONORATUS, resultSet.columnValue<AppellatioComis>("APPELLATIO"))
    }

    @Test
    fun `refuses a column value that is the spelling of no enum value`() {
        val resultSet = resultSetOf("APPELLATIO" to "VIR_HONORATUS")

        val exception = assertThrows<IllegalStateException> { resultSet.columnValue<AppellatioComis>("APPELLATIO") }

        assertTrue(
            exception.message.orEmpty().contains("is stored for no value of the enum 'AppellatioComis'"),
            "Unexpected message '${exception.message}'",
        )
    }

    @Test
    fun `reads a nested item from jsonb`() {
        val resultSet = resultSetOf("HOME_ADDRESS" to """{"street":"Bahnhofstrasse 1","town":"Zurich"}""")

        assertEquals(Address("Bahnhofstrasse 1", "Zurich"), resultSet.columnValue<Address>("HOME_ADDRESS"))
        assertNull(resultSetOf("HOME_ADDRESS" to null).columnValue<Address?>("HOME_ADDRESS"))
    }

    @Test
    fun `keeps the element type of a list read from jsonb`() {
        val resultSet = resultSetOf("ADDRESSES" to """[{"street":"Bahnhofstrasse 1","town":"Zurich"}]""")

        // the element type survives only because T is reified all the way into the object mapper
        assertEquals(listOf(Address("Bahnhofstrasse 1", "Zurich")), resultSet.columnValue<List<Address>>("ADDRESSES"))
    }

    @Test
    fun `reads a list of built-in values from an array column`() {
        val id = UUID.randomUUID()
        val resultSet = resultSetOf(
            "TAGS" to arrayOf("a", "b"),
            "AGES" to arrayOf(36, 37),
            "RATIOS" to arrayOf(1.5, 2.5),
            "FLAGS" to arrayOf(true, false),
            "REFERENCES" to arrayOf(id),
        )

        assertEquals(listOf("a", "b"), resultSet.columnValue<List<String>>("TAGS"))
        assertEquals(listOf(36, 37), resultSet.columnValue<List<Int>>("AGES"))
        assertEquals(listOf(1.5, 2.5), resultSet.columnValue<List<Double>>("RATIOS"))
        assertEquals(listOf(true, false), resultSet.columnValue<List<Boolean>>("FLAGS"))
        assertEquals(listOf(id), resultSet.columnValue<List<UUID>>("REFERENCES"))
    }

    @Test
    fun `reads an empty and a null array column`() {
        val resultSet = resultSetOf("TAGS" to emptyArray<String>(), "OTHER_TAGS" to null)

        assertEquals(emptyList<String>(), resultSet.columnValue<List<String>>("TAGS"))
        assertNull(resultSet.columnValue<List<String>?>("OTHER_TAGS"))
    }

    @Test
    fun `reads an array column into a nullable list`() {
        // List<String> and List<String>? are different types but the same column
        val resultSet = resultSetOf("TAGS" to arrayOf("a"))

        assertEquals(listOf("a"), resultSet.columnValue<List<String>?>("TAGS"))
    }

    @Test
    fun `reads an array of enum values through the database spelling declared for them`() {
        val resultSet = resultSetOf("APPELLATIONES" to arrayOf("vir-honoratus", "femina-honesta"))

        assertEquals(
            listOf(AppellatioComis.VIR_HONORATUS, AppellatioComis.FEMINA_HONESTA),
            resultSet.columnValue<List<AppellatioComis>>("APPELLATIONES"),
        )
    }

    @Test
    fun `reads a numeric array column that the driver hands out as BigDecimal`() {
        val resultSet = resultSetOf("RATIOS" to arrayOf(BigDecimal("1.5")))

        assertEquals(listOf(1.5), resultSet.columnValue<List<Double>>("RATIOS"))
    }
}

class ParamValueTest {

    @Test
    fun `passes a value of a scalar type through unchanged`() {
        val id = UUID.randomUUID()

        assertEquals(id, paramValue(id))
        assertEquals("Ada", paramValue("Ada"))
        assertEquals(36, paramValue(36))
        assertEquals(true, paramValue(true))
        assertNull(paramValue<String?>(null))
    }

    @Test
    fun `writes an enum as its name as long as no database spelling is declared for it`() {
        val parameter = paramValue(Salutation.MS) as SqlParameterValue

        assertEquals("MS", parameter.value)
        // the column decides whether that is a text or a SQL enum value
        assertEquals(Types.OTHER, parameter.sqlType)
    }

    @Test
    fun `writes an enum as the database spelling declared for it`() {
        val parameter = paramValue(AppellatioComis.VIR_HONORATUS) as SqlParameterValue

        assertEquals("vir-honoratus", parameter.value)
    }

    @Test
    fun `writes everything without a flat representation as json`() {
        assertEquals("""{"street":"Bahnhofstrasse 1","town":"Zurich"}""", paramValue(Address("Bahnhofstrasse 1", "Zurich")))
        // a list of nested items has no flat representation either, so it stays json
        assertEquals(
            """[{"street":"Bahnhofstrasse 1","town":"Zurich"}]""",
            paramValue(listOf(Address("Bahnhofstrasse 1", "Zurich"))),
        )
        assertEquals("[]", paramValue(emptyList<Address>()))
    }

    @Test
    fun `writes a list of built-in values as a sql array literal`() {
        val id = UUID.randomUUID()

        assertEquals("""{"a","b"}""", paramValue(listOf("a", "b")))
        assertEquals("""{"36","37"}""", paramValue(listOf(36, 37)))
        assertEquals("""{"1.5"}""", paramValue(listOf(1.5)))
        assertEquals("""{"true","false"}""", paramValue(listOf(true, false)))
        assertEquals("""{"$id"}""", paramValue(listOf(id)))
    }

    @Test
    fun `writes an empty and a null list of built-in values`() {
        // the element type is only known statically, which is why paramValue is reified
        assertEquals("{}", paramValue(emptyList<String>()))
        assertNull(paramValue<List<String>?>(null))
    }

    @Test
    fun `writes a nullable list of built-in values as a sql array literal`() {
        assertEquals("""{"a"}""", paramValue<List<String>?>(listOf("a")))
    }

    @Test
    fun `escapes the elements of a sql array literal`() {
        assertEquals("""{"a\"b","c\\d","NULL",NULL}""", paramValue(listOf("""a"b""", """c\d""", "NULL", null)))
    }

    @Test
    fun `writes a list of enum values with the database spelling of its values`() {
        assertEquals(
            """{"vir-honoratus","femina-honesta"}""",
            paramValue(listOf(AppellatioComis.VIR_HONORATUS, AppellatioComis.FEMINA_HONESTA)),
        )
    }
}
