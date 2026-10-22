import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTableModule} from "@angular/material/table";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatDialogModule} from "@angular/material/dialog";
import {TranslocoPipe} from "@jsverse/transloco";

export interface EntitasRelataSearchCriteria {
    searchQuery?: string;
}

export interface EntitasRelataSearchForm {
    [EntitasRelataSearchFormFieldName.searchQuery]: FormControl<string>,
}

export enum EntitasRelataSearchFormFieldName {
    searchQuery = "searchQuery",
}


@Component({
    selector: 'app-entitas-relata-search',
    templateUrl: './entitas-relata-search.component.html',
    styleUrls: ['./entitas-relata-search.component.scss'],
    imports: [
        ReactiveFormsModule,
        MatButtonModule,
        MatToolbarModule,
        MatTableModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatExpansionModule,
        MatSidenavModule,
        MatListModule,
        MatDialogModule,
        TranslocoPipe,
    ]
})
export class EntitasRelataSearchComponent {
    @Output() search = new EventEmitter<EntitasRelataSearchCriteria>();

    protected searchForm: FormGroup<EntitasRelataSearchForm>;
    protected searchQueryControl!: FormControl<string>

    constructor() {
        this.searchForm = new FormGroup<EntitasRelataSearchForm>({
            [EntitasRelataSearchFormFieldName.searchQuery]: new FormControl<string>(
                '',
                {
                    nonNullable: true,
                },
            ),
        });
        this.searchQueryControl = this.searchForm.controls[EntitasRelataSearchFormFieldName.searchQuery]
    }

    onSubmit(): void {
        if (this.searchForm.valid) {
            const criteria: EntitasRelataSearchCriteria = {
                searchQuery: this.searchQueryControl.value,
            };
            this.search.emit(criteria);
        }
    }

    onReset(): void {
        this.searchForm.reset();
        this.search.emit({});
    }
}
