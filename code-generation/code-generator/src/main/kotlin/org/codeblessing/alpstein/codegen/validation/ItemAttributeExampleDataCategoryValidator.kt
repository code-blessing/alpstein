package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * Only an attribute of a built-in type declares an example data category, because
 * example data for an enum type or a nested item is derived from that type itself.
 */
class ItemAttributeExampleDataCategoryValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.items.forEachIndexed { itemIndex, item ->
            val itemPath = path.child("items", itemIndex, item.itemName)
            item.attributes.forEachIndexed { attributeIndex, attribute ->
                val exampleDataCategory = attribute.exampleDataCategory ?: return@forEachIndexed
                if (attribute.type !is BuiltInType) {
                    validationError(
                        itemPath.child("attributes", attributeIndex, attribute.attributeName),
                        "The attribute declares the example data category $exampleDataCategory, but it is of " +
                                "${attribute.type.description}. Only an attribute of one of the built-in types " +
                                "${BuiltInType.entries} declares an example data category.",
                    )
                }
            }
        }
    }
}
