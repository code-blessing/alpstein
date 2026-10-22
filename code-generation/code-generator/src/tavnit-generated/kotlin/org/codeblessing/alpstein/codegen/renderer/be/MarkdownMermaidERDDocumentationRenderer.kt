/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.be

import org.codeblessing.alpstein.codegen.renderer.model.be.BeModel

/**
 * Generate the content for the template `MarkdownMermaidERDDocumentationRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `building-block-1-the-vertical-slice.md`
 * - path: `building-block-1-the-vertical-slice.md`
 */
object MarkdownMermaidERDDocumentationRenderer : BeRenderer {

    override fun renderTemplate(model: BeModel): String {
        return """
          |
          |
          |# Entity Relationship Diagram (ERD)
          |
          |## Table of Contents
          |
          |${ model.items.joinToString("") { item ->  """* [${item.itemName.pascalCase}](#${item.itemName.pascalCase})
              |""" } }* [Enumerations](#Enumerations)
          |
          |## Items Overview
          |```mermaid
          |  erDiagram
          |
          |${ model.items.joinToString("") { item ->  """    ${item.itemName.pascalCase} { }
              |${ item.attributesWithItemType.joinToString("") { attribute ->  """    ${item.itemName.pascalCase} ||${attribute.mermaidItemRelationship} ${attribute.referencedItem.itemName.pascalCase} : ${attribute.attributeName.pascalCase}
                  |""" } }${ item.attributesWithEnumType.joinToString("") { attribute ->  """    ${item.itemName.pascalCase} ||${attribute.mermaidItemRelationship} ${attribute.enum.enumName.pascalCase} : ${attribute.attributeName.pascalCase}
                  |""" } }${ item.attributesWithItemReference.joinToString("") { attribute ->  """    ${item.itemName.pascalCase} }o${attribute.mermaidItemRelationship} ${attribute.referencedItem.itemName.pascalCase} : ${attribute.attributeName.pascalCase}
                  |""" } }""" } }${ model.enums.joinToString("") { enumDefinition ->  """    ${enumDefinition.enumName.pascalCase} { }
              |""" } }```
          |
          |## Items and Enumerations
          |The system consists of the following items and enumerations:
          |${ model.items.joinToString("") { item ->  """
              |### ${item.itemName.pascalCase}
              |
              |```mermaid
              |  erDiagram
              |
              |
              |    ${item.itemName.pascalCase} {
              |${ item.attributes.joinToString("") { attribute ->  """      ${attribute.attributeName.pascalCase} ${attribute.mermaidItemTypeDefinition} "${attribute.mermaidItemAdditionalInformation(item.idAttribute)}"
                  |""" } }    }
              |
              |
              |${ item.attributesWithItemType.joinToString("") { attribute ->  """    ${item.itemName.pascalCase} ||${attribute.mermaidItemRelationship} ${attribute.referencedItem.itemName.pascalCase} : ${attribute.attributeName.pascalCase}
                  |""" } }${ item.attributesWithEnumType.joinToString("") { attribute ->  """    ${item.itemName.pascalCase} ||${attribute.mermaidItemRelationship} ${attribute.enum.enumName.pascalCase} : ${attribute.attributeName.pascalCase}
                  |""" } }${ item.attributesWithItemReference.joinToString("") { attribute ->  """    ${item.itemName.pascalCase} }o${attribute.mermaidItemRelationship} ${attribute.referencedItem.itemName.pascalCase} : ${attribute.attributeName.pascalCase}
                  |""" } }```
              |""" } }
          |
          |### Enumerations
          |```mermaid
          |  erDiagram
          |
          |${ model.enums.joinToString("") { enumDefinition ->  """    ${enumDefinition.enumName.pascalCase} {
              |${ enumDefinition.enumValues.joinToString("") { enumValue ->  """        ${enumValue.pascalCase} enum
                  |""" } }    }
              |""" } }```
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: BeModel): String {
      return "ERD-model.md"
    }
}