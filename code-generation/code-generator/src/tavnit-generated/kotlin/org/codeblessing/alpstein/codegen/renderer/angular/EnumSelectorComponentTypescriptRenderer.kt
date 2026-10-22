/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel

/**
 * Generate the content for the template `EnumSelectorComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `appellatio-comis-selector.component.ts`
 * - path: `enum/appellatio-comis-input-selection/appellatio-comis-selector.component.ts`
 */
object EnumSelectorComponentTypescriptRenderer : UiEnumRenderer {

    override fun renderTemplate(model: UiEnumModel): String {
        return """
          |import {Component, Input} from '@angular/core';
          |import {MatFormFieldModule} from '@angular/material/form-field';
          |import {MatInputModule} from '@angular/material/input';
          |import {CommonModule} from '@angular/common';
          |import {FormControl, ReactiveFormsModule} from '@angular/forms';
          |import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
          |import {${model.enumName.pascalCase}I18nComponent} from "@app/enum/${model.enumName.kebabCase}-i18n/${model.enumName.kebabCase}-i18n.component";
          |import {MatOption} from "@angular/material/core";
          |import {MatSelect} from "@angular/material/select";
          |import {${model.enumName.pascalCase}EnumValues} from "@app/wto/${model.enumName.kebabCase}.enum";
          |import {FieldErrorMessagesComponent} from "@app/shared/form-controls/field-error-messages/field-error-messages.component";
          |
          |@Component({
          |    selector: 'app-${model.enumName.kebabCase}-selector',
          |    templateUrl: './${model.enumName.kebabCase}-selector.component.html',
          |    styleUrls: ['./${model.enumName.kebabCase}-selector.component.scss'],
          |    standalone: true,
          |    imports: [
          |        CommonModule,
          |        ReactiveFormsModule,
          |        MatFormFieldModule,
          |        MatInputModule,
          |        ${model.enumName.pascalCase}I18nComponent,
          |        MatOption,
          |        MatSelect,
          |        FieldErrorMessagesComponent,
          |    ]
          |})
          |export class ${model.enumName.pascalCase}SelectorComponent {
          |    @Input() label: string = '';
          |    @Input({required: true}) enumFormControl!: FormControl;
          |    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];
          |
          |    protected readonly ${model.enumName.camelCase}List = ${model.enumName.pascalCase}EnumValues;
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEnumModel): String {
      return "enum/${model.enumName.kebabCase}-input-selection/${model.enumName.kebabCase}-selector.component.ts"
    }
}