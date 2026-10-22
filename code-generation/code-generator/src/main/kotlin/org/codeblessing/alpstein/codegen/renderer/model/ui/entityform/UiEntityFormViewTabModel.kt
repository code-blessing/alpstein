package org.codeblessing.alpstein.codegen.renderer.model.ui.entityform

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormBlockModel

data class UiEntityFormViewTabModel(
    val entity: UiEntityModel,
    val tabTranslationKey: String,
    val columns: List<UiEntityFormViewColumnModel>,
) : BlockHolder {
    override fun allBlocks(): List<UiEntityFormBlockModel> {
        return columns.flatMap { it.allBlocks() }
    }
}
