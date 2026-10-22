package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** Every item is configured for the database only once. */
class DbItemUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.dbItems.withIndex()
            .groupBy { (_, dbItem) -> dbItem.itemId }
            .filterValues { dbItemsOfSameItem -> dbItemsOfSameItem.size > 1 }
            .forEach { (itemId, dbItemsOfSameItem) ->
                val declarations = dbItemsOfSameItem.joinToString { (index, _) -> "dbItems[$index]" }
                validationError(
                    path.child("dbItems"),
                    "The item '${itemId.itemName}' is configured for the database by " +
                            "${dbItemsOfSameItem.size} declarations, namely by $declarations. " +
                            "An item is configured for the database only once.",
                )
            }
    }
}
