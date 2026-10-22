package org.codeblessing.alpstein.codegen.renderer.model.ui.entityform

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormBlockModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormNamedSectionSplitBlockModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormTextBlockModel

data class UiEntityFormViewItemModel(
    val entity: UiEntityModel,
    val item: UiItemModel,
    val noTab: List<UiEntityFormViewColumnModel>,
    val tabs: List<UiEntityFormViewTabModel>
) : BlockHolder {

    fun containsTextBlocks(): Boolean {
        return allBlocks().filterIsInstance<UiEntityFormTextBlockModel>().isNotEmpty()
    }

    fun containsNamedSectionSplitBlocks(): Boolean {
        return allBlocks().filterIsInstance<UiEntityFormNamedSectionSplitBlockModel>().isNotEmpty()
    }

    override fun allBlocks(): List<UiEntityFormBlockModel> {
        return noTab.flatMap { it.allBlocks() } + tabs.flatMap { it.allBlocks() }
    }
}
