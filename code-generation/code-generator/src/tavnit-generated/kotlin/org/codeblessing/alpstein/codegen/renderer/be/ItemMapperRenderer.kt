/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemMapperRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumMapper.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/mapper/SilvaOptionumMapper.kt`
 */
object ItemMapperRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto.mapper
          |
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}WTO
          |${ model.usedEnums.joinToString("") { usedEnum ->  """import org.codeblessing.alpstein.server.restapi.wto.mapper.${usedEnum.enumName.pascalCase}Mapper.toBo
              |import org.codeblessing.alpstein.server.restapi.wto.mapper.${usedEnum.enumName.pascalCase}Mapper.toWto
              |""" } }${ model.directlyNestedItems.joinToString("") { nestedItem ->  """import org.codeblessing.alpstein.server.restapi.wto.mapper.${nestedItem.itemName.pascalCase}Mapper.toBo
              |import org.codeblessing.alpstein.server.restapi.wto.mapper.${nestedItem.itemName.pascalCase}Mapper.toWto
              |""" } }import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}BO
          |
          |/**
          | * Maps between the WTOs (transport layer) and BOs (business layer).
          | */
          |object ${model.itemName.pascalCase}Mapper {
          |
          |    fun ${model.itemName.pascalCase}WTO.toBo(): ${model.itemName.pascalCase}BO = ${model.itemName.pascalCase}BO(
          |${ model.attributes.joinToString("") { attribute ->  """        ${attribute.attributeName.camelCase} = ${attribute.attributeName.camelCase}${attribute.toBoMappingSuffix},
              |""" } }    )
          |
          |    fun ${model.itemName.pascalCase}BO.toWto(): ${model.itemName.pascalCase}WTO = ${model.itemName.pascalCase}WTO(
          |${ model.attributes.joinToString("") { attribute ->  """        ${attribute.attributeName.camelCase} = ${attribute.attributeName.camelCase}${attribute.toWtoMappingSuffix},
              |""" } }    )
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/mapper/${model.itemName.pascalCase}Mapper.kt"
    }
}