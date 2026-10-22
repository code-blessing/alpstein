package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.codegen.renderer.model.db.DbNameDefaults
import org.codeblessing.alpstein.model.schema.DbItem
import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * Every table name is used by one item only and every column name by one attribute only.
 *
 * The derived default names cannot collide, as item and attribute names are unique already;
 * a collision therefore always involves a name declared by a `dbItem`.
 */
class DbNameUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        val dbItemPerItem: Map<ItemId, DbItem> = schemaData.dbItems.associateBy { it.itemId }
        val storedItems = schemaData.items.filter { it.hasPrimaryKey }
        val dbItemsPath = path.child("dbItems")

        storedItems
            .groupBy { DbNameDefaults.tableName(it, dbItemPerItem[it.itemId]) }
            .filterValues { itemsInSameTable -> itemsInSameTable.size > 1 }
            .forEach { (tableName, itemsInSameTable) ->
                validationError(
                    dbItemsPath,
                    "The table '$tableName' is used by ${itemsInSameTable.size} items, namely by " +
                            "${itemsInSameTable.map { it.itemName }}. Every item is stored in a table of its own.",
                )
            }

        storedItems.forEach { item ->
            validateColumnNames(item, dbItemPerItem[item.itemId], dbItemsPath)
        }
    }

    private fun validateColumnNames(item: Item, dbItem: DbItem?, path: ValidationPath) {
        item.attributes
            .groupBy { DbNameDefaults.columnName(it, dbItem) }
            .filterValues { attributesInSameColumn -> attributesInSameColumn.size > 1 }
            .forEach { (columnName, attributesInSameColumn) ->
                validationError(
                    path,
                    "The column '$columnName' of the table '${DbNameDefaults.tableName(item, dbItem)}' stores " +
                            "${attributesInSameColumn.size} attributes, namely " +
                            "${attributesInSameColumn.map { it.attributeName }}. " +
                            "Every attribute is stored in a column of its own.",
                )
            }
    }
}
