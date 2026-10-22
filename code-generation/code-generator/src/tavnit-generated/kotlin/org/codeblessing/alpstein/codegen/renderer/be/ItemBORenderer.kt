/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemBORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumBO.kt`
 * - path: `org/codeblessing/alpstein/server/service/bo/SilvaOptionumBO.kt`
 */
object ItemBORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.service.bo
          |
          |${ if(model.containsUuidAttributes) { """import java.util.UUID
              |""" } else { """""" } }
          |/**
          | * Business object for the [${model.itemName.pascalCase}BO] item.
          | */
          |data class ${model.itemName.pascalCase}BO(
          |${ model.attributes.joinToString("") { attribute ->  """    val ${attribute.attributeName.camelCase}: ${attribute.kotlinAttributeType},
              |""" } })
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/service/bo/${model.itemName.pascalCase}BO.kt"
    }
}