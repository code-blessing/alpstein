/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemByIdsResultWTORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumByIdsResultWTO.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/SilvaOptionumByIdsResultWTO.kt`
 */
object ItemByIdsResultWTORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto
          |
          |data class ${model.itemName.pascalCase}ByIdsResultWTO(
          |    val ${model.itemName.camelCase}List: List<${model.itemName.pascalCase}WTO>,
          |)
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/${model.itemName.pascalCase}ByIdsResultWTO.kt"
    }
}