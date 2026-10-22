/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel

/**
 * Generate the content for the template `EnumListFormFieldTableComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `appellatio-comis-input-table.component.html`
 * - path: `enum/appellatio-comis-input-table/appellatio-comis-input-table.component.html`
 */
object EnumListFormFieldTableComponentHtmlRenderer : UiEnumRenderer {

    override fun renderTemplate(model: UiEnumModel): String {
        return """
          |<app-single-form-field-table [formArray]="formArray" [columnHeader]="columnHeader" [createControl]="createControl">
          |    <ng-template let-control>
          |        <app-${model.enumName.kebabCase}-selector [enumFormControl]="control" [validatorTranslations]="validatorTranslations" />
          |    </ng-template>
          |</app-single-form-field-table>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEnumModel): String {
      return "enum/${model.enumName.kebabCase}-input-table/${model.enumName.kebabCase}-input-table.component.html"
    }
}