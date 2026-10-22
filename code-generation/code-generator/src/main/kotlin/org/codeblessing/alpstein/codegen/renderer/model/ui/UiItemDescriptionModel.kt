package org.codeblessing.alpstein.codegen.renderer.model.ui

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.ItemId

data class UiItemDescriptionModel(
    val itemId: ItemId,
    val itemName: NameCase,
)
