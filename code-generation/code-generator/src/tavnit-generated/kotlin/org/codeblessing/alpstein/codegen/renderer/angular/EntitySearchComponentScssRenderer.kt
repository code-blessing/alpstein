/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntitySearchComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-search.component.scss`
 * - path: `opus-magnum/opus-magnum-search/opus-magnum-search.component.scss`
 */
object EntitySearchComponentScssRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |.search-fields {
          |  display: flex;
          |  gap: 16px;
          |}
          |
          |mat-form-field {
          |  width: 100%;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-search/${model.entityName.kebabCase}-search.component.scss"
    }
}