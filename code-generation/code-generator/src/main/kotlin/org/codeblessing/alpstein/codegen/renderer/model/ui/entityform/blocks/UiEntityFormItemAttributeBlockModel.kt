package org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

data class UiEntityFormItemAttributeBlockModel(
    val entity: UiEntityModel,
    val item: UiItemModel,
    val attribute: UiAttributeModel,
) : UiEntityFormBlockModel
