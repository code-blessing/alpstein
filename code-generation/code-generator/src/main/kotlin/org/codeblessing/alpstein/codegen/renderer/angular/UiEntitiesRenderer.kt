package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

interface UiEntitiesRenderer {
    fun renderTemplate(models: List<UiEntityModel>): String
    fun filePath(models: List<UiEntityModel>): String
}
