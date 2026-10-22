/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemByIdsCriteriaMapperRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumByIdsCriteriaMapper.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/mapper/SilvaOptionumByIdsCriteriaMapper.kt`
 */
object ItemByIdsCriteriaMapperRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto.mapper
          |
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}ByIdsCriteriaWTO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}ByIdsCriteriaBO
          |
          |object ${model.itemName.pascalCase}ByIdsCriteriaMapper {
          |
          |    fun ${model.itemName.pascalCase}ByIdsCriteriaWTO.toBo(): ${model.itemName.pascalCase}ByIdsCriteriaBO = ${model.itemName.pascalCase}ByIdsCriteriaBO(
          |        ${model.primaryKeyAttribute.attributeName.camelCase}List = ${model.primaryKeyAttribute.attributeName.camelCase}List,
          |    )
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/mapper/${model.itemName.pascalCase}ByIdsCriteriaMapper.kt"
    }
}