/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityBoardComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-board.component.ts`
 * - path: `opus-magnum/opus-magnum-board/opus-magnum-board.component.ts`
 */
object EntityBoardComponentTypescriptRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |import {Component, ElementRef, ViewChild} from '@angular/core';
          |import {${model.entityName.pascalCase}SearchComponent, ${model.entityName.pascalCase}SearchCriteria} from '@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-search/${model.entityName.kebabCase}-search.component';
          |import {${model.entityName.pascalCase}ResultComponent} from '@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-result/${model.entityName.kebabCase}-result.component';
          |import {MatDialog, MatDialogModule} from '@angular/material/dialog';
          |import {${model.entityName.pascalCase}ConfirmDeleteDialogComponent} from '@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-confirm-delete-dialog/${model.entityName.kebabCase}-confirm-delete-dialog.component';
          |import {${model.entityRootItem.itemName.pascalCase}Service} from '@app/service/${model.entityRootItem.itemName.kebabCase}.service';
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
          |import {${model.entityName.pascalCase}FormComponent} from "@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form.component";
          |import {${model.entityRootItem.itemName.pascalCase}WTO} from "@app/wto/${model.entityRootItem.itemName.kebabCase}.wto";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |import {finalize} from "rxjs";
          |
          |@Component({
          |    selector: 'app-${model.entityName.kebabCase}-board',
          |    templateUrl: './${model.entityName.kebabCase}-board.component.html',
          |    styleUrls: ['./${model.entityName.kebabCase}-board.component.scss'],
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
          |        ${model.entityName.pascalCase}SearchComponent,
          |        ${model.entityName.pascalCase}ResultComponent,
          |        ${model.entityName.pascalCase}FormComponent,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.entityName.pascalCase}BoardComponent {
          |    currentSearchCriteria: ${model.entityName.pascalCase}SearchCriteria = {};
          |    selected${model.entityName.pascalCase}: ${model.entityRootItem.itemName.pascalCase}WTO | null = null;
          |    creating = false;
          |    saving = false;
          |    refreshKey = 0;
          |
          |    @ViewChild('editPanel', {read: ElementRef}) private editPanel?: ElementRef<HTMLElement>;
          |
          |    constructor(private dialog: MatDialog, private ${model.entityRootItem.itemName.camelCase}Service: ${model.entityRootItem.itemName.pascalCase}Service) {
          |    }
          |
          |    onSearch(criteria: ${model.entityName.pascalCase}SearchCriteria): void {
          |        this.currentSearchCriteria = criteria;
          |    }
          |
          |    onCreate${model.entityName.pascalCase}(): void {
          |        const editPanelWasOpen = this.isEditPanelOpen();
          |        this.selected${model.entityName.pascalCase} = null;
          |        this.creating = true;
          |        if (editPanelWasOpen) {
          |            this.scrollToEditPanel();
          |        }
          |    }
          |
          |    on${model.entityName.pascalCase}Select(${model.entityName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO): void {
          |        const editPanelWasOpen = this.isEditPanelOpen();
          |        this.creating = false;
          |        this.selected${model.entityName.pascalCase} = ${model.entityName.camelCase};
          |        if (editPanelWasOpen) {
          |            this.scrollToEditPanel();
          |        }
          |    }
          |
          |    // A panel that is still closed cannot be scrolled to yet: the page only grows
          |    // tall enough for it once it is expanded, so the panel calls this from its
          |    // (afterExpand) event. An already open panel emits no such event, which is why
          |    // the handlers above scroll themselves in that case.
          |    scrollToEditPanel(): void {
          |        this.editPanel?.nativeElement.scrollIntoView({behavior: 'smooth', block: 'start'});
          |    }
          |
          |    private isEditPanelOpen(): boolean {
          |        return !!this.selected${model.entityName.pascalCase} || this.creating;
          |    }
          |
          |    onDelete${model.entityName.pascalCase}(${model.entityName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO): void {
          |        const dialogRef = this.dialog.open(${model.entityName.pascalCase}ConfirmDeleteDialogComponent, {
          |            data: {
          |                entity: ${model.entityName.camelCase},
          |            }
          |        });
          |        dialogRef.afterClosed().subscribe(result => {
          |            if (result) {
          |                this.${model.entityRootItem.itemName.camelCase}Service.delete${model.entityRootItem.itemName.pascalCase}(${model.entityName.camelCase}.${model.idAttribute.attributeName.camelCase}).subscribe(() => {
          |                    this.refreshKey++;
          |                });
          |            }
          |        });
          |    }
          |
          |    onSave(${model.entityName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO): void {
          |        const save${"$"} = this.creating
          |            ? this.${model.entityRootItem.itemName.camelCase}Service.create${model.entityRootItem.itemName.pascalCase}(${model.entityName.camelCase})
          |            : this.${model.entityRootItem.itemName.camelCase}Service.update${model.entityRootItem.itemName.pascalCase}(${model.entityName.camelCase});
          |        this.saving = true;
          |        save${"$"}.pipe(finalize(() => this.saving = false))
          |            .subscribe(() => {
          |                this.selected${model.entityName.pascalCase} = null;
          |                this.creating = false;
          |                this.refreshKey++;
          |            });
          |    }
          |
          |    onCancel(): void {
          |        this.selected${model.entityName.pascalCase} = null;
          |        this.creating = false;
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-board/${model.entityName.kebabCase}-board.component.ts"
    }
}