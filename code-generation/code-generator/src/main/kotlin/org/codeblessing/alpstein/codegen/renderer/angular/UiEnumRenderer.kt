package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel

interface UiEnumRenderer {
    fun renderTemplate(model: UiEnumModel): String
    fun filePath(model: UiEnumModel): String
}
