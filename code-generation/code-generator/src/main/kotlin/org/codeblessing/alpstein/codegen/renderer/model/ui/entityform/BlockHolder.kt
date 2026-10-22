package org.codeblessing.alpstein.codegen.renderer.model.ui.entityform

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormBlockModel

interface BlockHolder {

    fun allBlocks(): List<UiEntityFormBlockModel>

}
