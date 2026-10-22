/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemExampleDataPopulatorRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumExampleDataPopulator.kt`
 * - path: `org/codeblessing/alpstein/server/exampledata/silvaoptionum/SilvaOptionumExampleDataPopulator.kt`
 */
object ItemExampleDataPopulatorRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.exampledata.${model.itemName.lowerCase}
          |
          |import org.springframework.stereotype.Component
          |import org.codeblessing.alpstein.server.exampledata.DataContext
          |import org.codeblessing.alpstein.server.exampledata.ExampleDataCreator
          |import org.codeblessing.alpstein.server.exampledata.bo.${model.itemName.pascalCase}ExampleDataCreator
          |import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}BO
          |import org.codeblessing.alpstein.server.service.${model.itemName.lowerCase}.${model.itemName.pascalCase}Repository
          |
          |/**
          | * Orchestrates the creation of ${model.itemName.pascalCase} example data.
          | *
          | * Builds a list of [${model.itemName.pascalCase}BO] aggregates by delegating to the per-business-object
          | * example data creators and persists the result through the [${model.itemName.pascalCase}Repository] port.
          | */
          |@Component
          |class ${model.itemName.pascalCase}ExampleDataPopulator(
          |    private val ${model.itemName.camelCase}ExampleDataCreator: ${model.itemName.pascalCase}ExampleDataCreator,
          |    private val ${model.itemName.camelCase}Repository: ${model.itemName.pascalCase}Repository,
          |): ExampleDataCreator {
          |
          |    /**
          |     * Creates the example [${model.itemName.pascalCase}BO] aggregates and writes each of them to the
          |     * persistence via the [${model.itemName.pascalCase}Repository].
          |     *
          |     * Every aggregate is stored under the key the persistence hands out rather than the one the
          |     * example data creator generated, so that each of them gets a key of its own.
          |     */
          |    override fun createExampleData(dataContext: DataContext) {
          |        val created = ${model.itemName.camelCase}ExampleDataCreator.createList(dataContext, FakerHelper.itemListRandomSize(dataContext))
          |        created.forEach { ${model.itemName.camelCase}Repository.create(it) }
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/exampledata/${model.itemName.lowerCase}/${model.itemName.pascalCase}ExampleDataPopulator.kt"
    }
}