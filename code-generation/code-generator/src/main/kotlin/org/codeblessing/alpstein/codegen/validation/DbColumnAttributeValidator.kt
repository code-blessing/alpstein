package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** A database configuration names a column only for an attribute of its own item, and only once. */
class DbColumnAttributeValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.dbItems.forEachIndexed { dbItemIndex, dbItem ->
            val dbItemPath = path.child("dbItems", dbItemIndex, dbItem.itemId.itemName)
            val item = schemaData.items.firstOrNull { it.itemId == dbItem.itemId } ?: return@forEachIndexed

            dbItem.columns.forEachIndexed { columnIndex, column ->
                if (item.attributes.none { it.attributeName == column.attributeName }) {
                    validationError(
                        dbItemPath.child("columns", columnIndex, column.attributeName),
                        "The database configuration names a column for the attribute " +
                                "'${column.attributeName}', but the item '${item.itemName}' has no such " +
                                "attribute. Available are ${item.attributes.map { it.attributeName }}.",
                    )
                }
            }

            dbItem.columns.withIndex()
                .groupBy { (_, column) -> column.attributeName }
                .filterValues { columnsOfSameAttribute -> columnsOfSameAttribute.size > 1 }
                .forEach { (attributeName, columnsOfSameAttribute) ->
                    val declarations = columnsOfSameAttribute.joinToString { (index, _) -> "columns[$index]" }
                    validationError(
                        dbItemPath,
                        "The database configuration names a column for the attribute '$attributeName' " +
                                "${columnsOfSameAttribute.size} times, namely in $declarations. " +
                                "Every attribute is named only once.",
                    )
                }
        }
    }
}
