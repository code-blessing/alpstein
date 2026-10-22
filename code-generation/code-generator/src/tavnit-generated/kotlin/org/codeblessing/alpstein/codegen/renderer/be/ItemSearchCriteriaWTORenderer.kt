/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemSearchCriteriaWTORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumSearchCriteriaWTO.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/SilvaOptionumSearchCriteriaWTO.kt`
 */
object ItemSearchCriteriaWTORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto
          |
          |data class ${model.itemName.pascalCase}SearchCriteriaWTO(
          |    val query: String = "",
          |)
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/${model.itemName.pascalCase}SearchCriteriaWTO.kt"
    }
}