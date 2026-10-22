/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemExampleDataCreatorRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumExampleDataCreator.kt`
 * - path: `org/codeblessing/alpstein/server/exampledata/bo/SilvaOptionumExampleDataCreator.kt`
 */
object ItemExampleDataCreatorRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.exampledata.bo
          |
          |import org.springframework.stereotype.Component
          |import org.codeblessing.alpstein.server.exampledata.DataContext
          |import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}BO
          |${ model.exampleDataGeneratorConfigs.joinToString("") { exampleDataGeneratorConfig ->  """import org.codeblessing.alpstein.server.exampledata.framework.datagenerator.${exampleDataGeneratorConfig.generatorNamePrefix.pascalCase}DataGenerator
              |""" } }${ model.referencedItems.joinToString("") { referencedItem ->  """import org.codeblessing.alpstein.server.exampledata.${referencedItem.itemName.lowerCase}.${referencedItem.itemName.pascalCase}ExampleDataFetcher
              |""" } }
          |
          |
          |/**
          | * Creates example data for the business object [${model.itemName.pascalCase}BO].
          | *
          | * Delegates the creation of nested objects to the dedicated example data creators of the
          | * respective business objects.
          | */
          |@Component
          |class ${model.itemName.pascalCase}ExampleDataCreator(
          |${ model.directlyNestedItems.joinToString("") { nestedItem ->  """    private val ${nestedItem.itemName.camelCase}ExampleDataCreator: ${nestedItem.itemName.pascalCase}ExampleDataCreator,
              |""" } }${ model.usedEnums.joinToString("") { usedEnum ->  """    private val ${usedEnum.enumName.camelCase}ExampleDataCreator: ${usedEnum.enumName.pascalCase}ExampleDataCreator,
              |""" } }${ model.exampleDataGeneratorConfigs.joinToString("") { exampleDataGeneratorConfig ->  """    private val ${exampleDataGeneratorConfig.generatorNamePrefix.camelCase}DataGenerator: ${exampleDataGeneratorConfig.generatorNamePrefix.pascalCase}DataGenerator,
              |""" } }${ model.referencedItems.joinToString("") { referencedItem ->  """    private val ${referencedItem.itemName.camelCase}ExampleDataFetcher: ${referencedItem.itemName.pascalCase}ExampleDataFetcher,
              |""" } }) {
          |
          |    fun create(dataContext: DataContext): ${model.itemName.pascalCase}BO = ${model.itemName.pascalCase}BO(
          |${ model.builtInAttributes.filter { !it.isItemReference }.joinToString("") { builtInAttribute ->  """${ if(builtInAttribute.isList) { """        ${builtInAttribute.attributeName.camelCase} = ${builtInAttribute.exampleDataGeneratorConfig.generatorNamePrefix.camelCase}DataGenerator.generateDataList(dataContext, size = FakerHelper.innerListRandomSize(dataContext)),
                  |""" } else { """        ${builtInAttribute.attributeName.camelCase} = ${builtInAttribute.exampleDataGeneratorConfig.generatorNamePrefix.camelCase}DataGenerator.generateData(dataContext),
                  |""" } }""" } }${ model.attributesWithItemType.joinToString("") { itemAttribute ->  """${ if(itemAttribute.isList) { """        ${itemAttribute.attributeName.camelCase} = ${itemAttribute.referencedItem.itemName.camelCase}ExampleDataCreator.createList(dataContext, FakerHelper.innerListRandomSize(dataContext)),
                  |""" } else { """        ${itemAttribute.attributeName.camelCase} = ${itemAttribute.referencedItem.itemName.camelCase}ExampleDataCreator.create(dataContext),
                  |""" } }
              |""" } }${ model.attributesWithEnumType.joinToString("") { enumAttribute ->  """${ if(enumAttribute.isList) { """        ${enumAttribute.attributeName.camelCase} = ${enumAttribute.enum.enumName.camelCase}ExampleDataCreator.createList(dataContext, FakerHelper.innerListRandomSize(dataContext)),
                  |""" } else { """        ${enumAttribute.attributeName.camelCase} = ${enumAttribute.enum.enumName.camelCase}ExampleDataCreator.create(dataContext),
                  |""" } }""" } }${ model.attributesWithItemReference.joinToString("") { referenceAttribute ->  """${ if(referenceAttribute.isList) { """        ${referenceAttribute.attributeName.camelCase} = ${referenceAttribute.referencedItem.itemName.camelCase}ExampleDataFetcher.fetchRandomKeysList(dataContext),
                  |""" } else { """        ${referenceAttribute.attributeName.camelCase} = ${referenceAttribute.referencedItem.itemName.camelCase}ExampleDataFetcher.fetchRandomKey(dataContext),
                  |""" } }""" } }    )
          |
          |    fun createList(dataContext: DataContext, size: Int): List<${model.itemName.pascalCase}BO> =
          |        List( size = size) { create(dataContext) }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/exampledata/bo/${model.itemName.pascalCase}ExampleDataCreator.kt"
    }
}