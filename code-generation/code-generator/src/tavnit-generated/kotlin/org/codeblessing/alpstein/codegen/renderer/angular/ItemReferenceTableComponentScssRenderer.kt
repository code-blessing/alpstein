/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemReferenceTableComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-reference-table.component.scss`
 * - path: `reference/membrum-relatum-reference-table/membrum-relatum-reference-table.component.scss`
 */
object ItemReferenceTableComponentScssRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |.table-container {
          |  padding: 20px;
          |
          |  table {
          |    width: 100%;
          |  }
          |
          |  th.mat-header-cell {
          |    font-weight: bold;
          |    color: rgba(0, 0, 0, 0.87);
          |  }
          |
          |  .action-bar {
          |    margin-bottom: 10px;
          |  }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-reference-table/${model.itemName.kebabCase}-reference-table.component.scss"
    }
}