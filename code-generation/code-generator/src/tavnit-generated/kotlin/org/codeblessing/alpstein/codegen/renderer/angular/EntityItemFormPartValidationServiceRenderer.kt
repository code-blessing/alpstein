/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemFormPartValidationServiceRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part-validation.service.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-validation.service.ts`
 */
object EntityItemFormPartValidationServiceRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |
          |import {Inject, Injectable, InjectionToken} from '@angular/core';
          |import {ValidatorFn, Validators} from "@angular/forms";
          |import {${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-field-name";
          |import {NamedValidator} from "@app/shared/form-controls/named-validator";
          |import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
          |
          |${ model.item.attributesWithCustomValidation.joinToString("") { attributeWithCustomValidation ->  """export const ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}${attributeWithCustomValidation.attributeName.pascalCase}NamedValidators = new InjectionToken<NamedValidator>('${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}${attributeWithCustomValidation.attributeName.pascalCase}NamedValidators');
              |""" } }
          |
          |@Injectable({providedIn: 'root'})
          |export class ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartValidationService {
          |
          |${ if(model.item.attributesWithCustomValidation.isNotEmpty()) { """    constructor(
              |${ model.item.attributesWithCustomValidation.joinToString("") { attributeWithCustomValidation ->  """        @Inject(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}${attributeWithCustomValidation.attributeName.pascalCase}NamedValidators) private ${attributeWithCustomValidation.attributeName.camelCase}NamedValidators: ReadonlyArray<NamedValidator>,
                  |""" } }    ) {}
              |""" } else { """""" } }
          |    validatorFunctions(field: ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName): Array<ValidatorFn> {
          |        return this.namedValidators(field).map(namedValidator => namedValidator.validatorFunction)
          |    }
          |
          |    validatorNames(field: ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName): Array<ValidatorTranslation> {
          |        return this.namedValidators(field)
          |            .map(namedValidator => this.toValidatorTranslation(namedValidator))
          |    }
          |
          |    private toValidatorTranslation(namedValidator: NamedValidator): ValidatorTranslation {
          |        return {
          |            validatorName: namedValidator.validatorName,
          |            validatorTranslationKey: namedValidator.validatorTranslationKey,
          |            validatorTranslationParams: namedValidator.validatorTranslationParams,
          |        }
          |    }
          |
          |    /**
          |     * t(validator.required)
          |     */
          |    namedValidators(field: ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName): ReadonlyArray<NamedValidator> {
          |        switch(field) {
          |${ model.item.attributes.joinToString("") { attribute ->  """            case ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}: return [
              |${ if(!attribute.isList) { """                {
                  |                    validatorName: "required",
                  |                    validatorFunction: Validators.required,
                  |                    validatorTranslationKey: "validator.required",
                  |                },
                  |""" } else { """""" } }${ if(attribute.hasCustomValidation) { """                ...this.${attribute.attributeName.camelCase}NamedValidators,
                  |""" } else { """""" } }            ]
              |""" } }            default: return []
          |        }
          |    };
          |} 
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-validation.service.ts"
    }
}