package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** Every item name is declared only once within the whole schema. */
class ItemNameUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.items.withIndex()
            .groupBy { (_, item) -> item.itemName }
            .filterValues { itemsWithSameName -> itemsWithSameName.size > 1 }
            .forEach { (itemName, itemsWithSameName) ->
                val declarations = itemsWithSameName.joinToString { (index, item) -> "items[$index] (${item.itemId})" }
                validationError(
                    path.child("items"),
                    "The item name '$itemName' is declared by ${itemsWithSameName.size} items, namely by " +
                            "$declarations. Every item name is declared only once.",
                )
            }
    }
}
