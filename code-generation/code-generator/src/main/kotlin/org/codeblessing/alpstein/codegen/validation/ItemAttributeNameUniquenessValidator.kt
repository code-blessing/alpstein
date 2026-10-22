package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** Every attribute name of an item is declared only once within that item. */
class ItemAttributeNameUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.items.forEachIndexed { itemIndex, item ->
            item.attributes.withIndex()
                .groupBy { (_, attribute) -> attribute.attributeName }
                .filterValues { attributesWithSameName -> attributesWithSameName.size > 1 }
                .forEach { (attributeName, attributesWithSameName) ->
                    val declarations = attributesWithSameName.joinToString { (index, _) -> "attributes[$index]" }
                    validationError(
                        path.child("items", itemIndex, item.itemName),
                        "The item declares the attribute name '$attributeName' " +
                                "${attributesWithSameName.size} times, namely as $declarations. " +
                                "Every attribute name is declared only once per item.",
                    )
                }
        }
    }
}
