/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityBoardComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-board.component.scss`
 * - path: `opus-magnum/opus-magnum-board/opus-magnum-board.component.scss`
 */
object EntityBoardComponentScssRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |.${model.entityName.kebabCase}-container {
          |  padding: 20px;
          |
          |  h2 {
          |    margin-bottom: 20px;
          |    color: rgba(0, 0, 0, 0.87);
          |  }
          |}
          |
          |.${model.entityName.kebabCase}-header {
          |  display: flex;
          |  align-items: center;
          |  justify-content: space-between;
          |  gap: 16px;
          |
          |  h2 {
          |    margin-bottom: 0;
          |  }
          |}
          |
          |.${model.entityName.kebabCase}-accordion {
          |  .mat-expansion-panel {
          |    margin-bottom: 16px;
          |  }
          |
          |  .mat-expansion-panel-header {
          |    mat-panel-title {
          |      display: flex;
          |      align-items: center;
          |      gap: 8px;
          |    }
          |  }
          |
          |  mat-icon {
          |    margin-right: 8px;
          |  }
          |}
          |
          |::ng-deep .mat-expansion-panel-body {
          |  padding: 16px 24px 24px !important;
          |}
          |
          |.edit-form-section {
          |  margin-top: 32px;
          |  border-top: 1px solid rgba(0, 0, 0, 0.12);
          |  padding-top: 24px;
          |
          |  h3 {
          |    margin-bottom: 20px;
          |    color: rgba(0, 0, 0, 0.87);
          |  }
          |} 
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-board/${model.entityName.kebabCase}-board.component.scss"
    }
}