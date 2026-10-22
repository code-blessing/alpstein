package org.codeblessing.alpstein.codegen.renderer.model.ui

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.BuiltInType

/**
 * One item that the search result of a [UiEntityModel] references, together with the
 * reference attributes of that search result pointing to it.
 *
 * A row of the search result holds nothing but the primary keys of its references, therefore
 * the search result resolves them to whole items and shows their display attributes instead.
 * All [referenceAttributes] are resolved with one single call per referenced item, which is
 * why they are grouped here.
 */
data class UiSearchResultReferencedItemModel(
    val referencedItem: UiReferencedItemModel,
    val referenceAttributes: List<ItemReferenceUiAttributeModel>,
) {
    val itemName: NameCase = referencedItem.itemName
    val idAttributeName: NameCase = referencedItem.idAttributeName
    val idBuiltInType: BuiltInType = referencedItem.idBuiltInType

    /** The TypeScript type of that primary key, which is therefore the type of every reference to the item. */
    val idTypescriptType: String = typescriptBuiltInType(idBuiltInType)
}
