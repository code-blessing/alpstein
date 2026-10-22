/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemSearchResultWTORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumSearchResultWTO.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/SilvaOptionumSearchResultWTO.kt`
 */
object ItemSearchResultWTORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto
          |
          |data class ${model.itemName.pascalCase}SearchResultWTO(
          |    val ${model.itemName.camelCase}List: List<${model.itemName.pascalCase}WTO>,
          |)
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/${model.itemName.pascalCase}SearchResultWTO.kt"
    }
}