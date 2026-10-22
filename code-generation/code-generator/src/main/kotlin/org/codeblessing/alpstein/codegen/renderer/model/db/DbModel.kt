package org.codeblessing.alpstein.codegen.renderer.model.db

import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.ItemId

/**
 * The relational schema of the whole model: one table per item that declares a primary key
 * and one entry per enum type.
 *
 * An item without a primary key is never stored on its own — it is nested into the row of the
 * item holding it — and has therefore no table here.
 */
data class DbModel(
    val tables: List<DbTableModel>,
    val enums: List<DbEnumModel>,
) {
    /** The enum types stored in a SQL enum type of their own, which the schema has to create. */
    val enumsWithEnumType: List<DbEnumModel> = enums.filter { it.hasEnumType }

    fun enumOf(enumId: EnumId): DbEnumModel = enums.single { it.enumId == enumId }

    fun tableOf(itemId: ItemId): DbTableModel = tables.singleOrNull { it.itemId == itemId }
        ?: throw NoSuchElementException(
            "The item '${itemId.itemName}' is stored in no table, because it declares no primary key. " +
                    "Stored are ${tables.map { it.itemName.pascalCase }}."
        )
}
