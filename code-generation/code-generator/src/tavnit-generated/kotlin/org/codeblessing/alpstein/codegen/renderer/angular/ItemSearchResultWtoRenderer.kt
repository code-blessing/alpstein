/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemSearchResultWtoRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `silva-optionum-search-result.wto.ts`
 * - path: `wto/silva-optionum-search-result.wto.ts`
 */
object ItemSearchResultWtoRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |import {${model.itemName.pascalCase}WTO} from "@app/wto/${model.itemName.kebabCase}.wto";
          |
          |/**
          | * The ${model.itemName.pascalCase} search result WTO (Web Transfer Object), returned by the search
          | * endpoint. It wraps the found ${model.itemName.pascalCase} instances.
          | */
          |export interface ${model.itemName.pascalCase}SearchResultWTO {
          |    ${model.itemName.camelCase}List: Array<${model.itemName.pascalCase}WTO>;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "wto/${model.itemName.kebabCase}-search-result.wto.ts"
    }
}