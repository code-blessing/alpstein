/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityFormComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-form.component.ts`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-form/opus-magnum-form.component.ts`
 */
object EntityFormComponentTypescriptRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
          |import {FormGroup, ReactiveFormsModule} from '@angular/forms';
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
          |import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
          |import {${model.entityRootItem.itemName.pascalCase}WTO} from "@app/wto/${model.entityRootItem.itemName.kebabCase}.wto";
          |import {${model.entityName.pascalCase}${model.entityRootItem.itemName.pascalCase}FormPartService} from "@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-${model.entityRootItem.itemName.kebabCase}-form-part/${model.entityName.kebabCase}-${model.entityRootItem.itemName.kebabCase}-form-part.service";
          |import {${model.entityName.pascalCase}${model.entityRootItem.itemName.pascalCase}FormPartComponent} from "@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-${model.entityRootItem.itemName.kebabCase}-form-part/${model.entityName.kebabCase}-${model.entityRootItem.itemName.kebabCase}-form-part.component";
          |import {
          |    ${model.entityName.pascalCase}${model.entityRootItem.itemName.pascalCase}FormPartGroup
          |} from "@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-${model.entityRootItem.itemName.kebabCase}-form-part/${model.entityName.kebabCase}-${model.entityRootItem.itemName.kebabCase}-form-part-group";
          |import {
          |    FormValidationSummaryComponent
          |} from "@app/shared/form-controls/form-validation-summary/form-validation-summary.component";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |
          |@Component({
          |    selector: 'app-${model.entityName.kebabCase}-form',
          |    templateUrl: './${model.entityName.kebabCase}-form.component.html',
          |    styleUrls: ['./${model.entityName.kebabCase}-form.component.scss'],
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
          |        MatProgressSpinnerModule,
          |        ${model.entityName.pascalCase}${model.entityRootItem.itemName.pascalCase}FormPartComponent,
          |        FormValidationSummaryComponent,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.entityName.pascalCase}FormComponent implements OnInit {
          |    @Input() ${model.entityRootItem.itemName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO | null = null;
          |    @Input() saving = false;
          |    /** Whether the form offers to discard the edit; only a host that can react to it says yes. */
          |    @Input() cancellable = false;
          |    @Output() save = new EventEmitter<${model.entityRootItem.itemName.pascalCase}WTO>();
          |    @Output() cancel = new EventEmitter<void>();
          |
          |    ${model.entityRootItem.itemName.camelCase}Form: FormGroup<${model.entityName.pascalCase}${model.entityRootItem.itemName.pascalCase}FormPartGroup>;
          |
          |    constructor(private ${model.entityRootItem.itemName.camelCase}FormPartService: ${model.entityName.pascalCase}${model.entityRootItem.itemName.pascalCase}FormPartService) {
          |        this.${model.entityRootItem.itemName.camelCase}Form = ${model.entityRootItem.itemName.camelCase}FormPartService.createInitial${model.entityRootItem.itemName.pascalCase}Form();
          |    }
          |
          |    ngOnInit(): void {
          |        if (this.${model.entityRootItem.itemName.camelCase}) {
          |            this.${model.entityRootItem.itemName.camelCase}FormPartService.patch${model.entityRootItem.itemName.pascalCase}Form(this.${model.entityRootItem.itemName.camelCase}Form, this.${model.entityRootItem.itemName.camelCase})
          |        }
          |    }
          |
          |    onSubmit(): void {
          |        if (this.${model.entityRootItem.itemName.camelCase}Form.valid && !this.saving) {
          |            const updated${model.entityRootItem.itemName.pascalCase}: ${model.entityRootItem.itemName.pascalCase}WTO = this.${model.entityRootItem.itemName.camelCase}FormPartService.create${model.entityRootItem.itemName.pascalCase}WTOFromForm(this.${model.entityRootItem.itemName.camelCase}Form)
          |            this.save.emit(updated${model.entityRootItem.itemName.pascalCase});
          |        }
          |    }
          |
          |    onCancel(): void {
          |        this.cancel.emit();
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form.component.ts"
    }
}