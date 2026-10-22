/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeModel

/**
 * Generate the content for the template `ItemsExampleDataCreatorRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `ItemsExampleDataCreator.kt`
 * - path: `org/codeblessing/alpstein/server/exampledata/ItemsExampleDataCreator.kt`
 */
object ItemsExampleDataCreatorRenderer : BeRenderer {

    override fun renderTemplate(model: BeModel): String {
        return """
          |package org.codeblessing.alpstein.server.exampledata
          |
          |import org.springframework.stereotype.Component
          |${ model.itemsWithPrimaryKey.joinToString("") { item ->  """import org.codeblessing.alpstein.server.exampledata.${item.itemName.lowerCase}.${item.itemName.pascalCase}ExampleDataPopulator
              |""" } }
          |@Component
          |class GeneratedItemsExampleDataCreator(
          |${ model.itemsWithPrimaryKey.joinToString("") { item ->  """    private val ${item.itemName.camelCase}ExampleDataPopulator: ${item.itemName.pascalCase}ExampleDataPopulator,
              |""" } }): ExampleDataCreator {
          |
          |    override fun createExampleData(dataContext: DataContext) {
          |        // the order matters: an item that references another one can only pick an existing
          |        // instance, so the referenced item has to create its example data first
          |
          |${ model.itemsWithPrimaryKey.joinToString("") { item ->  """        ${item.itemName.camelCase}ExampleDataPopulator.createExampleData(dataContext)
              |""" } }    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeModel): String {
      return "org/codeblessing/alpstein/server/exampledata/GeneratedItemsExampleDataCreator.kt"
    }
}