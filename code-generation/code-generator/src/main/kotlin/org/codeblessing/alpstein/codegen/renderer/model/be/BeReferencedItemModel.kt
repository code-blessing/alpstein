package org.codeblessing.alpstein.codegen.renderer.model.be

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.ItemId

/**
 * The item an attribute of another item points to, with everything the templates need to
 * fetch it, e.g. to create example data that references a really existing instance.
 * Only an item with a primary key can be referenced.
 */
data class BeReferencedItemModel(
    val itemId: ItemId,
    val itemName: NameCase,
    /** The attribute of the referenced item that holds the referenced primary key. */
    val idAttributeName: NameCase,
    /**
     * The built-in type of that primary key, which is therefore also the type every
     * reference to this item is stored and transported as.
     */
    val idBuiltInType: BuiltInType,
)
