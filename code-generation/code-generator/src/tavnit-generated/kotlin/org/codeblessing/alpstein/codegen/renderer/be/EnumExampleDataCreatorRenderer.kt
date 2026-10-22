/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeEnumModel

/**
 * Generate the content for the template `EnumExampleDataCreatorRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `AppellatioComisExampleDataCreator.kt`
 * - path: `org/codeblessing/alpstein/server/exampledata/bo/AppellatioComisExampleDataCreator.kt`
 */
object EnumExampleDataCreatorRenderer : BeEnumRenderer {

    override fun renderTemplate(model: BeEnumModel): String {
        return """
          |package org.codeblessing.alpstein.server.exampledata.bo
          |
          |import org.springframework.stereotype.Component
          |import org.codeblessing.alpstein.server.exampledata.DataContext
          |import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
          |import org.codeblessing.alpstein.server.exampledata.framework.datagenerator.RandomEnumValueDataGenerator
          |import org.codeblessing.alpstein.server.service.bo.${model.enumName.pascalCase}
          |
          |/**
          | * Creates example data for the [${model.enumName.pascalCase}] business enum.
          | */
          |@Component
          |class ${model.enumName.pascalCase}ExampleDataCreator(
          |    private val randomEnumValueDataGenerator: RandomEnumValueDataGenerator,
          |) {
          |
          |    fun create(dataContext: DataContext): ${model.enumName.pascalCase} =
          |        randomEnumValueDataGenerator.generateData(
          |            dataContext = dataContext,
          |            enumClass = ${model.enumName.pascalCase}::class,
          |        )
          |
          |    fun createList(dataContext: DataContext, size: Int): List<${model.enumName.pascalCase}> =
          |        randomEnumValueDataGenerator.generateDataList(
          |            dataContext = dataContext,
          |            enumClass = ${model.enumName.pascalCase}::class,
          |            size = size,
          |        )
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeEnumModel): String {
      return "org/codeblessing/alpstein/server/exampledata/bo/${model.enumName.pascalCase}ExampleDataCreator.kt"
    }
}