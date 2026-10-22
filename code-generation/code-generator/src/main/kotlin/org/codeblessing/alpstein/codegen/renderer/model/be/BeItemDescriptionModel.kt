package org.codeblessing.alpstein.codegen.renderer.model.be

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.ItemId

data class BeItemDescriptionModel(
    val itemId: ItemId,
    val itemName: NameCase,
)
