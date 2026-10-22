/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemByIdsCriteriaWtoRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `silva-optionum-by-ids-criteria.wto.ts`
 * - path: `wto/silva-optionum-by-ids-criteria.wto.ts`
 */
object ItemByIdsCriteriaWtoRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |${ if(model.hasUuidPrimaryKey) { """import {${model.primaryKeyAttribute.typescriptAttributeType}} from "@app/shared/uuid";
              |
              |""" } else { """""" } }/**
          | * The ${model.itemName.pascalCase} by-ids criteria WTO (Web Transfer Object), sent as the request body of
          | * the by-ids endpoint. It carries the identifier of every ${model.itemName.pascalCase} to resolve, which is
          | * what a reference to this entity is stored as.
          | */
          |export interface ${model.itemName.pascalCase}ByIdsCriteriaWTO {
          |    ${model.primaryKeyAttribute.attributeName.camelCase}List: Array<${model.primaryKeyAttribute.typescriptAttributeType}>;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "wto/${model.itemName.kebabCase}-by-ids-criteria.wto.ts"
    }
}