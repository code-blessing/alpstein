/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemByIdsCriteriaWTORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumByIdsCriteriaWTO.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/SilvaOptionumByIdsCriteriaWTO.kt`
 */
object ItemByIdsCriteriaWTORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto
          |
          |${ if(model.hasUuidPrimaryKey) { """import java.util.${model.primaryKeyAttribute.kotlinAttributeType}
              |
              |""" } else { """""" } }data class ${model.itemName.pascalCase}ByIdsCriteriaWTO(
          |    val ${model.primaryKeyAttribute.attributeName.camelCase}List: List<${model.primaryKeyAttribute.kotlinAttributeType}> = emptyList(),
          |)
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/${model.itemName.pascalCase}ByIdsCriteriaWTO.kt"
    }
}