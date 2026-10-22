/* @tt{{{

    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntitySearchComponentTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="OpusMagnum" replaceByExpression="model.entityName.pascalCase" ]
        [ searchValue="opusMagnum" replaceByExpression="model.entityName.camelCase" ]
        [ searchValue="opus-magnum" replaceByExpression="model.entityName.kebabCase" ]
        [ searchValue="SilvaOptionum" replaceByExpression="model.entityRootItem.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.entityRootItem.itemName.camelCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.entityRootItem.itemName.kebabCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */
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

export interface OpusMagnumSearchCriteria {
    searchQuery?: string;
}

export interface OpusMagnumSearchForm {
    [OpusMagnumSearchFormFieldName.searchQuery]: FormControl<string>,
}

export enum OpusMagnumSearchFormFieldName {
    searchQuery = "searchQuery",
}


@Component({
    selector: 'app-opus-magnum-search',
    templateUrl: './opus-magnum-search.component.html',
    styleUrls: ['./opus-magnum-search.component.scss'],
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
export class OpusMagnumSearchComponent {
    @Output() search = new EventEmitter<OpusMagnumSearchCriteria>();

    protected searchForm: FormGroup<OpusMagnumSearchForm>;
    protected searchQueryControl!: FormControl<string>

    constructor() {
        this.searchForm = new FormGroup<OpusMagnumSearchForm>({
            [OpusMagnumSearchFormFieldName.searchQuery]: new FormControl<string>(
                '',
                {
                    nonNullable: true,
                },
            ),
        });
        this.searchQueryControl = this.searchForm.controls[OpusMagnumSearchFormFieldName.searchQuery]
    }

    onSubmit(): void {
        if (this.searchForm.valid) {
            const criteria: OpusMagnumSearchCriteria = {
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
