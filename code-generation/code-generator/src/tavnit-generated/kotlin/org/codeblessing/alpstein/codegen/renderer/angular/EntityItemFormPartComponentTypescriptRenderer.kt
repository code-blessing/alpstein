/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemFormPartComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-silva-optionum-form-part.component.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part.component.ts`
 */
object EntityItemFormPartComponentTypescriptRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |import {Component, Input, OnInit} from '@angular/core';
          |import {FormArray, FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
          |import {MatButtonModule} from "@angular/material/button";
          |import {MatToolbarModule} from "@angular/material/toolbar";
          |import {MatTableModule} from "@angular/material/table";
          |import {MatCardModule} from "@angular/material/card";
          |import {MatFormFieldModule} from "@angular/material/form-field";
          |import {MatInputModule} from "@angular/material/input";
          |import {MatIconModule} from "@angular/material/icon";
          |import {MatExpansionModule} from "@angular/material/expansion";
          |import {MatSidenavModule} from "@angular/material/sidenav";
          |import {MatListModule} from "@angular/material/list";
          |import {MatDialogModule} from "@angular/material/dialog";
          |import {FormArrayEditState} from "@app/shared/form-controls/form-array-edit-state";
          |import {FieldWrapperComponent} from "@app/shared/form-controls/field-wrapper/field-wrapper.component";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartValidationService
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-validation.service";
          |import {${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName,} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-field-name";
          |${ if(model.item.containsUuidAttributes) { """import {UUID} from "@app/shared/uuid";
              |""" } else { """""" } }${ if(model.item.containsTextAttributes) { """import {TextInputComponent} from "@app/shared/form-controls/text-input/text-input.component";
              |""" } else { """""" } }${ if(model.item.containsBooleanAttributes) { """import {BooleanInputComponent} from "@app/shared/form-controls/boolean-input/boolean-input.component";
              |""" } else { """""" } }${ if(model.item.containsNumberAttributes) { """import {NumberInputComponent} from "@app/shared/form-controls/number-input/number-input.component";
              |""" } else { """""" } }import {DatepickerInputComponent} from "@app/shared/form-controls/datepicker-input/datepicker-input.component";
          |import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
          |import {MatTab, MatTabGroup} from "@angular/material/tabs";
          |import {${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-group";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |${ if(model.containsNamedSectionSplitBlocks()) { """import {SectionSplitterComponent} from "@app/shared/blocks/section-splitter/section-splitter.component";
              |""" } else { """""" } }${ if(model.containsTextBlocks()) { """import {TextBlockComponent} from "@app/shared/blocks/text-block/text-block.component";
              |""" } else { """""" } }${ if(model.item.containsTextListAttributes) { """import {
              |    SingleTextFormFieldTableComponent
              |} from "@app/shared/form-controls/single-text-form-field-table/single-text-form-field-table.component";
              |""" } else { """""" } }${ if(model.item.containsNumberListAttributes) { """import {
              |    SingleNumberFormFieldTableComponent
              |} from "@app/shared/form-controls/single-number-form-field-table/single-number-form-field-table.component";
              |""" } else { """""" } }${ if(model.item.containsBooleanListAttributes) { """import {
              |    SingleBooleanFormFieldTableComponent
              |} from "@app/shared/form-controls/single-boolean-form-field-table/single-boolean-form-field-table.component";
              |""" } else { """""" } }${ model.item.attributesWithEnumType.joinToString("") { attributeWithEnumType ->  """import {${attributeWithEnumType.enum.enumName.pascalCase}Enum} from "@app/wto/${attributeWithEnumType.enum.enumName.kebabCase}.enum";
              |${ if(attributeWithEnumType.isList) { """import {${attributeWithEnumType.enum.enumName.pascalCase}InputTableComponent} from "@app/enum/${attributeWithEnumType.enum.enumName.kebabCase}-input-table/${attributeWithEnumType.enum.enumName.kebabCase}-input-table.component";
                  |""" } else { """import {${attributeWithEnumType.enum.enumName.pascalCase}SelectorComponent} from "@app/enum/${attributeWithEnumType.enum.enumName.kebabCase}-input-selection/${attributeWithEnumType.enum.enumName.kebabCase}-selector.component";
                  |""" } }""" } }
          |
          |${ model.item.directlyNestedItems.joinToString("") { nestedItem ->  """import {
              |    ${model.entity.entityName.pascalCase}${nestedItem.itemName.pascalCase}TableComponent
              |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-table/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-table.component";
              |import {
              |    ${model.entity.entityName.pascalCase}${nestedItem.itemName.pascalCase}FormPartComponent
              |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-form-part.component";
              |import {
              |    ${model.entity.entityName.pascalCase}${nestedItem.itemName.pascalCase}FormPartGroup
              |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${nestedItem.itemName.kebabCase}-form-part-group";
              |""" } }${ model.item.listReferencedItems.joinToString("") { listReferencedItem ->  """import {
              |    ${listReferencedItem.itemName.pascalCase}ReferenceTableComponent
              |} from "@app/reference/${listReferencedItem.itemName.kebabCase}-reference-table/${listReferencedItem.itemName.kebabCase}-reference-table.component";
              |""" } }${ model.item.singleReferencedItems.joinToString("") { singleReferencedItem ->  """import {
              |    ${singleReferencedItem.itemName.pascalCase}ReferenceFieldComponent
              |} from "@app/reference/${singleReferencedItem.itemName.kebabCase}-reference-field/${singleReferencedItem.itemName.kebabCase}-reference-field.component";
              |""" } }
          |@Component({
          |    selector: 'app-${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part',
          |    templateUrl: './${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part.component.html',
          |    styleUrls: ['./${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part.component.scss'],
          |    imports: [
          |        ReactiveFormsModule,
          |        MatButtonModule,
          |        MatToolbarModule,
          |        MatTableModule,
          |        MatCardModule,
          |        MatFormFieldModule,
          |        MatInputModule,
          |        MatIconModule,
          |        MatExpansionModule,
          |        MatSidenavModule,
          |        MatListModule,
          |        MatDialogModule,
          |        FieldWrapperComponent,
          |        TranslocoPipe,
          |${ if(model.tabs.isNotEmpty()) { """        MatTabGroup,
              |        MatTab,
              |""" } else { """""" } }${ if(model.containsNamedSectionSplitBlocks()) { """        SectionSplitterComponent,
              |""" } else { """""" } }${ if(model.containsTextBlocks()) { """        TextBlockComponent,
              |""" } else { """""" } }${ model.item.attributesWithItemType.joinToString("") { attributeWithItemType ->  """${ if(attributeWithItemType.isList) { """        ${model.entity.entityName.pascalCase}${attributeWithItemType.referencedItem.itemName.pascalCase}TableComponent,
                  |""" } else { """""" } }        ${model.entity.entityName.pascalCase}${attributeWithItemType.referencedItem.itemName.pascalCase}FormPartComponent,
              |""" } }${ if(model.item.containsTextAttributes) { """        TextInputComponent,
              |""" } else { """""" } }${ if(model.item.containsBooleanAttributes) { """        BooleanInputComponent,
              |""" } else { """""" } }${ if(model.item.containsNumberAttributes) { """        NumberInputComponent,
              |""" } else { """""" } }${ if(model.item.containsTextListAttributes) { """        SingleTextFormFieldTableComponent,
              |""" } else { """""" } }${ model.item.listReferencedItems.joinToString("") { listReferencedItem ->  """        ${listReferencedItem.itemName.pascalCase}ReferenceTableComponent,
              |""" } }${ model.item.singleReferencedItems.joinToString("") { singleReferencedItem ->  """        ${singleReferencedItem.itemName.pascalCase}ReferenceFieldComponent,
              |""" } }${ if(model.item.containsNumberListAttributes) { """        SingleNumberFormFieldTableComponent,""" } else { """""" } }${ if(model.item.containsBooleanListAttributes) { """        SingleBooleanFormFieldTableComponent,""" } else { """""" } }${ model.item.attributesWithEnumType.joinToString("") { attributeWithEnumType ->  """${ if(attributeWithEnumType.isList) { """        ${attributeWithEnumType.enum.enumName.pascalCase}InputTableComponent,
                  |""" } else { """        ${attributeWithEnumType.enum.enumName.pascalCase}SelectorComponent,
                  |""" } }""" } }    ]
          |})
          |export class ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartComponent implements OnInit {
          |    @Input({ required: true }) ${model.item.itemName.camelCase}Form!: FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>;
          |
          |${ model.item.attributesWithItemType.filter { it.isList }.joinToString("") { attributeWithItemType ->  """    readonly ${attributeWithItemType.attributeName.camelCase}EditState = new FormArrayEditState<${attributeWithItemType.angularFormControlType(model.entity.entityName)}>(() => this.${attributeWithItemType.attributeName.camelCase}Control);
              |""" } }
          |${ model.item.attributes.joinToString("") { attribute ->  """${ if(attribute.isNullable) { """    protected ${attribute.attributeName.camelCase}IsNotNullControl!: FormControl<boolean>
                  |    protected ${attribute.attributeName.camelCase}IsNotNullValidatorNames!: ReadonlyArray<ValidatorTranslation>
                  |""" } else { """""" } }    protected ${attribute.attributeName.camelCase}Control!: ${attribute.angularFormControlTypeWithCollection(model.entity.entityName)}
              |    protected ${attribute.attributeName.camelCase}ValidatorNames!: ReadonlyArray<ValidatorTranslation>
              |""" } }
          |    constructor(private readonly ${model.item.itemName.camelCase}FormValidationService: ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartValidationService,) {
          |    }
          |
          |    ngOnInit() {
          |${ model.item.attributes.joinToString("") { attribute ->  """${ if(attribute.isNullable) { """        this.${attribute.attributeName.camelCase}IsNotNullControl = this.${model.item.itemName.camelCase}Form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull]
                  |        this.${attribute.attributeName.camelCase}IsNotNullValidatorNames = this.${model.item.itemName.camelCase}FormValidationService.validatorNames(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}IsNotNull)
                  |""" } else { """""" } }        this.${attribute.attributeName.camelCase}Control = this.${model.item.itemName.camelCase}Form.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}]
              |        this.${attribute.attributeName.camelCase}ValidatorNames = this.${model.item.itemName.camelCase}FormValidationService.validatorNames(${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase})
              |""" } }    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part.component.ts"
    }
}