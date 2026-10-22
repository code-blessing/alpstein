/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeEnumModel

/**
 * Generate the content for the template `EnumWTORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `AppellatioComisEnum.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/AppellatioComisEnum.kt`
 */
object EnumWTORenderer : BeEnumRenderer {

    override fun renderTemplate(model: BeEnumModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto
          |
          |/**
          | * WTO (Web Transfer Object) enum mirroring the Angular `${model.enumName.pascalCase}Enum`.
          | */
          |enum class ${model.enumName.pascalCase}Enum {
          |${ model.enumValues.joinToString("") { enumValue ->  """    ${enumValue.screamingSnakeCase},
              |""" } }}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeEnumModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/${model.enumName.pascalCase}Enum.kt"
    }
}