/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel

/**
 * Generate the content for the template `EntityItemTableComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-articulus-interior-table.component.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-table/opus-magnum-articulus-interior-table.component.ts`
 */
object EntityItemTableComponentTypescriptRenderer : UiEntityItemRenderer {

    override fun renderTemplate(model: UiEntityFormViewItemModel): String {
        return """
          |import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
          |import {MatTableDataSource, MatTableModule} from '@angular/material/table';
          |import {FormArray, FormGroup, ReactiveFormsModule} from "@angular/forms";
          |import {MatButtonModule} from "@angular/material/button";
          |import {MatToolbarModule} from "@angular/material/toolbar";
          |import {MatCardModule} from "@angular/material/card";
          |import {MatFormFieldModule} from "@angular/material/form-field";
          |import {MatInputModule} from "@angular/material/input";
          |import {MatIconModule} from "@angular/material/icon";
          |import {MatExpansionModule} from "@angular/material/expansion";
          |import {MatSidenavModule} from "@angular/material/sidenav";
          |import {MatListModule} from "@angular/material/list";
          |import {MatDialogModule} from "@angular/material/dialog";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}TableRow
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table-row.model";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-field-name";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartService
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part.service";
          |import {
          |    ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup
          |} from "@app/${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-form-part-group";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |
          |@Component({
          |    selector: 'app-${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table',
          |    templateUrl: './${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table.component.html',
          |    styleUrls: ['./${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table.component.scss'],
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
          |export class ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}TableComponent implements OnInit {
          |    @Input({ required: true }) ${model.item.itemName.camelCase}FormArray!: FormArray<FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>>;
          |    @Output() edit${model.item.itemName.pascalCase}FormGroup = new EventEmitter<FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>>();
          |    @Output() delete${model.item.itemName.pascalCase}FormGroup = new EventEmitter<FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>>();
          |
          |    displayedColumns: string[] = [
          |${ model.item.attributes.joinToString("") { attribute ->  """        '${attribute.attributeName.camelCase}',
              |""" } }        'actions',
          |    ];
          |    dataSource: MatTableDataSource<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}TableRow> = new MatTableDataSource<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}TableRow>();
          |
          |    selectedFormGroup: FormGroup | undefined = undefined;
          |
          |    constructor(private readonly ${model.item.itemName.camelCase}FormService: ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartService) {
          |    }
          |
          |    ngOnInit(): void {
          |        this.updateFormData()
          |        this.${model.item.itemName.camelCase}FormArray.valueChanges.subscribe(() => this.updateFormData())
          |    }
          |
          |    private toTableRow(formGroup: FormGroup<${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartGroup>): ${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}TableRow {
          |        return {
          |${ model.item.attributes.joinToString("") { attribute ->  """            ${attribute.attributeName.camelCase}: JSON.stringify(formGroup.controls[${model.entity.entityName.pascalCase}${model.item.itemName.pascalCase}FormPartFieldName.${attribute.attributeName.camelCase}].value),
              |""" } }            formGroup: formGroup,
          |        }
          |    }
          |
          |    private updateFormData(): void {
          |        this.dataSource.data = this.${model.item.itemName.camelCase}FormArray.controls.map((control) => this.toTableRow(control))
          |    }
          |
          |    onAdd(): void {
          |        const newEntry = this.${model.item.itemName.camelCase}FormService.createInitial${model.item.itemName.pascalCase}Form()
          |        const indexOfSelected = this.selectedFormGroup ? this.${model.item.itemName.camelCase}FormArray.controls.indexOf(this.selectedFormGroup) : -1
          |        if(indexOfSelected !== -1) {
          |            this.${model.item.itemName.camelCase}FormArray.insert(indexOfSelected + 1, newEntry)
          |        } else {
          |            this.${model.item.itemName.camelCase}FormArray.push(newEntry)
          |        }
          |        this.edit${model.item.itemName.pascalCase}FormGroup.emit(newEntry);
          |    }
          |
          |    onSelect(${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup: FormGroup): void {
          |        this.selectedFormGroup = ${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup
          |    }
          |
          |    isSelected(${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup: FormGroup): boolean {
          |        return this.selectedFormGroup == ${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup
          |    }
          |
          |    onEdit(${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup: FormGroup): void {
          |        this.edit${model.item.itemName.pascalCase}FormGroup.emit(${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup);
          |    }
          |
          |    onDelete(${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup: FormGroup): void {
          |        this.delete${model.item.itemName.pascalCase}FormGroup.emit(${model.entity.entityName.camelCase}${model.item.itemName.pascalCase}FormGroup);
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityFormViewItemModel): String {
      return "${model.entity.entityName.kebabCase}/${model.entity.entityName.kebabCase}-form/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table/${model.entity.entityName.kebabCase}-${model.item.itemName.kebabCase}-table.component.ts"
    }
}