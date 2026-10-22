/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel

/**
 * Generate the content for the template `EnumDefinitionTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `appellatio-comis.enum.ts`
 * - path: `wto/appellatio-comis.enum.ts`
 */
object EnumDefinitionTypescriptRenderer : UiEnumRenderer {

    override fun renderTemplate(model: UiEnumModel): String {
        return """
          |export enum ${model.enumName.pascalCase}Enum {
          |${ model.enumValues.joinToString("") { enumValue ->  """    ${enumValue.screamingSnakeCase} = '${enumValue.screamingSnakeCase}',
              |""" } }}
          |
          |export const ${model.enumName.pascalCase}EnumValues: ReadonlyArray<${model.enumName.pascalCase}Enum> = [
          |${ model.enumValues.joinToString("") { enumValue ->  """    ${model.enumName.pascalCase}Enum.${enumValue.screamingSnakeCase},
              |""" } }]
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEnumModel): String {
      return "wto/${model.enumName.kebabCase}.enum.ts"
    }
}