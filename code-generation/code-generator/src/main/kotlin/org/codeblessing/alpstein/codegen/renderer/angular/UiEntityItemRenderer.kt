package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

interface UiEntityItemRenderer {
    fun renderTemplate(model: UiEntityFormViewItemModel): String
    fun filePath(model: UiEntityFormViewItemModel): String
}
