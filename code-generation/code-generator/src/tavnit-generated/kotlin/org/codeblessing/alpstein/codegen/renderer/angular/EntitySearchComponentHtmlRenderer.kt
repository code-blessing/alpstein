/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntitySearchComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-search.component.html`
 * - path: `opus-magnum/opus-magnum-search/opus-magnum-search.component.html`
 */
object EntitySearchComponentHtmlRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |        <form [formGroup]="searchForm" (ngSubmit)="onSubmit()">
          |            <div class="search-fields">
          |                <mat-form-field>
          |                    <mat-label>{{ 'search.field.label' | transloco }}</mat-label>
          |                    <input matInput [formControl]="searchQueryControl" [placeholder]="'${model.entityName.camelCase}.search.placeholder' | transloco">
          |                </mat-form-field>
          |                <button mat-raised-button color="primary" type="submit">
          |                    <mat-icon>search</mat-icon>
          |                    {{ 'action.search' | transloco }}
          |                </button>
          |                <button mat-button type="button" (click)="onReset()">
          |                    <mat-icon>clear</mat-icon>
          |                    {{ 'action.reset' | transloco }}
          |                </button>
          |            </div>
          |        </form>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-search/${model.entityName.kebabCase}-search.component.html"
    }
}