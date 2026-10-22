package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeEnumModel

interface BeEnumRenderer {
    fun renderTemplate(model: BeEnumModel): String
    fun filePath(model: BeEnumModel): String
}
