/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemSearchCriteriaMapperRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumSearchCriteriaMapper.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/wto/mapper/SilvaOptionumSearchCriteriaMapper.kt`
 */
object ItemSearchCriteriaMapperRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.wto.mapper
          |
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}SearchCriteriaWTO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}SearchCriteriaBO
          |
          |object ${model.itemName.pascalCase}SearchCriteriaMapper {
          |
          |    fun ${model.itemName.pascalCase}SearchCriteriaWTO.toBo(): ${model.itemName.pascalCase}SearchCriteriaBO = ${model.itemName.pascalCase}SearchCriteriaBO(
          |        query = query,
          |    )
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/wto/mapper/${model.itemName.pascalCase}SearchCriteriaMapper.kt"
    }
}