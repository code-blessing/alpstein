/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityFormComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-form.component.html`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-form/opus-magnum-form.component.html`
 */
object EntityFormComponentHtmlRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |<div class="edit-form-container">
          |    <mat-card>
          |        <mat-card-header>
          |            <mat-card-title>{{ (${model.entityRootItem.itemName.camelCase} ? '${model.entityName.camelCase}.edit.title' : '${model.entityName.camelCase}.create.title') | transloco }}</mat-card-title>
          |        </mat-card-header>
          |
          |        <mat-card-content>
          |            <form [formGroup]="${model.entityRootItem.itemName.camelCase}Form" (ngSubmit)="onSubmit()">
          |                <app-${model.entityName.kebabCase}-${model.entityRootItem.itemName.kebabCase}-form-part [${model.entityRootItem.itemName.camelCase}Form]="${model.entityRootItem.itemName.camelCase}Form" />
          |
          |                <app-form-validation-summary [form]="${model.entityRootItem.itemName.camelCase}Form" />
          |
          |                <div class="form-actions">
          |                    @if (cancellable) {
          |                        <button mat-button type="button" (click)="onCancel()">
          |                            {{ 'action.cancel' | transloco }}
          |                        </button>
          |                    }
          |                    <button mat-raised-button color="primary" type="submit" [disabled]="!${model.entityRootItem.itemName.camelCase}Form.valid || saving">
          |                        @if (saving) {
          |                            <mat-progress-spinner class="save-button-spinner" value="75" diameter="18" aria-hidden="true" />
          |                        }
          |                        {{ 'action.save' | transloco }}
          |                    </button>
          |                </div>
          |            </form>
          |        </mat-card-content>
          |    </mat-card>
          |</div>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form.component.html"
    }
}