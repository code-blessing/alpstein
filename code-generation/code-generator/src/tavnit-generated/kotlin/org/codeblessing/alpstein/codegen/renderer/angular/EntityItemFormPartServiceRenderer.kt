/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemFormPartServiceRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part.service.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part.service.ts`
 */
object EntityItemFormPartServiceRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |
          |import {Injectable} from '@angular/core';
          |import {${model.item.itemName.pascalCase}WTO} from "@app/wto/${model.item.itemName.kebabCase}.wto";
          |import {FormArray, FormControl, FormGroup} from "@angular/forms";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartValidationService
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-validation.service";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartInitialValueService
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-initial-value.service";
          |import {${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-group";
          |import {${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-field-name";
          |import {
          |    registerFormPartValidationTranslations
          |} from "@app/shared/form-controls/form-part-validation-translations";
          |${ if(model.item.containsUuidAttributes) { """import {UUID} from "@app/shared/uuid";
              |""" } else { """""" } }
          |${ model.item.usedEnums.joinToString("") { usedEnum ->  """import {${usedEnum.enumName.pascalCase}Enum} from "@app/wto/${usedEnum.enumName.kebabCase}.enum";
              |""" } }
          |${ model.item.directlyNestedItems.joinToString("") { nestedItem ->  """import {
              |    ${model.entity.entityName.pascalCase}${nestedItem.itemName.pascalCase}FormPartService
              |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-form-part.service";
              |""" } }
          |@Injectable({providedIn: 'root'})
          |export class ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartService {
          |
          |    constructor(
          |        private ${model.item.itemName.camelCase}FormValidationService: ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartValidationService,
          |        private ${model.item.itemName.camelCase}FormInitialValueService: ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartInitialValueService,
          |${ model.item.directlyNestedItems.joinToString("") { nestedItem ->  """        private ${nestedItem.itemName.camelCase}FormPartService: ${model.entity.entityName.pascalCase}${nestedItem.itemName.pascalCase}FormPartService,
              |""" } }    ) {}
          |
          |    public createInitial${model.item.itemName.pascalCase}Form(): FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup> {
          |        const ${model.item.itemName.camelCase}Form = new FormGroup({
          |${ model.item.attributes.joinToString("") { attribute ->  """${ if(!attribute.isItem && !attribute.isList && !attribute.isItemReference) { """            [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}]: new ${attribute.angularFormControlType(model.entity.entityName)}(
                  |                this.${model.item.itemName.camelCase}FormInitialValueService.${attribute.attributeName.camelCase}InitialValue(),
                  |                {
                  |                    nonNullable: true,
                  |                    validators: this.${model.item.itemName.camelCase}FormValidationService.validatorFunctions(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase})
                  |                },
                  |            ),
                  |""" } else { """""" } }""" } }${ model.item.attributesWithItemReference.filter { !it.isList }.joinToString("") { referenceAttribute ->  """            [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${referenceAttribute.attributeName.camelCase}]: new ${referenceAttribute.angularFormControlType(model.entity.entityName)}(
              |                null,
              |                {
              |                    validators: this.${model.item.itemName.camelCase}FormValidationService.validatorFunctions(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${referenceAttribute.attributeName.camelCase})
              |                },
              |            ),
              |""" } }${ model.item.attributesWithItemType.filter { !it.isList }.joinToString("") { attributeWithItemType ->  """            [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}]: this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.createInitial${attributeWithItemType.referencedItem.itemName.pascalCase}Form(),
              |""" } }${ model.item.attributesWithItemType.filter { it.isList }.joinToString("") { attributeWithItemType ->  """            [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}]: new FormArray(
              |                this.${model.item.itemName.camelCase}FormInitialValueService.${attributeWithItemType.attributeName.camelCase}InitialValue(),
              |                {
              |                    validators: this.${model.item.itemName.camelCase}FormValidationService.validatorFunctions(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase})
              |                },
              |            ),
              |""" } }${ model.item.builtInTypeAndEnumAttributes.filter { it.isList }.joinToString("") { attribute ->  """            [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}]: new ${attribute.angularFormControlTypeWithCollection(model.entity.entityName)}(
              |                [],
              |                {
              |                    validators: this.${model.item.itemName.camelCase}FormValidationService.validatorFunctions(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase})
              |                },
              |            ),
              |""" } }${ model.item.attributes.joinToString("") { attribute ->  """${ if(attribute.isNullable) { """            [${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull]: new FormControl<boolean>(
                  |                false,
                  |                {
                  |                    nonNullable: true,
                  |                    validators: this.${model.item.itemName.camelCase}FormValidationService.validatorFunctions(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull)
                  |                },
                  |            ),
                  |""" } else { """""" } }""" } }
          |        });
          |        return registerFormPartValidationTranslations(${model.item.itemName.camelCase}Form, this.${model.item.itemName.camelCase}FormValidationService);
          |    }
          |
          |${ model.item.builtInTypeAndEnumAttributes.filter { it.isList }.joinToString("") { attribute ->  """    public createInitial${attribute.attributeName.pascalCase}Form(): ${attribute.angularFormControlType(model.entity.entityName)} {
              |        return new ${attribute.angularFormControlType(model.entity.entityName)}(
              |            this.${model.item.itemName.camelCase}FormInitialValueService.${attribute.attributeName.camelCase}InitialValue(),
              |            {
              |                nonNullable: true,
              |                validators: this.${model.item.itemName.camelCase}FormValidationService.validatorFunctions(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase})
              |            },
              |        )
              |    }
              |""" } }
          |
          |    public patch${model.item.itemName.pascalCase}Form(form: FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>, ${model.item.itemName.camelCase}: ${model.item.itemName.pascalCase}WTO): void {
          |        this.patchPreparation(form, ${model.item.itemName.camelCase});
          |
          |${ model.item.attributes.joinToString("") { attribute ->  """${ if(!attribute.isNullable) { """        form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}].patchValue(${model.item.itemName.camelCase}.${attribute.attributeName.camelCase});
                  |""" } else { """""" } }${ if(attribute.isNullable) { """        if(${model.item.itemName.camelCase}.${attribute.attributeName.camelCase} != null) {
                  |            form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull].patchValue(true);
                  |            form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}].patchValue(${model.item.itemName.camelCase}.${attribute.attributeName.camelCase});
                  |        } else {
                  |            form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull].patchValue(false);
                  |        }
                  |""" } else { """""" } }""" } }
          |        this.patchNestedItems(form, ${model.item.itemName.camelCase});
          |    }
          |
          |    /**
          |     * patchValue does not create missing FormGroups inside the FormArray.
          |     * So if your FormArray is empty (or shorter than the incoming data), nothing (or only the first N) gets patched.
          |     * We need to prefill the FormArray with empty values first
          |     */
          |    private patchPreparation(form: FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>, ${model.item.itemName.camelCase}: ${model.item.itemName.pascalCase}WTO): void {
          |${ model.item.attributesWithItemType.filter { it.isList }.joinToString("") { attributeWithItemType ->  """        if(${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase} != null) {
              |            const ${attributeWithItemType.attributeName.camelCase}Length = form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}].controls.length
              |            if (${attributeWithItemType.attributeName.camelCase}Length < ${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase}.length) {
              |                for (let i = ${attributeWithItemType.attributeName.camelCase}Length; i < ${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase}.length; i++) {
              |                    form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}].push(this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.createInitial${attributeWithItemType.referencedItem.itemName.pascalCase}Form())
              |                }
              |            }
              |        }
              |""" } }${ model.item.builtInTypeAndEnumAttributes.filter { it.isList }.joinToString("") { attribute ->  """        if(${model.item.itemName.camelCase}.${attribute.attributeName.camelCase} != null) {
              |            const ${attribute.attributeName.camelCase}Length = form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}].controls.length
              |            if (${attribute.attributeName.camelCase}Length < ${model.item.itemName.camelCase}.${attribute.attributeName.camelCase}.length) {
              |                for (let i = ${attribute.attributeName.camelCase}Length; i < ${model.item.itemName.camelCase}.${attribute.attributeName.camelCase}.length; i++) {
              |                    form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}].push(this.createInitial${attribute.attributeName.pascalCase}Form())
              |                }
              |            }
              |        }
              |""" } }    }
          |
          |
          |    private patchNestedItems(form: FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>, ${model.item.itemName.camelCase}: ${model.item.itemName.pascalCase}WTO): void {
          |${ model.item.attributesWithItemType.filter { it.isList }.joinToString("") { attributeWithItemType ->  """        if(${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase} != null) {
              |            for (let i = 0; i < ${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase}.length; i++) {
              |                this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.patch${attributeWithItemType.referencedItem.itemName.pascalCase}Form(
              |                    form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}].at(i),
              |                    ${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase}[i]
              |                )
              |            }
              |        }
              |""" } }
          |${ model.item.attributesWithItemType.filter { !it.isList }.joinToString("") { attributeWithItemType ->  """${ if(attributeWithItemType.isNullable) { """        if(${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase} != null) {
                  |            this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.patch${attributeWithItemType.referencedItem.itemName.pascalCase}Form(form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}], ${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase})
                  |        }
                  |""" } else { """        this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.patch${attributeWithItemType.referencedItem.itemName.pascalCase}Form(form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}], ${model.item.itemName.camelCase}.${attributeWithItemType.attributeName.camelCase})
                  |""" } }""" } }    }
          |
          |    public create${model.item.itemName.pascalCase}WTOFromForm(form: FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>): ${model.item.itemName.pascalCase}WTO {
          |        return {
          |${ model.item.attributesWithItemType.joinToString("") { attributeWithItemType ->  """${ if(attributeWithItemType.isList) { """${ if(attributeWithItemType.isNullable) { """            ${attributeWithItemType.attributeName.camelCase}: form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}IsNotNull].value
                      |                ? form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}].controls.map(
                      |                    (controlEntry) => this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.create${attributeWithItemType.referencedItem.itemName.pascalCase}WTOFromForm(controlEntry))
                      |                : null,
                      |""" } else { """            ${attributeWithItemType.attributeName.camelCase}: form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}].controls.map(
                      |                (controlEntry) => this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.create${attributeWithItemType.referencedItem.itemName.pascalCase}WTOFromForm(controlEntry)
                      |            ),
                      |""" } }""" } else { """${ if(attributeWithItemType.isNullable) { """            ${attributeWithItemType.attributeName.camelCase}: form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}IsNotNull].value
                      |                ? this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.create${attributeWithItemType.referencedItem.itemName.pascalCase}WTOFromForm(form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}])
                      |                : null,
                      |""" } else { """            ${attributeWithItemType.attributeName.camelCase}: this.${attributeWithItemType.referencedItem.itemName.camelCase}FormPartService.create${attributeWithItemType.referencedItem.itemName.pascalCase}WTOFromForm(form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attributeWithItemType.attributeName.camelCase}]),
                      |""" } }""" } }
              |""" } }
          |${ model.item.builtInTypeAndEnumAttributes.joinToString("") { attribute ->  """${ if(attribute.isNullable) { """            ${attribute.attributeName.camelCase}: form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull].value
                  |                ? form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}].getRawValue()
                  |                : null,
                  |""" } else { """            // the non-null assertion is only needed for a mandatory reference, whose control holds
                  |            // `null` until an entry is picked; for every other attribute it is a no-op
                  |            ${attribute.attributeName.camelCase}: form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}].getRawValue()!,
                  |""" } }""" } }        };
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part.service.ts"
    }
}