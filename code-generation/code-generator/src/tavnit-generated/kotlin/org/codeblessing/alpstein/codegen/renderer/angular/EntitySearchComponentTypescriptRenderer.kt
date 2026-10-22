/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntitySearchComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-search.component.ts`
 * - path: `opus-magnum/opus-magnum-search/opus-magnum-search.component.ts`
 */
object EntitySearchComponentTypescriptRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |import {Component, EventEmitter, Output} from '@angular/core';
          |import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
          |import {MatButtonModule} from "@angular/material/button";
          |import {MatToolbarModule} from "@angular/material/toolbar";
          |import {MatTableModule} from "@angular/material/table";
          |import {MatCardModule} from "@angular/material/card";
          |import {MatFormFieldModule} from "@angular/material/form-field";
          |import {MatInputModule} from "@angular/material/input";
          |import {MatIconModule} from "@angular/material/icon";
          |import {MatExpansionModule} from "@angular/material/expansion";
          |import {MatSidenavModule} from "@angular/material/sidenav";
          |import {MatListModule} from "@angular/material/list";
          |import {MatDialogModule} from "@angular/material/dialog";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |
          |export interface ${model.entityName.pascalCase}SearchCriteria {
          |    searchQuery?: string;
          |}
          |
          |export interface ${model.entityName.pascalCase}SearchForm {
          |    [${model.entityName.pascalCase}SearchFormFieldName.searchQuery]: FormControl<string>,
          |}
          |
          |export enum ${model.entityName.pascalCase}SearchFormFieldName {
          |    searchQuery = "searchQuery",
          |}
          |
          |
          |@Component({
          |    selector: 'app-${model.entityName.kebabCase}-search',
          |    templateUrl: './${model.entityName.kebabCase}-search.component.html',
          |    styleUrls: ['./${model.entityName.kebabCase}-search.component.scss'],
          |    imports: [
          |        ReactiveFormsModule,
          |        MatButtonModule,
          |        MatToolbarModule,
          |        MatTableModule,
          |        MatCardModule,
          |        MatFormFieldModule,
          |        MatInputModule,
          |        MatIconModule,
          |        MatExpansionModule,
          |        MatSidenavModule,
          |        MatListModule,
          |        MatDialogModule,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.entityName.pascalCase}SearchComponent {
          |    @Output() search = new EventEmitter<${model.entityName.pascalCase}SearchCriteria>();
          |
          |    protected searchForm: FormGroup<${model.entityName.pascalCase}SearchForm>;
          |    protected searchQueryControl!: FormControl<string>
          |
          |    constructor() {
          |        this.searchForm = new FormGroup<${model.entityName.pascalCase}SearchForm>({
          |            [${model.entityName.pascalCase}SearchFormFieldName.searchQuery]: new FormControl<string>(
          |                '',
          |                {
          |                    nonNullable: true,
          |                },
          |            ),
          |        });
          |        this.searchQueryControl = this.searchForm.controls[${model.entityName.pascalCase}SearchFormFieldName.searchQuery]
          |    }
          |
          |    onSubmit(): void {
          |        if (this.searchForm.valid) {
          |            const criteria: ${model.entityName.pascalCase}SearchCriteria = {
          |                searchQuery: this.searchQueryControl.value,
          |            };
          |            this.search.emit(criteria);
          |        }
          |    }
          |
          |    onReset(): void {
          |        this.searchForm.reset();
          |        this.search.emit({});
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-search/${model.entityName.kebabCase}-search.component.ts"
    }
}