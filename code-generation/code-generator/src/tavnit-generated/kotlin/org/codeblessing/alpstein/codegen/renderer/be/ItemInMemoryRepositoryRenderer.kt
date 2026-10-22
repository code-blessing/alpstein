/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemInMemoryRepositoryRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `InMemorySilvaOptionumRepository.kt`
 * - path: `org/codeblessing/alpstein/server/persistence/silvaoptionum/InMemorySilvaOptionumRepository.kt`
 */
object ItemInMemoryRepositoryRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.persistence.${model.itemName.lowerCase}
          |
          |import org.springframework.stereotype.Repository
          |import org.codeblessing.alpstein.server.service.${model.itemName.lowerCase}.${model.itemName.pascalCase}Repository
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}BO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}ByIdsCriteriaBO
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}SearchCriteriaBO
          |import java.util.concurrent.ConcurrentHashMap
          |${ if(model.hasUuidPrimaryKey) { """import java.util.${model.primaryKeyAttribute.kotlinAttributeType}
              |""" } else { """""" } }
          |/**
          | * Simple in-memory implementation of the [${model.itemName.pascalCase}Repository] port defined in the
          | * service module. Holds the [${model.itemName.pascalCase}BO] aggregates in memory only; a real
          | * persistence framework can replace this later without touching the service layer.
          | */
          |@Repository
          |class InMemory${model.itemName.pascalCase}Repository : ${model.itemName.pascalCase}Repository {
          |
          |    private val store = ConcurrentHashMap<${model.primaryKeyAttribute.kotlinAttributeType}, ${model.itemName.pascalCase}BO>()
          |
          |    override fun findAll(): List<${model.itemName.pascalCase}BO> = this.store.values.toList()
          |
          |    override fun findById(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType}): ${model.itemName.pascalCase}BO? = this.store[${model.primaryKeyAttribute.attributeName.camelCase}]
          |
          |    override fun findByIds(criteria: ${model.itemName.pascalCase}ByIdsCriteriaBO): List<${model.itemName.pascalCase}BO> =
          |        criteria.${model.primaryKeyAttribute.attributeName.camelCase}List.mapNotNull { this.store[it] }
          |
          |    override fun search(searchCriteria: ${model.itemName.pascalCase}SearchCriteriaBO): List<${model.itemName.pascalCase}BO> =
          |        this.store.values.filter { it.toString().contains(searchCriteria.query, ignoreCase = true) }
          |
          |    override fun create(${model.itemName.camelCase}: ${model.itemName.pascalCase}BO): ${model.itemName.pascalCase}BO {
          |        val created = ${model.itemName.camelCase}.copy(${model.primaryKeyAttribute.attributeName.camelCase} = nextId())
          |        this.store[created.${model.primaryKeyAttribute.attributeName.camelCase}] = created
          |        return created
          |    }
          |
          |    override fun update(${model.itemName.camelCase}: ${model.itemName.pascalCase}BO): ${model.itemName.pascalCase}BO {
          |        this.store[${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase}] = ${model.itemName.camelCase}
          |        return ${model.itemName.camelCase}
          |    }
          |
          |    override fun deleteById(${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType}) {
          |        this.store.remove(${model.primaryKeyAttribute.attributeName.camelCase})
          |    }
          |
          |    private fun nextId(): ${model.primaryKeyAttribute.kotlinAttributeType} = ${model.nextPrimaryKeyValueExpression}
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/persistence/${model.itemName.lowerCase}/InMemory${model.itemName.pascalCase}Repository.kt"
    }
}