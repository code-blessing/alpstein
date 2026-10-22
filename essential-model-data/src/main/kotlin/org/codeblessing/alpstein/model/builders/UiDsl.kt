package org.codeblessing.alpstein.model.builders

import org.codeblessing.alpstein.model.schema.ItemId

@MainDslMarker
interface UiItemDsl {
    /**
     * Declares the attributes that identify one instance of this item for a human reader,
     * see [UiDisplayAttributesDsl].
     */
    fun displayAttributes(builder: UiDisplayAttributesDsl.() -> Unit)

    /**
     * Declares the editor of this item that is used wherever the item is edited as a nested
     * item of an editor, so that the same configuration does not have to be repeated in
     * every [UiEntityDsl] the item appears in.
     *
     * An editor that declares its own [UiEditorDsl.configureEditorForNestedItem] for this
     * item uses that one instead of this default.
     */
    fun configureEditorForNestedItemDefault(builder: UiEditorForNestedItemDsl.() -> Unit)
}

/**
 * Declares which attributes of an item are shown wherever a reference to that item is
 * rendered, in the order they are declared here.
 *
 * A reference is stored as a bare primary key, which tells the user nothing, so every place that
 * shows one resolves it to the whole item and renders these attributes instead. They have
 * to be single-valued text attributes, because they are rendered as plain strings.
 *
 * As long as an item declares none, every single-valued text attribute of the item is used.
 */
@MainDslMarker
interface UiDisplayAttributesDsl {
    fun attribute(attributeName: String)
}

@MainDslMarker
interface UiEntityDsl {
    fun views(builder: UiViewsDsl.() -> Unit)
}

@MainDslMarker
interface UiViewsDsl {
    fun editor(builder: UiEditorDsl.() -> Unit)

    /**
     * Declares the columns of the search result table, see [UiSearchResultDsl].
     */
    fun searchResult(builder: UiSearchResultDsl.() -> Unit)
}

/**
 * Declares which attributes of the main item are shown as the columns of the
 * search result table, in the order they are declared here.
 */
@MainDslMarker
interface UiSearchResultDsl {
    fun attribute(attributeName: String)
}

@MainDslMarker
interface UiEditorDsl {
    fun configureEditorForMainItem(builder: UiEditorForMainItemDsl.() -> Unit)

    fun configureEditorForNestedItem(
        itemId: ItemId,
        builder: UiEditorForNestedItemDsl.() -> Unit,
    )
}

@MainDslMarker
interface UiEditorForMainItemDsl {
    fun tab(
        tabTranslationKey: String,
        builder: UiTabDsl.() -> Unit,
    )

    fun column(builder: UiBlockEditorDsl.() -> Unit)
}

@MainDslMarker
interface UiEditorForNestedItemDsl {
    fun column(builder: UiBlockEditorDsl.() -> Unit)
}

@MainDslMarker
interface UiTabDsl {
    fun column(builder: UiBlockEditorDsl.() -> Unit)
}

@MainDslMarker
interface UiBlockEditorDsl {
    fun text(textTranslationKey: String)

    fun section(sectionTranslationKey: String)

    fun attribute(attributeName: String)
}
