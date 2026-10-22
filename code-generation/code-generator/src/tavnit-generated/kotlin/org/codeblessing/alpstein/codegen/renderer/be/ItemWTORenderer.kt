/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemWTORenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumWTO.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/SilvaOptionumWTO.kt`
 */
object ItemWTORenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto
          |
          |${ if(model.containsUuidAttributes) { """import java.util.UUID
              |""" } else { """""" } }
          |/**
          | * Root WTO (Web Transfer Object), mirroring the Angular `${model.itemName.pascalCase}WTO` interface
          | * field by field so it serializes 1:1 for the client.
          | */
          |data class ${model.itemName.pascalCase}WTO(
          |${ model.attributes.joinToString("") { attribute ->  """    val ${attribute.attributeName.camelCase}: ${attribute.wtoAttributeType},
              |""" } })
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/${model.itemName.pascalCase}WTO.kt"
    }
}