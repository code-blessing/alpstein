/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel

/**
 * Generate the content for the template `ItemExampleDataFetcherRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `SilvaOptionumExampleDataFetcher.kt`
 * - path: `org/codeblessing/alpstein/server/exampledata/silvaoptionum/SilvaOptionumExampleDataFetcher.kt`
 */
object ItemExampleDataFetcherRenderer : BeItemRenderer {

    override fun renderTemplate(model: BeItemModel): String {
        return """
          |package org.codeblessing.alpstein.server.exampledata.${model.itemName.lowerCase}
          |
          |import org.springframework.stereotype.Component
          |import org.codeblessing.alpstein.server.exampledata.DataContext
          |import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
          |import org.codeblessing.alpstein.server.service.bo.${model.itemName.pascalCase}BO
          |import org.codeblessing.alpstein.server.service.${model.itemName.lowerCase}.${model.itemName.pascalCase}Repository
          |${ if(model.hasUuidPrimaryKey) { """import java.util.${model.primaryKeyAttribute.kotlinAttributeType}
              |""" } else { """""" } }
          |/**
          | * Fetches already persisted ${model.itemName.pascalCase} example data so that other example data creators can
          | * reference it.
          | *
          | * Unlike the example data creators, this does not create anything: it reads the
          | * [${model.itemName.pascalCase}BO] instances that the [${model.itemName.pascalCase}ExampleDataPopulator] has written before,
          | * so that a reference is always a valid one. It therefore only returns something once
          | * ${model.itemName.pascalCase} example data has been created.
          | */
          |@Component
          |class ${model.itemName.pascalCase}ExampleDataFetcher(
          |    private val ${model.itemName.camelCase}Repository: ${model.itemName.pascalCase}Repository,
          |) {
          |
          |    /**
          |     * A random subset of the ${model.primaryKeyAttribute.attributeName.camelCase} of the existing [${model.itemName.pascalCase}BO] instances, or an
          |     * empty list if none exist yet.
          |     */
          |    fun fetchRandomKeysList(dataContext: DataContext): List<${model.primaryKeyAttribute.kotlinAttributeType}> =
          |        FakerHelper.manyOfRandom(
          |            dataContext = dataContext,
          |            array = ${model.itemName.camelCase}Repository.findAll().toTypedArray(),
          |            size = FakerHelper.referenceListRandomSize(dataContext),
          |        ).map { it.${model.primaryKeyAttribute.attributeName.camelCase} }
          |
          |    /**
          |     * The ${model.primaryKeyAttribute.attributeName.camelCase} of one random existing [${model.itemName.pascalCase}BO].
          |     *
          |     * A mandatory reference needs a value even where nothing can be referenced yet, for example
          |     * where an item references itself or where the referenced item has not created its
          |     * example data yet. Such a reference gets [UNRESOLVABLE_KEY], which resolves to nothing and
          |     * is therefore shown with the fallback of the display attributes.
          |     */
          |    fun fetchRandomKey(dataContext: DataContext): ${model.primaryKeyAttribute.kotlinAttributeType} {
          |        val ${model.itemName.camelCase}List = ${model.itemName.camelCase}Repository.findAll()
          |        if (${model.itemName.camelCase}List.isEmpty()) {
          |            return UNRESOLVABLE_KEY
          |        }
          |        return FakerHelper.oneRandomOf(
          |            dataContext = dataContext,
          |            array = ${model.itemName.camelCase}List.toTypedArray(),
          |        ).${model.primaryKeyAttribute.attributeName.camelCase}
          |    }
          |
          |    private companion object {
          |        /** A primary key no item is ever identified by. */
          |        private val UNRESOLVABLE_KEY: ${model.primaryKeyAttribute.kotlinAttributeType} = ${model.unresolvablePrimaryKeyValueExpression}
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeItemModel): String {
      return "org/codeblessing/alpstein/server/exampledata/${model.itemName.lowerCase}/${model.itemName.pascalCase}ExampleDataFetcher.kt"
    }
}