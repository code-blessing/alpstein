/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemTableRowComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-articulus-interior-table-row.model.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-table/opus-magnum-articulus-interior-table-row.model.ts`
 */
object EntityItemTableRowComponentTypescriptRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |
          |import {FormGroup} from "@angular/forms";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-group";
          |
          |export interface ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}TableRow {
          |${ model.item.attributes.joinToString("") { attribute ->  """    ${attribute.attributeName.camelCase}: string
              |""" } }    formGroup: FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table-row.model.ts"
    }
}