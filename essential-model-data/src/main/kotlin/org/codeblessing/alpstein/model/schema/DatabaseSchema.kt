package org.codeblessing.alpstein.model.schema

/**
 * The relational mapping of an [Item] that declares a primary key, as far as it is declared
 * by a `dbItem` block. Everything in it is optional: an item without a `dbItem`, or a
 * `dbItem` that leaves a part out, falls back to the name of the item resp. of the attribute
 * in `SCREAMING_SNAKE_CASE`.
 *
 * A name declared here is a SQL identifier and is therefore used verbatim, never re-cased.
 */
data class DbItem(
    val itemId: ItemId,
    /** `null` as long as no `tableName` is declared. */
    val tableName: String?,
    /** Only the columns whose name is declared; empty as long as none is. */
    val columns: List<DbColumn>,
)

/**
 * The column the attribute [attributeName] of an [Item] is stored in.
 */
data class DbColumn(
    val attributeName: String,
    val columnName: String,
)

/**
 * The database representation of an [EnumType], as far as it is declared by a `dbEnum` block.
 * Everything in it is optional: an enum type without a `dbEnum`, or a `dbEnum` that leaves a
 * part out, falls back to the name of the enum value in `SCREAMING_SNAKE_CASE`, stored in a
 * `text` column.
 *
 * A name declared here is a SQL identifier resp. a value stored in the database and is
 * therefore used verbatim, never re-cased.
 */
data class DbEnum(
    val enumId: EnumId,
    /** `null` as long as no `enumTypeName` is declared; the values are then stored as `text`. */
    val enumTypeName: String?,
    /** Only the values whose database spelling is declared; empty as long as none is. */
    val values: List<DbEnumValue>,
)

/**
 * The way the value [enumValue] of an [EnumType] is spelled in the database, which is free of
 * the PascalCase rule an enum value follows: `PG13` is stored as `PG-13`.
 */
data class DbEnumValue(
    val enumValue: String,
    val databaseValue: String,
)
