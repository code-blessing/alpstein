/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemReferenceFieldComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-reference-field.component.html`
 * - path: `reference/membrum-relatum-reference-field/membrum-relatum-reference-field.component.html`
 */
object ItemReferenceFieldComponentHtmlRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |<app-${model.itemName.kebabCase}-typeahead
          |        [label]="'${model.itemName.camelCase}.label' | transloco"
          |        [selectionLabel]="selectionLabel()"
          |        [selection${model.primaryKeyAttribute.attributeName.pascalCase}]="referenced${model.primaryKeyAttribute.attributeName.pascalCase}()"
          |        [disabled]="${model.itemName.camelCase}ReferenceFormControl.disabled"
          |        (${model.itemName.camelCase}Selected)="on${model.itemName.pascalCase}Selected(${"$"}event)" />
          |<app-field-error-messages [control]="${model.itemName.camelCase}ReferenceFormControl"
          |                          [validatorTranslations]="validatorTranslations" />
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-reference-field/${model.itemName.kebabCase}-reference-field.component.html"
    }
}