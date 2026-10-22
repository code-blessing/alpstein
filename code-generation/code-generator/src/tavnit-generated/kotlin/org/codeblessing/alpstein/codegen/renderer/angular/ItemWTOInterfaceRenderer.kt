/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemWTOInterfaceRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `silva-optionum.wto.ts`
 * - path: `wto/silva-optionum.wto.ts`
 */
object ItemWTOInterfaceRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |
          |${ model.directlyNestedItems.joinToString("") { nestedItem ->  """import {${nestedItem.itemName.pascalCase}WTO} from "@app/wto/${nestedItem.itemName.kebabCase}.wto";
              |""" } }${ if(model.containsUuidAttributes) { """import {UUID} from "@app/shared/uuid";
              |""" } else { """""" } }${ model.usedEnums.joinToString("") { usedEnum ->  """import {${usedEnum.enumName.pascalCase}Enum} from "@app/wto/${usedEnum.enumName.kebabCase}.enum";
              |""" } }
          |/**
          | * The ${model.itemName.pascalCase} WTO (Web Transfer Object) class.
          | */
          |export interface ${model.itemName.pascalCase}WTO {
          |${ model.attributes.joinToString("") { attribute ->  """    ${attribute.attributeName.camelCase}: ${attribute.typescriptAttributeType};
              |""" } }}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "wto/${model.itemName.kebabCase}.wto.ts"
    }
}