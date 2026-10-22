package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

interface BeItemRenderer {
    fun renderTemplate(model: BeItemModel): String
    fun filePath(model: BeItemModel): String
}
