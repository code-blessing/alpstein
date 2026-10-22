/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemByIdsCriteriaBORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumByIdsCriteriaBO.kt`
 * - path: `org/codeblessing/alpstein/server/service/bo/SilvaOptionumByIdsCriteriaBO.kt`
 */
object ItemByIdsCriteriaBORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.service.bo
          |
          |${ if(model.hasUuidPrimaryKey) { """import java.util.${model.primaryKeyAttribute.kotlinAttributeType}
              |
              |""" } else { """""" } }/**
          | * Business object for resolving a whole set of references to ${model.itemName.pascalCase} aggregates at once.
          | *
          | * Holds the identifiers a reference to this item is stored as; unknown ones are simply not
          | * part of the result.
          | */
          |data class ${model.itemName.pascalCase}ByIdsCriteriaBO(
          |    val ${model.primaryKeyAttribute.attributeName.camelCase}List: List<${model.primaryKeyAttribute.kotlinAttributeType}>,
          |)
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/service/bo/${model.itemName.pascalCase}ByIdsCriteriaBO.kt"
    }
}