/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityResultComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-result.component.html`
 * - path: `opus-magnum/opus-magnum-result/opus-magnum-result.component.html`
 */
object EntityResultComponentHtmlRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |<div class="${model.entityName.kebabCase}-table-container">
          |    <div class="${model.entityName.kebabCase}-result-actions">
          |        <button mat-raised-button color="primary" (click)="onCreate()">
          |            <mat-icon>add</mat-icon>
          |            <ng-container>{{ '${model.entityName.camelCase}.create.title' | transloco }}</ng-container>
          |        </button>
          |    </div>
          |    <div class="${model.entityName.kebabCase}-table-area">
          |        <div class="${model.entityName.kebabCase}-table-scroll">
          |            <table mat-table [dataSource]="dataSource">
          |
          |${ model.searchResultPlainAttributes.joinToString("") { attribute ->  """                <!-- ${attribute.attributeName.pascalCase} Column -->
              |                <ng-container matColumnDef="${attribute.attributeName.camelCase}">
              |                    <th mat-header-cell *matHeaderCellDef><span class="table-cell-content">{{ '${model.entityRootItem.itemName.camelCase}.${attribute.attributeName.camelCase}.label' | transloco }}</span></th>
              |                    <td mat-cell *matCellDef="let ${model.entityName.camelCase}">
              |                        <span class="table-cell-content" [title]="${model.entityName.camelCase}.${attribute.attributeName.camelCase}">{{ ${model.entityName.camelCase}.${attribute.attributeName.camelCase} }}</span>
              |                    </td>
              |                </ng-container>
              |""" } }${ model.searchResultReferenceAttributes.joinToString("") { attribute ->  """                <!-- ${attribute.attributeName.pascalCase} Column, a reference shown by the display attributes of the referenced item -->
              |                <ng-container matColumnDef="${attribute.attributeName.camelCase}">
              |                    <th mat-header-cell *matHeaderCellDef><span class="table-cell-content">{{ '${model.entityRootItem.itemName.camelCase}.${attribute.attributeName.camelCase}.label' | transloco }}</span></th>
              |                    <td mat-cell *matCellDef="let ${model.entityName.camelCase}">
              |                        <span class="table-cell-content" [title]="${attribute.attributeName.camelCase}Label(${model.entityName.camelCase})">{{ ${attribute.attributeName.camelCase}Label(${model.entityName.camelCase}) }}</span>
              |                    </td>
              |                </ng-container>
              |""" } }
          |                <!-- Actions Column -->
          |                <ng-container matColumnDef="actions" stickyEnd>
          |                    <th mat-header-cell *matHeaderCellDef>{{ 'table.column.actions' | transloco }}</th>
          |                    <td mat-cell *matCellDef="let ${model.entityName.camelCase}">
          |                        @let openEntryAction = 'action.openEntry' | transloco;
          |                        <button mat-icon-button color="primary" (click)="onEdit(${model.entityName.camelCase})">
          |                            <mat-icon>edit</mat-icon>
          |                        </button>
          |                        <!-- The pencil edits the entry in the panel below the result, this link
          |                             opens it in an editor tab of its own, next to the board. -->
          |                        <a mat-icon-button
          |                           [routerLink]="['/${model.entityName.kebabCase}-edit', ${model.entityName.camelCase}.${model.idAttribute.attributeName.camelCase}]"
          |                           [matTooltip]="openEntryAction"
          |                           [attr.aria-label]="openEntryAction">
          |                            <mat-icon>open_in_new</mat-icon>
          |                        </a>
          |                        <button mat-icon-button color="warn" (click)="onDelete(${model.entityName.camelCase})">
          |                            <mat-icon>delete</mat-icon>
          |                        </button>
          |                    </td>
          |                </ng-container>
          |
          |                <tr mat-header-row *matHeaderRowDef="displayedColumns; sticky: true"></tr>
          |                <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          |            </table>
          |        </div>
          |        @if (loading) {
          |            <div class="${model.entityName.kebabCase}-table-loading" role="status" [attr.aria-label]="'text.loading' | transloco">
          |                <mat-progress-spinner class="${model.entityName.kebabCase}-loading-spinner" value="75" diameter="48" aria-hidden="true" />
          |            </div>
          |        }
          |    </div>
          |</div> 
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-result/${model.entityName.kebabCase}-result.component.html"
    }
}