/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemFormPartComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part.component.scss`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part.component.scss`
 */
object EntityItemFormPartComponentScssRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |
          |.form-row {
          |  margin-bottom: 20px;
          |
          |  mat-form-field {
          |    width: 100%;
          |  }
          |}
          |
          |.edit-area {
          |  border-left-color: darkgrey;
          |  border-left-width: 1px;
          |  border-left-style: solid;
          |}
          |
          |.column-layout {
          |  display: flex;
          |  gap: 16px;
          |  padding: 16px;
          |}
          |
          |.column {
          |  flex: 1;
          |}
          |
          |@media (max-width: 768px) {
          |  .column-layout {
          |    flex-direction: column;
          |  }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part.component.scss"
    }
}