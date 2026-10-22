/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemFormPartInitialValueServiceRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part-initial-value.service.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-initial-value.service.ts`
 */
object EntityItemFormPartInitialValueServiceRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |
          |import {Injectable} from '@angular/core';
          |import {FormGroup} from "@angular/forms";
          |${ if(model.item.containsUuidAttributes) { """import {UUID} from "@app/shared/uuid";
              |""" } else { """""" } }
          |${ model.item.directlyNestedItems.joinToString("") { directlyNestedItem ->  """
              |import {
              |    ${model.entity.entityName.pascalCase}${directlyNestedItem.itemName.pascalCase}FormPartGroup
              |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${directlyNestedItem.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${directlyNestedItem.itemName.kebabCase}-form-part-group";
              |""" } }${ model.item.usedEnums.joinToString("") { usedEnum ->  """import {${usedEnum.enumName.pascalCase}Enum} from "@app/wto/${usedEnum.enumName.kebabCase}.enum";
              |""" } }
          |@Injectable({providedIn: 'root'})
          |export class ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartInitialValueService {
          |${ model.item.attributesWithAngularFormInitialValues.joinToString("") { attribute ->  """    ${attribute.attributeName.camelCase}InitialValue(): ${attribute.angularInitialValueFormType(model.entity.entityName)} {
              |        return ${attribute.angularFormInitialValue}
              |    }
              |""" } }}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-initial-value.service.ts"
    }
}