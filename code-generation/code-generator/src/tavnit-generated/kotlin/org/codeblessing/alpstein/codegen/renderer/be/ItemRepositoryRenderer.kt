/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemRepositoryRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumRepository.kt`
 * - path: `org/codeblessing/alpstein/server/service/silvaoptionum/SilvaOptionumRepository.kt`
 */
object ItemRepositoryRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.service.${model.itemName.lowerCase}
          |
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}BO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}ByIdsCriteriaBO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}SearchCriteriaBO
          |${ if(model.hasUuidPrimaryKey) { """import java.util.${model.primaryKeyAttribute.kotlinAttributeType}
              |""" } else { """""" } }
          |/**
          | * Port for persisting the ${model.itemName.pascalCase} root object [${model.itemName.pascalCase}BO]. The implementation
          | * lives in the persistence module, so the service (business) layer stays independent of
          | * any persistence technology.
          | *
          | * Always operates on the whole [${model.itemName.pascalCase}BO] aggregate, never on nested items.
          | */
          |interface ${model.itemName.pascalCase}Repository {
          |
          |    fun findAll(): List<${model.itemName.pascalCase}BO>
          |
          |    fun findById(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType}): ${model.itemName.pascalCase}BO?
          |
          |    fun findByIds(criteria: ${model.itemName.pascalCase}ByIdsCriteriaBO): List<${model.itemName.pascalCase}BO>
          |
          |    fun search(searchCriteria: ${model.itemName.pascalCase}SearchCriteriaBO): List<${model.itemName.pascalCase}BO>
          |
          |    /**
          |     * Stores a ${model.itemName.pascalCase} that does not exist yet and returns it under the primary key the
          |     * persistence handed out for it. Handing out identity is the job of the persistence, like a
          |     * sequence of a database, so the key the given aggregate carries is ignored.
          |     */
          |    fun create(${model.itemName.camelCase}: ${model.itemName.pascalCase}BO): ${model.itemName.pascalCase}BO
          |
          |    fun update(${model.itemName.camelCase}: ${model.itemName.pascalCase}BO): ${model.itemName.pascalCase}BO
          |
          |    fun deleteById(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType})
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/service/${model.itemName.lowerCase}/${model.itemName.pascalCase}Repository.kt"
    }
}