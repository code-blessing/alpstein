/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemReferenceFieldComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-reference-field.component.scss`
 * - path: `reference/membrum-relatum-reference-field/membrum-relatum-reference-field.component.scss`
 */
object ItemReferenceFieldComponentScssRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |:host {
          |    display: block;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-reference-field/${model.itemName.kebabCase}-reference-field.component.scss"
    }
}