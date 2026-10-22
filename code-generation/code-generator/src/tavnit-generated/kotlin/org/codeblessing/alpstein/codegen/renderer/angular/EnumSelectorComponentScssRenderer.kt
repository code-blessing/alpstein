/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel

/**
 * Generate the content for the template `EnumSelectorComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `appellatio-comis-selector.component.scss`
 * - path: `enum/appellatio-comis-input-selection/appellatio-comis-selector.component.scss`
 */
object EnumSelectorComponentScssRenderer : UiEnumRenderer {

    override fun renderTemplate(model: UiEnumModel): String {
        return """
          |:host {
          |    display: block;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEnumModel): String {
      return "enum/${model.enumName.kebabCase}-input-selection/${model.enumName.kebabCase}-selector.component.scss"
    }
}