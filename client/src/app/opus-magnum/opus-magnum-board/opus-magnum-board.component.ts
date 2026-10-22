/* @tt{{{

    

    @template-renderer [
        templateRendererClassName="EntityBoardComponentTypescriptRenderer"
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

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.idAttribute.attributeName.camelCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */
import {Component, ElementRef, ViewChild} from '@angular/core';
import {OpusMagnumSearchComponent, OpusMagnumSearchCriteria} from '@app/opus-magnum/opus-magnum-search/opus-magnum-search.component';
import {OpusMagnumResultComponent} from '@app/opus-magnum/opus-magnum-result/opus-magnum-result.component';
import {MatDialog, MatDialogModule} from '@angular/material/dialog';
import {OpusMagnumConfirmDeleteDialogComponent} from '@app/opus-magnum/opus-magnum-confirm-delete-dialog/opus-magnum-confirm-delete-dialog.component';
import {SilvaOptionumService} from '@app/service/silva-optionum.service';
import {ReactiveFormsModule} from "@angular/forms";
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
import {OpusMagnumFormComponent} from "@app/opus-magnum/opus-magnum-form/opus-magnum-form/opus-magnum-form.component";
import {SilvaOptionumWTO} from "@app/wto/silva-optionum.wto";
import {TranslocoPipe} from "@jsverse/transloco";
import {finalize} from "rxjs";

@Component({
    selector: 'app-opus-magnum-board',
    templateUrl: './opus-magnum-board.component.html',
    styleUrls: ['./opus-magnum-board.component.scss'],
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
        OpusMagnumSearchComponent,
        OpusMagnumResultComponent,
        OpusMagnumFormComponent,
        TranslocoPipe,
    ]
})
export class OpusMagnumBoardComponent {
    currentSearchCriteria: OpusMagnumSearchCriteria = {};
    selectedOpusMagnum: SilvaOptionumWTO | null = null;
    creating = false;
    saving = false;
    refreshKey = 0;

    @ViewChild('editPanel', {read: ElementRef}) private editPanel?: ElementRef<HTMLElement>;

    constructor(private dialog: MatDialog, private silvaOptionumService: SilvaOptionumService) {
    }

    onSearch(criteria: OpusMagnumSearchCriteria): void {
        this.currentSearchCriteria = criteria;
    }

    onCreateOpusMagnum(): void {
        const editPanelWasOpen = this.isEditPanelOpen();
        this.selectedOpusMagnum = null;
        this.creating = true;
        if (editPanelWasOpen) {
            this.scrollToEditPanel();
        }
    }

    onOpusMagnumSelect(opusMagnum: SilvaOptionumWTO): void {
        const editPanelWasOpen = this.isEditPanelOpen();
        this.creating = false;
        this.selectedOpusMagnum = opusMagnum;
        if (editPanelWasOpen) {
            this.scrollToEditPanel();
        }
    }

    // A panel that is still closed cannot be scrolled to yet: the page only grows
    // tall enough for it once it is expanded, so the panel calls this from its
    // (afterExpand) event. An already open panel emits no such event, which is why
    // the handlers above scroll themselves in that case.
    scrollToEditPanel(): void {
        this.editPanel?.nativeElement.scrollIntoView({behavior: 'smooth', block: 'start'});
    }

    private isEditPanelOpen(): boolean {
        return !!this.selectedOpusMagnum || this.creating;
    }

    onDeleteOpusMagnum(opusMagnum: SilvaOptionumWTO): void {
        const dialogRef = this.dialog.open(OpusMagnumConfirmDeleteDialogComponent, {
            data: {
                entity: opusMagnum,
            }
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.silvaOptionumService.deleteSilvaOptionum(opusMagnum.indexUnicus).subscribe(() => {
                    this.refreshKey++;
                });
            }
        });
    }

    onSave(opusMagnum: SilvaOptionumWTO): void {
        const save$ = this.creating
            ? this.silvaOptionumService.createSilvaOptionum(opusMagnum)
            : this.silvaOptionumService.updateSilvaOptionum(opusMagnum);
        this.saving = true;
        save$.pipe(finalize(() => this.saving = false))
            .subscribe(() => {
                this.selectedOpusMagnum = null;
                this.creating = false;
                this.refreshKey++;
            });
    }

    onCancel(): void {
        this.selectedOpusMagnum = null;
        this.creating = false;
    }
}
