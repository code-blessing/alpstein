package org.codeblessing.alpstein.model.schema

/**
 * The UI configuration of an [Item] itself, holding for every UI component that shows
 * that item. In contrast to a [UiEntity], which configures one single editor, this
 * configuration is independent of the editor the item appears in.
 */
data class UiItem(
    val itemId: ItemId,
    /**
     * The attributes that identify one instance of this item for a human reader, in the
     * order they are shown, declared as the names of the attributes of the item.
     */
    val displayAttributeNames: List<String>,
    /**
     * The columns of the editor of this item wherever it is edited as a nested item of a
     * [UiEntity] that does not configure the item itself with its own
     * [UiEntityEditorEntityNestedItemConfiguration].
     *
     * Empty as long as the item declares no such default.
     */
    val defaultNestedItemEditor: List<UiEntityEditorColumn>,
)

/**
 * A [UiEntity] exists for the frontend only: it is the shell that bundles all
 * Angular components of one editor around a root [Item] with a primary key.
 * The backend has no notion of it.
 */
data class UiEntity(
    val uiEntityName: String,
    val rootItem: Item,
    val editorView: UiEntityEditorView,
    val searchResultView: UiEntitySearchResultView,
)

/**
 * The columns of the search result table of a [UiEntity], declared as the names of the
 * attributes of its [UiEntity.rootItem] in the order they are shown.
 */
data class UiEntitySearchResultView(
    val attributeNames: List<String>,
)

data class UiEntityEditorView(
    val itemConfiguration: List<UiEntityEditorItemConfiguration>,
)

sealed interface UiEntityEditorItemConfiguration {
    val noTab: List<UiEntityEditorColumn>
}

data class UiEntityEditorRootItemConfiguration(
    override val noTab: List<UiEntityEditorColumn>,
    val tabs: List<UiEntityEditorTab>,
): UiEntityEditorItemConfiguration

data class UiEntityEditorEntityNestedItemConfiguration(
    val itemId: ItemId,
    override val noTab: List<UiEntityEditorColumn>,
): UiEntityEditorItemConfiguration

data class UiEntityEditorTab(
    val tabTranslationKey: String,
    val columns: List<UiEntityEditorColumn>
)

data class UiEntityEditorColumn(
    val blocks: List<UiBlock>
)

sealed interface UiBlock

data class UiItemAttributeBlock(
    val attributeName: String,
): UiBlock

data class UiSectionBlock(
    val sectionTranslationKey: String,
): UiBlock

data class UiTextBlock(
    val textTranslationKey: String,
): UiBlock
