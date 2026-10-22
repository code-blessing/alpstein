/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeEnumModel

/**
 * Generate the content for the template `EnumBORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `AppellatioComis.kt`
 * - path: `org/codeblessing/alpstein/server/service/bo/AppellatioComis.kt`
 */
object EnumBORenderer : BeEnumRenderer {

    override fun renderTemplate(model: BeEnumModel): String {
        return """
          |package org.codeblessing.alpstein.server.service.bo
          |
          |/**
          | * Business enum [${model.enumName.pascalCase}]. Pure business representation,
          | * independent of the WTO layer used for transport.
          | */
          |enum class ${model.enumName.pascalCase} {
          |${ model.enumValues.joinToString("") { enumValue ->  """    ${enumValue.screamingSnakeCase},
              |""" } }}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeEnumModel): String {
      return "org/codeblessing/alpstein/server/service/bo/${model.enumName.pascalCase}.kt"
    }
}