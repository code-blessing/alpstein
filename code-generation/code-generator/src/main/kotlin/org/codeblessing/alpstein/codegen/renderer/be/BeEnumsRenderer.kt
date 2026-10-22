package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeEnumModel

/** Renders one single file out of all enum types, in contrast to the per-enum [BeEnumRenderer]. */
interface BeEnumsRenderer {
    fun renderTemplate(models: List<BeEnumModel>): String
    fun filePath(models: List<BeEnumModel>): String
}
