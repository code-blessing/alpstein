/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemTableComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-articulus-interior-table.component.scss`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-table/opus-magnum-articulus-interior-table.component.scss`
 */
object EntityItemTableComponentScssRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
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
          |  .mat-elevation-z8 {
          |    box-shadow: 0 5px 5px -3px rgba(0, 0, 0, .2),
          |    0 8px 10px 1px rgba(0, 0, 0, .14),
          |    0 3px 14px 2px rgba(0, 0, 0, .12);
          |  }
          |}
          |
          |.selectedRow {
          |  background-color: rgba(0, 0, 0, .01);
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table.component.scss"
    }
}