package org.codeblessing.alpstein.model.builders

import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.ItemId

@MainDslMarker
interface RootDsl {
    fun schema(builder: SchemaDsl.() -> Unit)
}

@MainDslMarker
interface SchemaDsl {
    fun enumType(
        enumId: EnumId,
        builder: EnumDsl.() -> Unit,
    )

    fun item(
        itemId: ItemId,
        builder: ItemDsl.() -> Unit,
    )

    /**
     * Declares the UI configuration of the item [itemId] that holds wherever that item is
     * shown, for example `uiItem(itemId = Items.ADDRESS) { displayAttributes { ... } }`.
     *
     * In contrast to [uiEntity], which configures one single editor, this configuration
     * belongs to the item itself and is therefore used by every UI component showing it.
     */
    fun uiItem(
        itemId: ItemId,
        builder: UiItemDsl.() -> Unit,
    )

    /**
     * Declares how the item [itemId] is mapped to a SQL table, for example
     * `dbItem(itemId = Items.COMPANY) { tableName(name = "T_COMPANY") }`.
     *
     * Only an item with a primary key is stored in a table of its own, therefore only such
     * an item is configured here. Declaring nothing at all is valid: the table and column
     * names are then derived from the item and attribute names.
     */
    fun dbItem(
        itemId: ItemId,
        builder: DbItemDsl.() -> Unit,
    )

    /**
     * Declares how the values of the enum type [enumId] are spelled in the database, for
     * example `dbEnum(enumId = EnumTypes.MPAA_RATING) { enumTypeName(name = "mpaa_rating") }`.
     *
     * Declaring nothing at all is valid: the values are then stored as `text`, spelled as the
     * enum value in `SCREAMING_SNAKE_CASE`.
     */
    fun dbEnum(
        enumId: EnumId,
        builder: DbEnumDsl.() -> Unit,
    )

    /**
     * Declares the frontend shell that bundles all Angular components of one editor
     * around the root item [rootItemId], which has to declare a primary key.
     * The [uiEntityName] names the directory and the component classes.
     */
    fun uiEntity(
        uiEntityName: String,
        rootItemId: ItemId,
        builder: UiEntityDsl.() -> Unit,
    )
}
