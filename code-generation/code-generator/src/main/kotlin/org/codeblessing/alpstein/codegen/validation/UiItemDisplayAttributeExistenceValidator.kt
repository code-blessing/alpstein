package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.SchemaData
import org.codeblessing.alpstein.model.schema.UiItem

/** The ui configuration of an item shows only attributes of that item as its display attributes. */
class UiItemDisplayAttributeExistenceValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.uiItems.forEachIndexed { uiItemIndex, uiItem ->
            val uiItemPath = path.child("uiItems", uiItemIndex, uiItem.itemId.itemName)
            val item = configuredItem(uiItem, schemaData, uiItemPath)

            uiItem.displayAttributeNames.forEachIndexed { attributeIndex, attributeName ->
                if (item.attributes.none { it.attributeName == attributeName }) {
                    validationError(
                        uiItemPath.child("displayAttributeNames", attributeIndex, attributeName),
                        "The ui configuration shows '$attributeName' as a display attribute, but the item " +
                                "'${item.itemName}' has no such attribute. Available are " +
                                "${item.attributes.map { it.attributeName }}.",
                    )
                }
            }
        }
    }

    private fun configuredItem(uiItem: UiItem, schemaData: SchemaData, path: ValidationPath): Item =
        schemaData.items.firstOrNull { it.itemId == uiItem.itemId }
            ?: validationError(
                path,
                "There is a ui configuration for the item '${uiItem.itemId.itemName}', but no such item is " +
                        "declared in the schema. Available are ${schemaData.items.map { it.itemName }}.",
            )
}
