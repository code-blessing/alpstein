/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityBoardComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-board.component.html`
 * - path: `opus-magnum/opus-magnum-board/opus-magnum-board.component.html`
 */
object EntityBoardComponentHtmlRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |<div class="${model.entityName.kebabCase}-container">
          |    <div class="${model.entityName.kebabCase}-header">
          |        <h2>{{'${model.entityName.camelCase}.board.title' | transloco }}</h2>
          |    </div>
          |
          |    <mat-accordion class="${model.entityName.kebabCase}-accordion" multi>
          |        <!-- Search Panel -->
          |        <mat-expansion-panel>
          |            <mat-expansion-panel-header>
          |                <mat-panel-title>
          |                    <mat-icon>search</mat-icon>
          |                    <ng-container>{{ '${model.entityName.camelCase}.search.panelTitle' | transloco }}</ng-container>
          |                </mat-panel-title>
          |            </mat-expansion-panel-header>
          |            <app-${model.entityName.kebabCase}-search (search)="onSearch(${"$"}event)"></app-${model.entityName.kebabCase}-search>
          |        </mat-expansion-panel>
          |
          |        <!-- Results Panel -->
          |        <mat-expansion-panel expanded>
          |            <mat-expansion-panel-header>
          |                <mat-panel-title>
          |                    <mat-icon>list</mat-icon>
          |                    <ng-container>{{ '${model.entityName.camelCase}.result.panelTitle' | transloco }}</ng-container>
          |                </mat-panel-title>
          |            </mat-expansion-panel-header>
          |            <app-${model.entityName.kebabCase}-result
          |                    [searchCriteria]="currentSearchCriteria"
          |                    (select${model.entityName.pascalCase})="on${model.entityName.pascalCase}Select(${"$"}event)"
          |                    (delete${model.entityName.pascalCase})="onDelete${model.entityName.pascalCase}(${"$"}event)"
          |                    (create${model.entityName.pascalCase})="onCreate${model.entityName.pascalCase}()"
          |                    [refreshKey]="refreshKey">
          |            </app-${model.entityName.kebabCase}-result>
          |        </mat-expansion-panel>
          |
          |        <!-- Edit Form Panel -->
          |        <mat-expansion-panel #editPanel
          |                [expanded]="!!selected${model.entityName.pascalCase} || creating"
          |                [disabled]="!selected${model.entityName.pascalCase} && !creating"
          |                (afterExpand)="scrollToEditPanel()">
          |            <mat-expansion-panel-header>
          |                <mat-panel-title>
          |                    <mat-icon>edit</mat-icon>
          |                    <ng-container>{{ (creating ? '${model.entityName.camelCase}.create.title' : '${model.entityName.camelCase}.edit.title') | transloco }}</ng-container>
          |                </mat-panel-title>
          |                @if (selected${model.entityName.pascalCase}) {
          |                    <mat-panel-description>
          |                        {{ selected${model.entityName.pascalCase}.${model.idAttribute.attributeName.camelCase} }}
          |                    </mat-panel-description>
          |                }
          |            </mat-expansion-panel-header>
          |
          |            @if (selected${model.entityName.pascalCase} || creating) {
          |                <app-${model.entityName.kebabCase}-form
          |                        [${model.entityRootItem.itemName.camelCase}]="selected${model.entityName.pascalCase}"
          |                        [saving]="saving"
          |                        [cancellable]="true"
          |                        (save)="onSave(${"$"}event)"
          |                        (cancel)="onCancel()">
          |                </app-${model.entityName.kebabCase}-form>
          |            }
          |        </mat-expansion-panel>
          |    </mat-accordion>
          |</div> 
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-board/${model.entityName.kebabCase}-board.component.html"
    }
}