package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** The ui configuration of an item shows every display attribute only once. */
class UiItemDisplayAttributeUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.uiItems.forEachIndexed { uiItemIndex, uiItem ->
            uiItem.displayAttributeNames.withIndex()
                .groupBy { (_, attributeName) -> attributeName }
                .filterValues { sameAttributeNames -> sameAttributeNames.size > 1 }
                .forEach { (attributeName, sameAttributeNames) ->
                    val declarations = sameAttributeNames
                        .joinToString { (index, _) -> "displayAttributeNames[$index]" }
                    validationError(
                        path.child("uiItems", uiItemIndex, uiItem.itemId.itemName),
                        "The ui configuration shows '$attributeName' as a display attribute " +
                                "${sameAttributeNames.size} times, namely as $declarations. " +
                                "Every display attribute is declared only once per item.",
                    )
                }
        }
    }
}
