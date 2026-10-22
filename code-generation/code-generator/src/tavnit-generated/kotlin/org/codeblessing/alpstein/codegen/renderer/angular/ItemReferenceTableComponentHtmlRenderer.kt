/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemReferenceTableComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-reference-table.component.html`
 * - path: `reference/membrum-relatum-reference-table/membrum-relatum-reference-table.component.html`
 */
object ItemReferenceTableComponentHtmlRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |<div class="table-container">
          |    <div class="action-bar">
          |        <app-${model.itemName.kebabCase}-typeahead
          |                [label]="'${model.itemName.camelCase}.reference.addEntry' | transloco"
          |                [excluded${model.primaryKeyAttribute.attributeName.pascalCase}List]="referenced${model.primaryKeyAttribute.attributeName.pascalCase}List()"
          |                [disabled]="${model.itemName.camelCase}ReferenceFormArray.disabled"
          |                (${model.itemName.camelCase}Selected)="on${model.itemName.pascalCase}Selected(${"$"}event)" />
          |    </div>
          |    <table mat-table [dataSource]="dataSource">
          |${ model.displayAttributes.joinToString("") { displayAttribute ->  """        <!-- Display attribute: ${displayAttribute.attributeName.pascalCase} -->
              |        <ng-container matColumnDef="${displayAttribute.attributeName.camelCase}">
              |            <th mat-header-cell *matHeaderCellDef>{{ columnHeader${displayAttribute.attributeName.pascalCase}TranslationKey | transloco }}</th>
              |            <td mat-cell *matCellDef="let tableRow">{{ tableRow.${displayAttribute.attributeName.camelCase} }}</td>
              |        </ng-container>
              |""" } }
          |        <!-- Actions Column -->
          |        <ng-container matColumnDef="actions">
          |            <th mat-header-cell *matHeaderCellDef>{{ 'table.column.actions' | transloco }}</th>
          |            <td mat-cell *matCellDef="let tableRow">
          |${ if(model.hasOwnUiEntity) { """                <!-- A link, not a button: the referenced entry is opened in a tab of its own,
              |                     next to the form this table is edited in. Reading it stays possible while
              |                     the reference list itself is disabled. -->
              |                @let openEntryAction = 'action.openEntry' | transloco;
              |                <a mat-icon-button
              |                   [routerLink]="['/${model.ownUiEntityRouteName}-edit', tableRow.${model.primaryKeyAttribute.attributeName.camelCase}]"
              |                   [matTooltip]="openEntryAction"
              |                   [attr.aria-label]="openEntryAction">
              |                    <mat-icon>open_in_new</mat-icon>
              |                </a>
              |""" } else { """""" } }                <button mat-icon-button color="warn" (click)="onDelete(tableRow)" type="button"
          |                        [disabled]="${model.itemName.camelCase}ReferenceFormArray.disabled">
          |                    <mat-icon>delete</mat-icon>
          |                </button>
          |            </td>
          |        </ng-container>
          |
          |        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
          |        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          |    </table>
          |</div>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-reference-table/${model.itemName.kebabCase}-reference-table.component.html"
    }
}