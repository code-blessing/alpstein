package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

interface UiEntityRenderer {
    fun renderTemplate(model: UiEntityModel): String
    fun filePath(model: UiEntityModel): String
}
