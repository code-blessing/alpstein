/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemControllerRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumController.kt`
 * - path: `org/codeblessing/alpstein/server/restapi/silvaoptionum/SilvaOptionumController.kt`
 */
object ItemControllerRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.restapi.${model.itemName.lowerCase}
          |
          |import org.springframework.web.bind.annotation.*
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}ByIdsCriteriaWTO
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}ByIdsResultWTO
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}SearchCriteriaWTO
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}SearchResultWTO
          |import org.codeblessing.alpstein.server.restapi.wto.${model.itemName.pascalCase}WTO
          |import org.codeblessing.alpstein.server.restapi.wto.mapper.${model.itemName.pascalCase}Mapper
          |import org.codeblessing.alpstein.server.restapi.wto.mapper.${model.itemName.pascalCase}ByIdsCriteriaMapper.toBo
          |import org.codeblessing.alpstein.server.restapi.wto.mapper.${model.itemName.pascalCase}SearchCriteriaMapper.toBo
          |import org.codeblessing.alpstein.server.restapi.wto.mapper.${model.itemName.pascalCase}Mapper.toBo
          |import org.codeblessing.alpstein.server.restapi.wto.mapper.${model.itemName.pascalCase}Mapper.toWto
          |import org.codeblessing.alpstein.server.service.${model.itemName.lowerCase}.${model.itemName.pascalCase}Service
          |${ if(model.hasUuidPrimaryKey) { """import java.util.${model.primaryKeyAttribute.kotlinAttributeType}
              |""" } else { """""" } }
          |/**
          | * REST endpoints of the ${model.itemName.pascalCase} business context. Served under `/api/${model.itemName.kebabCase}`
          | * (the `/api` prefix is added by `WebConfig`) and consumed by the Angular
          | * `${model.itemName.pascalCase}Service`.
          | *
          | * The controller speaks WTOs to the outside world, maps them to BOs via
          | * [${model.itemName.pascalCase}Mapper] and always calls the [${model.itemName.pascalCase}Service] with the whole
          | * `${model.itemName.pascalCase}` aggregate.
          | */
          |@RestController
          |@RequestMapping("/${model.itemName.kebabCase}")
          |class ${model.itemName.pascalCase}Controller(
          |    private val ${model.itemName.camelCase}Service: ${model.itemName.pascalCase}Service,
          |) {
          |
          |    @GetMapping
          |    fun get${model.itemName.pascalCase}List(): List<${model.itemName.pascalCase}WTO> =
          |        ${model.itemName.camelCase}Service.get${model.itemName.pascalCase}List().map { it.toWto() }
          |
          |    @PostMapping("/search")
          |    fun search${model.itemName.pascalCase}List(@RequestBody searchCriteria: ${model.itemName.pascalCase}SearchCriteriaWTO): ${model.itemName.pascalCase}SearchResultWTO =
          |        ${model.itemName.pascalCase}SearchResultWTO(
          |            ${model.itemName.camelCase}List = ${model.itemName.camelCase}Service.search${model.itemName.pascalCase}List(searchCriteria.toBo()).map { it.toWto() },
          |        )
          |
          |    /**
          |     * Resolves a whole set of references to this item at once, so that the client can show
          |     * stored identifiers by their display attributes instead of the bare primary keys.
          |     */
          |    @PostMapping("/by-ids")
          |    fun get${model.itemName.pascalCase}ListByIds(@RequestBody criteria: ${model.itemName.pascalCase}ByIdsCriteriaWTO): ${model.itemName.pascalCase}ByIdsResultWTO =
          |        ${model.itemName.pascalCase}ByIdsResultWTO(
          |            ${model.itemName.camelCase}List = ${model.itemName.camelCase}Service.get${model.itemName.pascalCase}ListByIds(criteria.toBo()).map { it.toWto() },
          |        )
          |
          |    @GetMapping("/{${model.primaryKeyAttribute.attributeName.camelCase}}")
          |    fun get${model.itemName.pascalCase}ById(@PathVariable ${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType}): ${model.itemName.pascalCase}WTO? =
          |        ${model.itemName.camelCase}Service.get${model.itemName.pascalCase}ById(${model.primaryKeyAttribute.attributeName.camelCase})?.toWto()
          |
          |    @PostMapping
          |    fun create${model.itemName.pascalCase}(@RequestBody ${model.itemName.camelCase}: ${model.itemName.pascalCase}WTO): ${model.itemName.pascalCase}WTO {
          |        val created = ${model.itemName.camelCase}Service.create${model.itemName.pascalCase}(${model.itemName.camelCase}.toBo())
          |        return created.toWto()
          |    }
          |
          |    @PutMapping("/{${model.primaryKeyAttribute.attributeName.camelCase}}")
          |    fun update${model.itemName.pascalCase}(
          |        @PathVariable ${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType},
          |        @RequestBody ${model.itemName.camelCase}: ${model.itemName.pascalCase}WTO,
          |    ): ${model.itemName.pascalCase}WTO {
          |        val toUpdate = ${model.itemName.camelCase}.toBo().copy(${model.primaryKeyAttribute.attributeName.camelCase} = ${model.primaryKeyAttribute.attributeName.camelCase})
          |        return ${model.itemName.camelCase}Service.update${model.itemName.pascalCase}(toUpdate).toWto()
          |    }
          |
          |    @DeleteMapping("/{${model.primaryKeyAttribute.attributeName.camelCase}}")
          |    fun delete${model.itemName.pascalCase}(@PathVariable ${model.primaryKeyAttribute.attributeName.camelCase}: ${model.primaryKeyAttribute.kotlinAttributeType}) =
          |        ${model.itemName.camelCase}Service.delete${model.itemName.pascalCase}(${model.primaryKeyAttribute.attributeName.camelCase})
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/restapi/${model.itemName.lowerCase}/${model.itemName.pascalCase}Controller.kt"
    }
}