/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel

/**
 * Generate the content for the template `EnumI18nComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `appellatio-comis-i18n.component.html`
 * - path: `enum/appellatio-comis-i18n/appellatio-comis-i18n.component.html`
 */
object EnumI18nComponentHtmlRenderer : UiEnumRenderer {

    override fun renderTemplate(model: UiEnumModel): String {
        return """
          |<ng-container>
          |@switch (enumValue) {
          |${ model.enumValues.joinToString("") { enumValue ->  """    @case (${enumValue.screamingSnakeCase}) {
              |        {{'enum.${model.enumName.camelCase}.${enumValue.screamingSnakeCase}' | transloco }}
              |    }
              |""" } }    @default {
          |        {{'enum.noEnum' | transloco }}
          |    }
          |}
          |</ng-container>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEnumModel): String {
      return "enum/${model.enumName.kebabCase}-i18n/${model.enumName.kebabCase}-i18n.component.html"
    }
}