/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityConfirmDeleteDialogComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-confirm-delete-dialog.component.html`
 * - path: `opus-magnum/opus-magnum-confirm-delete-dialog/opus-magnum-confirm-delete-dialog.component.html`
 */
object EntityConfirmDeleteDialogComponentHtmlRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |<h2 mat-dialog-title>{{ '${model.entityName.camelCase}.delete.title' | transloco }}</h2>
          |<mat-dialog-content><ng-container>{{ 'dialog.confirmDelete.question' | transloco }}</ng-container> <br />
          |    <b>
          |    {{ data.entity | json }}
          |    </b>
          |</mat-dialog-content>
          |<mat-dialog-actions align="end">
          |    <button mat-button (click)="onCancel()">{{ 'action.cancel' | transloco }}</button>
          |    <button mat-raised-button color="warn" (click)="onConfirm()">{{ 'action.ok' | transloco }}</button>
          |</mat-dialog-actions>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-confirm-delete-dialog/${model.entityName.kebabCase}-confirm-delete-dialog.component.html"
    }
}