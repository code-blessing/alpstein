/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemSearchCriteriaBORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumSearchCriteriaBO.kt`
 * - path: `org/codeblessing/alpstein/server/service/bo/SilvaOptionumSearchCriteriaBO.kt`
 */
object ItemSearchCriteriaBORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.service.bo
          |
          |/**
          | * Business object for the search criteria of the ${model.itemName.pascalCase} aggregates.
          | *
          | * Holds a single free text [query]; a blank query matches every [${model.itemName.pascalCase}BO].
          | */
          |data class ${model.itemName.pascalCase}SearchCriteriaBO(
          |    val query: String,
          |)
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/service/bo/${model.itemName.pascalCase}SearchCriteriaBO.kt"
    }
}