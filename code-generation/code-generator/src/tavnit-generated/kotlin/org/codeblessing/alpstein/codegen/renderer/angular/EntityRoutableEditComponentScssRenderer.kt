/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityRoutableEditComponentScssRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-routable-edit.component.scss`
 * - path: `opus-magnum/opus-magnum-routable-edit/opus-magnum-routable-edit.component.scss`
 */
object EntityRoutableEditComponentScssRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |.${model.entityName.kebabCase}-routable-edit {
          |  padding: 20px;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-routable-edit/${model.entityName.kebabCase}-routable-edit.component.scss"
    }
}