package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * The search result of a ui entity shows only attributes of a built-in type, of an enum
 * type or a reference to another item, because a search result column renders its value as
 * a plain string.
 *
 * A reference qualifies because it stores nothing but the primary key of the referenced
 * item: the search result resolves it and shows the display attributes of that item. A
 * nested item does not: it is a whole instance instead of a single value.
 */
class SearchResultViewAttributeTypeValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.uiEntities.forEachIndexed { entityIndex, uiEntity ->
            val searchResultViewPath = path
                .child("uiEntities", entityIndex, uiEntity.uiEntityName)
                .child("searchResultView")
            val rootItem = uiEntity.rootItem

            uiEntity.searchResultView.attributeNames.forEachIndexed { attributeIndex, attributeName ->
                val attribute = rootItem.attributes.firstOrNull { it.attributeName == attributeName }
                    ?: return@forEachIndexed
                if (attribute.type !is BuiltInType && attribute.type !is EnumId && !attribute.isReference) {
                    validationError(
                        searchResultViewPath.child("attributeNames", attributeIndex, attributeName),
                        "The search result shows the attribute '$attributeName' of the root item " +
                                "'${rootItem.itemName}', but that attribute is of ${attribute.type.description} " +
                                "and does not reference it by its primary key. A search result only shows an " +
                                "attribute of a built-in type, of an enum type or a reference to another item.",
                    )
                }
            }
        }
    }
}
