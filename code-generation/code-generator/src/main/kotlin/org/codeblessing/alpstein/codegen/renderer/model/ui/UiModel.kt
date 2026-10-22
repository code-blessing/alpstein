package org.codeblessing.alpstein.codegen.renderer.model.ui

/**
 * Everything the client renderers need: the items (the transport and service layer),
 * the enums, and the UiEntities (the editor shells, e.g. for the left navigation).
 */
data class UiModel(
    val uiItems: List<UiItemModel>,
    val uiEnums: List<UiEnumModel>,
    val uiEntitiesViews: List<UiEntityViewsModel>,
) {
    val uiEntities: List<UiEntityModel> = uiEntitiesViews.map { it.uiEntity }

    /**
     * The items that are identified by a primary key. Only for those the service, the
     * search/by-ids WTOs and the reference components exist, because only they can be
     * addressed, searched and referenced.
     */
    val uiItemsWithPrimaryKey: List<UiItemModel> = uiItems.filter { it.hasPrimaryKey }
}
