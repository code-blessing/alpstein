package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

interface UiItemRenderer {
    fun renderTemplate(model: UiItemModel): String
    fun filePath(model: UiItemModel): String
}
