package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** The search result of a ui entity shows only attributes of its root item. */
class SearchResultViewAttributeExistenceValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.uiEntities.forEachIndexed { entityIndex, uiEntity ->
            val searchResultViewPath = path
                .child("uiEntities", entityIndex, uiEntity.uiEntityName)
                .child("searchResultView")
            val rootItem = uiEntity.rootItem

            uiEntity.searchResultView.attributeNames.forEachIndexed { attributeIndex, attributeName ->
                if (rootItem.attributes.none { it.attributeName == attributeName }) {
                    validationError(
                        searchResultViewPath.child("attributeNames", attributeIndex, attributeName),
                        "The search result shows the attribute '$attributeName', but the root item " +
                                "'${rootItem.itemName}' has no such attribute. Available are " +
                                "${rootItem.attributes.map { it.attributeName }}.",
                    )
                }
            }
        }
    }
}
