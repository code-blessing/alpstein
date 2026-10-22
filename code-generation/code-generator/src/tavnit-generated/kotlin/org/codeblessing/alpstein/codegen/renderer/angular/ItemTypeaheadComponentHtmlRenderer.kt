/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemTypeaheadComponentHtmlRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-typeahead.component.html`
 * - path: `reference/membrum-relatum-typeahead/membrum-relatum-typeahead.component.html`
 */
object ItemTypeaheadComponentHtmlRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |<mat-form-field appearance="fill" class="typeahead-field">
          |    @if (label !== '') {
          |        <mat-label>{{ label }}</mat-label>
          |    }
          |    <mat-icon matPrefix>search</mat-icon>
          |    <input matInput
          |           #queryInput
          |           [placeholder]="placeholderTranslationKey | transloco"
          |           [disabled]="disabled"
          |           [value]="selectionLabel"
          |           [matAutocomplete]="${model.itemName.camelCase}Autocomplete"
          |           (input)="onQueryChanged(queryInput.value)"
          |           (blur)="onSearchFieldBlurred(queryInput)">
          |${ if(model.hasOwnUiEntity) { """    <!-- Every action is a pure link: it opens the view it points at in a new tab of the shell,
              |         which is what keeps the half filled form the typeahead sits in alive. The action that
              |         opens the selected entry comes last, so that the two actions that are always there do
              |         not move when a selection appears, and it is a block of its own, because two projected
              |         nodes inside one block are silently not projected at all. -->
              |    @let ${model.itemName.camelCase}Label = itemLabelTranslationKey | transloco;
              |    @let createEntryAction = 'typeahead.action.createEntry' | transloco: {item: ${model.itemName.camelCase}Label};
              |    @let openBoardAction = 'typeahead.action.openBoard' | transloco: {item: ${model.itemName.camelCase}Label};
              |    @let openSelectedEntryAction = 'typeahead.action.openSelectedEntry' | transloco: {item: ${model.itemName.camelCase}Label};
              |    <a mat-icon-button matSuffix
              |       routerLink="/${model.ownUiEntityRouteName}-create"
              |       [matTooltip]="createEntryAction"
              |       [attr.aria-label]="createEntryAction">
              |        <mat-icon>add</mat-icon>
              |    </a>
              |    <a mat-icon-button matSuffix
              |       routerLink="/${model.ownUiEntityRouteName}-board"
              |       [matTooltip]="openBoardAction"
              |       [attr.aria-label]="openBoardAction">
              |        <mat-icon>dashboard</mat-icon>
              |    </a>
              |    @if (selection${model.primaryKeyAttribute.attributeName.pascalCase} !== null) {
              |        <a mat-icon-button matSuffix
              |           [routerLink]="['/${model.ownUiEntityRouteName}-edit', selection${model.primaryKeyAttribute.attributeName.pascalCase}]"
              |           [matTooltip]="openSelectedEntryAction"
              |           [attr.aria-label]="openSelectedEntryAction">
              |            <mat-icon>open_in_new</mat-icon>
              |        </a>
              |    }
              |""" } else { """""" } }    <mat-autocomplete #${model.itemName.camelCase}Autocomplete="matAutocomplete"
          |                      [displayWith]="clearSearchField"
          |                      (optionSelected)="onOptionSelected(${"$"}event.option.value, queryInput)">
          |        @for (${model.itemName.camelCase} of suggestionList(); track ${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase}) {
          |            <mat-option [value]="${model.itemName.camelCase}">{{ displayLabel(${model.itemName.camelCase}) }}</mat-option>
          |        }
          |    </mat-autocomplete>
          |</mat-form-field>
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-typeahead/${model.itemName.kebabCase}-typeahead.component.html"
    }
}