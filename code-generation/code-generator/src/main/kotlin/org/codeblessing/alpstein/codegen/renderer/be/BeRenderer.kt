package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeModel

/** Renders one single file out of all items, in contrast to the per-item [BeItemRenderer]. */
interface BeRenderer {
    fun renderTemplate(model: BeModel): String
    fun filePath(model: BeModel): String
}
