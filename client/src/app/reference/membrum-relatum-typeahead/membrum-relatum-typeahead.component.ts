/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemTypeaheadComponentTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="MembrumRelatum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="membrumRelatum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="membrum-relatum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="ClavisPrimaria" replaceByExpression="model.primaryKeyAttribute.attributeName.pascalCase" ]
        [ searchValue="clavisPrimaria" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.typescriptAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatTooltipModule} from "@angular/material/tooltip";
import {RouterLink} from "@angular/router";
import {Observable, Subject, debounceTime, distinctUntilChanged, of, switchMap} from "rxjs";
import {MembrumRelatumService} from "@app/service/membrum-relatum.service";
import {membrumRelatumDisplayLabel} from "@app/reference/membrum-relatum-display";
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */
import {MembrumRelatumSearchResultWTO} from "@app/wto/membrum-relatum-search-result.wto";
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {TranslocoPipe} from "@jsverse/transloco";

/** Idle time after the last keystroke before the search is sent to the backend. */
const SEARCH_DEBOUNCE_IN_MILLISECONDS = 250;

/**
 * Typeahead to pick a single MembrumRelatum, searched by the backend search endpoint.
 *
 * The suggestions come from the already existing search of the MembrumRelatumService
 * (`POST /api/membrum-relatum/search`), debounced so that not every keystroke hits the backend.
 * Each suggestion is labelled with the display attributes of the MembrumRelatum, never with
 * the bare clavisPrimaria.
 *
 * The whole MembrumRelatumWTO is emitted, not just its clavisPrimaria, so the caller can show
 * the display attributes of the new entry without another backend call.
 *
 * What the field shows when nothing is being typed is up to the caller, through
 * [selectionLabel]: where the typeahead stands for a single reference the field shows the
 * picked entry, so the selection is never deleted, only overwritten. Where it only appends to a
 * list of references the label stays empty and the field is a pure search field that clears
 * itself after every pick.
 *
 * Next to the search field it offers the actions that lead out of it: creating another
 * MembrumRelatum, opening the selected one and opening the board with all of them. They are
 * plain links into the routes of the editor shell of the MembrumRelatum, so that a tab is
 * opened next to the one the typeahead is edited in instead of replacing it. An item without
 * an editor shell of its own has no such action.
 *
 * The search field is deliberately not bound to a form control: MatAutocompleteTrigger writes
 * the raw value of the selected option into the bound control (displayWith only changes the
 * text shown in the input), which would push a whole MembrumRelatumWTO through the search
 * query. Instead the typed text is read straight off the input element.
 */
@Component({
    selector: 'app-membrum-relatum-typeahead',
    templateUrl: './membrum-relatum-typeahead.component.html',
    styleUrls: ['./membrum-relatum-typeahead.component.scss'],
    imports: [
        MatAutocompleteModule,
        MatButtonModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        MatTooltipModule,
        RouterLink,
        TranslocoPipe,
    ]
})
export class MembrumRelatumTypeaheadComponent implements OnInit {
    /** Already translated, because what the field stands for is up to the caller. */
    @Input() label: string = '';
    /**
     * t(membrumRelatum.typeahead.placeholder)
     */
    @Input() placeholderTranslationKey: string = 'membrumRelatum.typeahead.placeholder';
    /**
     * The display attributes of the entry the typeahead itself stands for, shown in the search
     * field whenever the user is not typing. Empty where the typeahead does not represent a
     * selection of its own.
     */
    @Input() selectionLabel: string = '';
    /**
     * The primary key of that same entry, which the action that opens it navigates to. Null
     * where the typeahead does not represent a selection of its own.
     */
    @Input() selectionClavisPrimaria: UUID | null = null;
    /** clavisPrimaria that are already referenced and must therefore not be suggested again. */
    @Input() excludedClavisPrimariaList: ReadonlyArray<UUID> = [];
    @Input() disabled: boolean = false;

    @Output() membrumRelatumSelected = new EventEmitter<MembrumRelatumWTO>();

    /**
     * Names the type in the tooltips of the actions, which are the same for every item and
     * therefore translated with the item as a parameter.
     *
     * t(membrumRelatum.label)
     */
    protected readonly itemLabelTranslationKey = 'membrumRelatum.label';

    private readonly query = new Subject<string>();
    private membrumRelatumSuggestionList: ReadonlyArray<MembrumRelatumWTO> = [];

    constructor(private readonly membrumRelatumService: MembrumRelatumService) {}

    ngOnInit(): void {
        this.query
            .pipe(
                debounceTime(SEARCH_DEBOUNCE_IN_MILLISECONDS),
                distinctUntilChanged(),
                switchMap(query => this.searchMembrumRelatumList(query)),
            )
            .subscribe(searchResult => this.membrumRelatumSuggestionList = searchResult.membrumRelatumList);
    }

    protected onQueryChanged(query: string): void {
        this.query.next(query);
    }

    /** An empty query is not sent to the backend, it simply clears the suggestions. */
    private searchMembrumRelatumList(query: string): Observable<MembrumRelatumSearchResultWTO> {
        if (query.length === 0) {
            return of({membrumRelatumList: []});
        }
        return this.membrumRelatumService.searchMembrumRelatumList({query: query});
    }

    protected suggestionList(): ReadonlyArray<MembrumRelatumWTO> {
        return this.membrumRelatumSuggestionList
            .filter(membrumRelatum => !this.excludedClavisPrimariaList.includes(membrumRelatum.clavisPrimaria));
    }

    protected displayLabel(membrumRelatum: MembrumRelatumWTO): string {
        return membrumRelatumDisplayLabel(membrumRelatum);
    }

    /**
     * Keeps MatAutocompleteTrigger from writing the raw option object into the search field. What
     * the field shows after a pick is decided in [onOptionSelected] instead.
     */
    protected readonly clearSearchField = (): string => '';

    protected onOptionSelected(membrumRelatum: MembrumRelatumWTO, searchField: HTMLInputElement): void {
        this.membrumRelatumSuggestionList = [];
        // Keeps distinctUntilChanged from swallowing the same query when it is typed again.
        this.query.next('');
        this.membrumRelatumSelected.emit(membrumRelatum);
        // The caller reacts to the pick by updating selectionLabel, which the [value] binding
        // writes into the field. Picking the entry that is already selected leaves the label
        // unchanged, so the field is restored here as well.
        searchField.value = this.selectionLabel;
    }

    /**
     * Leaving the field without picking anything must not leave a half typed query behind where
     * the field shows a selection: it goes back to the picked entry. A pure search field keeps
     * what was typed.
     */
    protected onSearchFieldBlurred(searchField: HTMLInputElement): void {
        if (this.selectionLabel !== '') {
            searchField.value = this.selectionLabel;
        }
    }
}
