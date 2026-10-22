package org.codeblessing.alpstein.codegen.renderer.model.ui.entityform

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormBlockModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormNamedSectionSplitBlockModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormTextBlockModel

data class UiEntityFormViewColumnModel(
    private val entity: UiEntityModel,
    val blocks: List<UiEntityFormBlockModel>
): BlockHolder {
    override fun allBlocks(): List<UiEntityFormBlockModel> {
        return blocks
    }
}
