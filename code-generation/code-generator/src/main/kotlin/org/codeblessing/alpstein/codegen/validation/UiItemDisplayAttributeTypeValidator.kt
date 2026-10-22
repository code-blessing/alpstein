package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * The ui configuration of an item shows only attributes of a built-in type, of an enum
 * type or a reference to another item as its display attributes, because a display
 * attribute is rendered as a plain string wherever a reference to the item is shown.
 *
 * A reference qualifies because it stores nothing but the primary key of the referenced
 * item, which is of a built-in type itself. A nested item does not: it is a whole
 * instance instead of a single value.
 */
class UiItemDisplayAttributeTypeValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.uiItems.forEachIndexed { uiItemIndex, uiItem ->
            val uiItemPath = path.child("uiItems", uiItemIndex, uiItem.itemId.itemName)
            val item = schemaData.items.firstOrNull { it.itemId == uiItem.itemId } ?: return@forEachIndexed

            uiItem.displayAttributeNames.forEachIndexed { attributeIndex, attributeName ->
                val attribute = item.attributes.firstOrNull { it.attributeName == attributeName }
                    ?: return@forEachIndexed
                if (attribute.type !is BuiltInType && attribute.type !is EnumId && !attribute.isReference) {
                    validationError(
                        uiItemPath.child("displayAttributeNames", attributeIndex, attributeName),
                        "The ui configuration shows '$attributeName' of the item '${item.itemName}' as a " +
                                "display attribute, but that attribute is of ${attribute.type.description} " +
                                "and does not reference it by its primary key. A display attribute is only " +
                                "an attribute of a built-in type, of an enum type or a reference to another " +
                                "item.",
                    )
                }
            }
        }
    }
}
