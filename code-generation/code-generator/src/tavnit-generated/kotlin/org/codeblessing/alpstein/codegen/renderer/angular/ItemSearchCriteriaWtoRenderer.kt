/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemSearchCriteriaWtoRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `silva-optionum-search-criteria.wto.ts`
 * - path: `wto/silva-optionum-search-criteria.wto.ts`
 */
object ItemSearchCriteriaWtoRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |
          |/**
          | * The ${model.itemName.pascalCase} search criteria WTO (Web Transfer Object), sent as the request body
          | * of the search endpoint. A blank query matches every ${model.itemName.pascalCase}.
          | */
          |export interface ${model.itemName.pascalCase}SearchCriteriaWTO {
          |    query: string;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "wto/${model.itemName.kebabCase}-search-criteria.wto.ts"
    }
}