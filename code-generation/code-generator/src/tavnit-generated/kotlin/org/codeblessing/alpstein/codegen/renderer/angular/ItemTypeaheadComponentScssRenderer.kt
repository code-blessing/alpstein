/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemTypeaheadComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-typeahead.component.scss`
 * - path: `reference/membrum-relatum-typeahead/membrum-relatum-typeahead.component.scss`
 */
object ItemTypeaheadComponentScssRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |.typeahead-field {
          |  width: 100%;
          |  min-width: 300px;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-typeahead/${model.itemName.kebabCase}-typeahead.component.scss"
    }
}