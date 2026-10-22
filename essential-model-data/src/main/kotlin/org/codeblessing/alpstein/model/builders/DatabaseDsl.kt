package org.codeblessing.alpstein.model.builders

/**
 * Declares how one item is mapped to a SQL table, for example
 * `dbItem(itemId = Items.COMPANY) { tableName(name = "T_COMPANY") }`.
 *
 * Both declarations are optional and only override a default: without them the table is
 * named after the item and every column after its attribute, in `SCREAMING_SNAKE_CASE`.
 */
@MainDslMarker
interface DbItemDsl {
    fun tableName(name: String)

    /**
     * Declares the column the attribute [attributeName] of this item is stored in.
     * The attributes not declared here keep their default column name.
     */
    fun column(attributeName: String, columnName: String)
}

/**
 * Declares how the values of one enum type are spelled in the database, for example
 * `dbEnum(enumId = EnumTypes.MPAA_RATING) { enumTypeName(name = "mpaa_rating") }`.
 *
 * Both declarations are optional and only override a default: without them the values are
 * stored as `text`, spelled as the enum value in `SCREAMING_SNAKE_CASE`.
 */
@MainDslMarker
interface DbEnumDsl {
    /**
     * Declares the SQL enum type the values are stored in, which is created by the
     * generated schema. Without it they are stored in a `text` column.
     */
    fun enumTypeName(name: String)

    /**
     * Declares how the enum value [name] is spelled in the database, e.g.
     * `enumValue(name = "PG13", databaseValue = "PG-13")`.
     * The values not declared here keep their default spelling.
     */
    fun enumValue(name: String, databaseValue: String)
}
