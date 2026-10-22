package org.codeblessing.alpstein.codegen.renderer.model.ui

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.BuiltInType

/**
 * One editor shell of the frontend: a directory that bundles the Angular components
 * (board, search, result, form, ...) around one root item with a primary key.
 * It has no counterpart in the backend.
 */
data class UiEntityModel(
    val entityName: NameCase,
    val entityRootItem: UiItemModel,
    val entityItemModels: List<UiItemModel>,
    val entityEnumTypes: List<UiEnumModel>,
    /**
     * The attributes shown as the columns of the search result table, in the order
     * declared in the `searchResult` section of the UiEntity. They are always attributes
     * of the [entityRootItem].
     */
    val searchResultAttributes: List<UiAttributeModel>,
) {
    /**
     * The attribute of the [entityRootItem] that identifies it, e.g. for the route
     * parameter of the edit route. It is always present, because a UiEntity can only be
     * built on a root item with a primary key.
     */
    val idAttribute: BuiltInTypeUiAttributeModel = entityRootItem.primaryKeyAttribute

    /** See [UiItemModel.displayAttributes]. */
    val displayAttributes: List<UiAttributeModel> = entityRootItem.displayAttributes

    /** The search result columns that show their value as it is, i.e. everything but a reference. */
    val searchResultPlainAttributes: List<UiAttributeModel> = searchResultAttributes
        .filter { !it.isItemReference }

    /**
     * The search result columns that reference another item. Such a column holds nothing but a
     * primary key, which tells the user nothing, so it is shown by the display attributes of the
     * referenced item instead.
     */
    val searchResultReferenceAttributes: List<ItemReferenceUiAttributeModel> = searchResultAttributes
        .filterIsInstance<ItemReferenceUiAttributeModel>()

    /**
     * The items referenced by [searchResultReferenceAttributes], each with the columns pointing
     * to it, e.g. to resolve them with one call per referenced item.
     */
    val searchResultReferencedItems: List<UiSearchResultReferencedItemModel> = searchResultReferenceAttributes
        .groupBy { it.referencedItem }
        .map { (referencedItem, referenceAttributes) ->
            UiSearchResultReferencedItemModel(referencedItem, referenceAttributes)
        }

    /**
     * Whether any item referenced by the search result is identified by a [BuiltInType.UUID],
     * e.g. to render the import of the UUID type.
     */
    val searchResultContainsUuidReferences: Boolean = searchResultReferencedItems
        .any { it.idBuiltInType == BuiltInType.UUID }
}
