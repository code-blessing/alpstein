/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityConfirmDeleteDialogComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-confirm-delete-dialog.component.scss`
 * - path: `opus-magnum/opus-magnum-confirm-delete-dialog/opus-magnum-confirm-delete-dialog.component.scss`
 */
object EntityConfirmDeleteDialogComponentScssRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-confirm-delete-dialog/${model.entityName.kebabCase}-confirm-delete-dialog.component.scss"
    }
}