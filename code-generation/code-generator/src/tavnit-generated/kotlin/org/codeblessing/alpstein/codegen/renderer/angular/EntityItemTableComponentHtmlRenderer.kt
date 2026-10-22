/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemTableComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-articulus-interior-table.component.html`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-table/opus-magnum-articulus-interior-table.component.html`
 */
object EntityItemTableComponentHtmlRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |<div class="table-container">
          |    <div class="action-bar">
          |        <button mat-button color="primary" (click)="onAdd()" type="button" [disabled]="${model.item.itemName.camelCase}FormArray.disabled">
          |            <mat-icon>add</mat-icon> {{ '${model.item.itemName.camelCase}.table.addEntry' | transloco }}
          |        </button>
          |
          |    </div>
          |    <table mat-table [dataSource]="dataSource">
          |${ model.item.attributes.joinToString("") { attribute ->  """
              |        <!-- ${attribute.attributeName.pascalCase} Column -->
              |        <ng-container matColumnDef="${attribute.attributeName.camelCase}">
              |            <th mat-header-cell *matHeaderCellDef>{{ '${model.item.itemName.camelCase}.${attribute.attributeName.camelCase}.label' | transloco }}</th>
              |            <td mat-cell *matCellDef="let tableRow" (click)="onSelect(tableRow.formGroup)">
              |                {{ tableRow.${attribute.attributeName.camelCase} }}
              |            </td>
              |        </ng-container>
              |""" } }
          |
          |        <!-- Actions Column -->
          |        <ng-container matColumnDef="actions">
          |            <th mat-header-cell *matHeaderCellDef>{{ 'table.column.actions' | transloco }}</th>
          |            <td mat-cell *matCellDef="let tableRow">
          |                <button mat-icon-button color="primary" (click)="onEdit(tableRow.formGroup)" type="button" [disabled]="${model.item.itemName.camelCase}FormArray.disabled">
          |                    <mat-icon>edit</mat-icon>
          |                </button>
          |                <button mat-icon-button color="warn" (click)="onDelete(tableRow.formGroup)" type="button" [disabled]="${model.item.itemName.camelCase}FormArray.disabled">
          |                    <mat-icon>delete</mat-icon>
          |                </button>
          |            </td>
          |        </ng-container>
          |
          |        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
          |        <tr mat-row *matRowDef="let row; columns: displayedColumns;" [class.selectedRow] = "isSelected(row)"></tr>
          |    </table>
          |</div> 
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table.component.html"
    }
}