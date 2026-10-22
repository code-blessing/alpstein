/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityConfirmDeleteDialogComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-confirm-delete-dialog.component.ts`
 * - path: `opus-magnum/opus-magnum-confirm-delete-dialog/opus-magnum-confirm-delete-dialog.component.ts`
 */
object EntityConfirmDeleteDialogComponentTypescriptRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |import {Component, Inject} from '@angular/core';
          |import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from '@angular/material/dialog';
          |import {ReactiveFormsModule} from "@angular/forms";
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
          |import {${model.entityRootItem.itemName.pascalCase}WTO} from "@app/wto/${model.entityRootItem.itemName.kebabCase}.wto";
          |import {JsonPipe} from "@angular/common";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |
          |@Component({
          |    selector: 'app-${model.entityName.kebabCase}-confirm-delete-dialog',
          |    templateUrl: './${model.entityName.kebabCase}-confirm-delete-dialog.component.html',
          |    styleUrls: ['./${model.entityName.kebabCase}-confirm-delete-dialog.component.scss'],
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
          |        JsonPipe,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.entityName.pascalCase}ConfirmDeleteDialogComponent {
          |    constructor(
          |        public dialogRef: MatDialogRef<${model.entityName.pascalCase}ConfirmDeleteDialogComponent>,
          |        @Inject(MAT_DIALOG_DATA) public data: {
          |            entity: ${model.entityRootItem.itemName.pascalCase}WTO,
          |        }
          |    ) {
          |    }
          |
          |    onCancel(): void {
          |        this.dialogRef.close(false);
          |    }
          |
          |    onConfirm(): void {
          |        this.dialogRef.close(true);
          |    }
          |} 
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-confirm-delete-dialog/${model.entityName.kebabCase}-confirm-delete-dialog.component.ts"
    }
}