/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemTypeaheadComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-typeahead.component.ts`
 * - path: `reference/membrum-relatum-typeahead/membrum-relatum-typeahead.component.ts`
 */
object ItemTypeaheadComponentTypescriptRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
          |import {MatAutocompleteModule} from "@angular/material/autocomplete";
          |import {MatButtonModule} from "@angular/material/button";
          |import {MatFormFieldModule} from "@angular/material/form-field";
          |import {MatIconModule} from "@angular/material/icon";
          |import {MatInputModule} from "@angular/material/input";
          |import {MatTooltipModule} from "@angular/material/tooltip";
          |import {RouterLink} from "@angular/router";
          |import {Observable, Subject, debounceTime, distinctUntilChanged, of, switchMap} from "rxjs";
          |import {${model.itemName.pascalCase}Service} from "@app/service/${model.itemName.kebabCase}.service";
          |import {${model.itemName.camelCase}DisplayLabel} from "@app/reference/${model.itemName.kebabCase}-display";
          |${ if(model.hasUuidPrimaryKey) { """import {${model.primaryKeyAttribute.typescriptAttributeType}} from "@app/shared/uuid";
              |""" } else { """""" } }import {${model.itemName.pascalCase}SearchResultWTO} from "@app/wto/${model.itemName.kebabCase}-search-result.wto";
          |import {${model.itemName.pascalCase}WTO} from "@app/wto/${model.itemName.kebabCase}.wto";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |
          |/** Idle time after the last keystroke before the search is sent to the backend. */
          |const SEARCH_DEBOUNCE_IN_MILLISECONDS = 250;
          |
          |/**
          | * Typeahead to pick a single ${model.itemName.pascalCase}, searched by the backend search endpoint.
          | *
          | * The suggestions come from the already existing search of the ${model.itemName.pascalCase}Service
          | * (`POST /api/${model.itemName.kebabCase}/search`), debounced so that not every keystroke hits the backend.
          | * Each suggestion is labelled with the display attributes of the ${model.itemName.pascalCase}, never with
          | * the bare ${model.primaryKeyAttribute.attributeName.camelCase}.
          | *
          | * The whole ${model.itemName.pascalCase}WTO is emitted, not just its ${model.primaryKeyAttribute.attributeName.camelCase}, so the caller can show
          | * the display attributes of the new entry without another backend call.
          | *
          | * What the field shows when nothing is being typed is up to the caller, through
          | * [selectionLabel]: where the typeahead stands for a single reference the field shows the
          | * picked entry, so the selection is never deleted, only overwritten. Where it only appends to a
          | * list of references the label stays empty and the field is a pure search field that clears
          | * itself after every pick.
          | *
          | * Next to the search field it offers the actions that lead out of it: creating another
          | * ${model.itemName.pascalCase}, opening the selected one and opening the board with all of them. They are
          | * plain links into the routes of the editor shell of the ${model.itemName.pascalCase}, so that a tab is
          | * opened next to the one the typeahead is edited in instead of replacing it. An item without
          | * an editor shell of its own has no such action.
          | *
          | * The search field is deliberately not bound to a form control: MatAutocompleteTrigger writes
          | * the raw value of the selected option into the bound control (displayWith only changes the
          | * text shown in the input), which would push a whole ${model.itemName.pascalCase}WTO through the search
          | * query. Instead the typed text is read straight off the input element.
          | */
          |@Component({
          |    selector: 'app-${model.itemName.kebabCase}-typeahead',
          |    templateUrl: './${model.itemName.kebabCase}-typeahead.component.html',
          |    styleUrls: ['./${model.itemName.kebabCase}-typeahead.component.scss'],
          |    imports: [
          |        MatAutocompleteModule,
          |        MatButtonModule,
          |        MatFormFieldModule,
          |        MatIconModule,
          |        MatInputModule,
          |        MatTooltipModule,
          |        RouterLink,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.itemName.pascalCase}TypeaheadComponent implements OnInit {
          |    /** Already translated, because what the field stands for is up to the caller. */
          |    @Input() label: string = '';
          |    /**
          |     * t(${model.itemName.camelCase}.typeahead.placeholder)
          |     */
          |    @Input() placeholderTranslationKey: string = '${model.itemName.camelCase}.typeahead.placeholder';
          |    /**
          |     * The display attributes of the entry the typeahead itself stands for, shown in the search
          |     * field whenever the user is not typing. Empty where the typeahead does not represent a
          |     * selection of its own.
          |     */
          |    @Input() selectionLabel: string = '';
          |    /**
          |     * The primary key of that same entry, which the action that opens it navigates to. Null
          |     * where the typeahead does not represent a selection of its own.
          |     */
          |    @Input() selection${model.primaryKeyAttribute.attributeName.pascalCase}: ${model.primaryKeyAttribute.typescriptAttributeType} | null = null;
          |    /** ${model.primaryKeyAttribute.attributeName.camelCase} that are already referenced and must therefore not be suggested again. */
          |    @Input() excluded${model.primaryKeyAttribute.attributeName.pascalCase}List: ReadonlyArray<${model.primaryKeyAttribute.typescriptAttributeType}> = [];
          |    @Input() disabled: boolean = false;
          |
          |    @Output() ${model.itemName.camelCase}Selected = new EventEmitter<${model.itemName.pascalCase}WTO>();
          |
          |    /**
          |     * Names the type in the tooltips of the actions, which are the same for every item and
          |     * therefore translated with the item as a parameter.
          |     *
          |     * t(${model.itemName.camelCase}.label)
          |     */
          |    protected readonly itemLabelTranslationKey = '${model.itemName.camelCase}.label';
          |
          |    private readonly query = new Subject<string>();
          |    private ${model.itemName.camelCase}SuggestionList: ReadonlyArray<${model.itemName.pascalCase}WTO> = [];
          |
          |    constructor(private readonly ${model.itemName.camelCase}Service: ${model.itemName.pascalCase}Service) {}
          |
          |    ngOnInit(): void {
          |        this.query
          |            .pipe(
          |                debounceTime(SEARCH_DEBOUNCE_IN_MILLISECONDS),
          |                distinctUntilChanged(),
          |                switchMap(query => this.search${model.itemName.pascalCase}List(query)),
          |            )
          |            .subscribe(searchResult => this.${model.itemName.camelCase}SuggestionList = searchResult.${model.itemName.camelCase}List);
          |    }
          |
          |    protected onQueryChanged(query: string): void {
          |        this.query.next(query);
          |    }
          |
          |    /** An empty query is not sent to the backend, it simply clears the suggestions. */
          |    private search${model.itemName.pascalCase}List(query: string): Observable<${model.itemName.pascalCase}SearchResultWTO> {
          |        if (query.length === 0) {
          |            return of({${model.itemName.camelCase}List: []});
          |        }
          |        return this.${model.itemName.camelCase}Service.search${model.itemName.pascalCase}List({query: query});
          |    }
          |
          |    protected suggestionList(): ReadonlyArray<${model.itemName.pascalCase}WTO> {
          |        return this.${model.itemName.camelCase}SuggestionList
          |            .filter(${model.itemName.camelCase} => !this.excluded${model.primaryKeyAttribute.attributeName.pascalCase}List.includes(${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase}));
          |    }
          |
          |    protected displayLabel(${model.itemName.camelCase}: ${model.itemName.pascalCase}WTO): string {
          |        return ${model.itemName.camelCase}DisplayLabel(${model.itemName.camelCase});
          |    }
          |
          |    /**
          |     * Keeps MatAutocompleteTrigger from writing the raw option object into the search field. What
          |     * the field shows after a pick is decided in [onOptionSelected] instead.
          |     */
          |    protected readonly clearSearchField = (): string => '';
          |
          |    protected onOptionSelected(${model.itemName.camelCase}: ${model.itemName.pascalCase}WTO, searchField: HTMLInputElement): void {
          |        this.${model.itemName.camelCase}SuggestionList = [];
          |        // Keeps distinctUntilChanged from swallowing the same query when it is typed again.
          |        this.query.next('');
          |        this.${model.itemName.camelCase}Selected.emit(${model.itemName.camelCase});
          |        // The caller reacts to the pick by updating selectionLabel, which the [value] binding
          |        // writes into the field. Picking the entry that is already selected leaves the label
          |        // unchanged, so the field is restored here as well.
          |        searchField.value = this.selectionLabel;
          |    }
          |
          |    /**
          |     * Leaving the field without picking anything must not leave a half typed query behind where
          |     * the field shows a selection: it goes back to the picked entry. A pure search field keeps
          |     * what was typed.
          |     */
          |    protected onSearchFieldBlurred(searchField: HTMLInputElement): void {
          |        if (this.selectionLabel !== '') {
          |            searchField.value = this.selectionLabel;
          |        }
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-typeahead/${model.itemName.kebabCase}-typeahead.component.ts"
    }
}