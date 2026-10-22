package org.codeblessing.alpstein.codegen.renderer.model.db

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.ItemId

/**
 * The table one item with a primary key is stored in, with one column per attribute of that
 * item. The [tableName] is a SQL identifier and therefore a plain string, either declared by
 * the `dbItem` of the item or derived from the [itemName].
 */
data class DbTableModel(
    val itemId: ItemId,
    val itemName: NameCase,
    val tableName: String,
    val columns: List<DbColumnModel>,
) {
    val primaryKeyColumn: DbColumnModel = columns.single { it.isPrimaryKey }

    val columnsWithoutPrimaryKey: List<DbColumnModel> = columns.filter { !it.isPrimaryKey }
}
