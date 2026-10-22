/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemFormPartFieldNameRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part-field-name.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-field-name.ts`
 */
object EntityItemFormPartFieldNameRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |
          |
          |export enum ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName {
          |${ model.item.attributes.joinToString("") { attribute ->  """    ${attribute.attributeName.camelCase} = "${attribute.attributeName.camelCase}",
              |${ if(attribute.isNullable) { """    ${attribute.attributeName.camelCase}IsNotNull = "${attribute.attributeName.camelCase}IsNotNull",
                  |""" } else { """""" } }""" } }}
          |
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-field-name.ts"
    }
}