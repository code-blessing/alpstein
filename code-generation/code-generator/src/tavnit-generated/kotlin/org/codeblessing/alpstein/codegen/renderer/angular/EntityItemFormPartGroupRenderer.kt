/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemFormPartGroupRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part-group.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-group.ts`
 */
object EntityItemFormPartGroupRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |
          |import {FormArray, FormControl, FormGroup} from "@angular/forms";
          |import {${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-field-name";
          |${ if(model.item.containsUuidAttributes) { """import {UUID} from "@app/shared/uuid";
              |""" } else { """""" } }${ model.item.usedEnums.joinToString("") { usedEnum ->  """import {${usedEnum.enumName.pascalCase}Enum} from "@app/wto/${usedEnum.enumName.kebabCase}.enum";
              |""" } }
          |${ model.item.directlyNestedItems.joinToString("") { directlyNestedItem ->  """
              |import {
              |    ${model.entity.entityName.pascalCase}${directlyNestedItem.itemName.pascalCase}FormPartGroup
              |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${directlyNestedItem.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${directlyNestedItem.itemName.kebabCase}-form-part-group";
              |""" } }
          |export interface ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup {
          |${ model.item.attributes.joinToString("") { attribute ->  """    [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}]: ${attribute.angularFormControlTypeWithCollection(model.entity.entityName)},
              |${ if(attribute.isNullable) { """    [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull]: FormControl<boolean>,
                  |""" } else { """""" } }""" } }}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-group.ts"
    }
}