/* @tt{{{

    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemTableComponentTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityFormViewItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui.entityform"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="articulus-interior" replaceByExpression="model.item.itemName.kebabCase" ]
        [ searchValue="articulusInterior" replaceByExpression="model.item.itemName.camelCase" ]
        [ searchValue="ArticulusInterior" replaceByExpression="model.item.itemName.pascalCase" ]
        [ searchValue="opus-magnum" replaceByExpression="model.entity.entityName.kebabCase" ]
        [ searchValue="OpusMagnum" replaceByExpression="model.entity.entityName.pascalCase" ]
        [ searchValue="opusMagnum" replaceByExpression="model.entity.entityName.camelCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {FormArray, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatDialogModule} from "@angular/material/dialog";
import {
    OpusMagnumArticulusInteriorTableRow
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-table/opus-magnum-articulus-interior-table-row.model";
import {
    OpusMagnumArticulusInteriorFormPartFieldName
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-field-name";
import {
    OpusMagnumArticulusInteriorFormPartService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part.service";
import {
    OpusMagnumArticulusInteriorFormPartGroup
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-group";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-opus-magnum-articulus-interior-table',
    templateUrl: './opus-magnum-articulus-interior-table.component.html',
    styleUrls: ['./opus-magnum-articulus-interior-table.component.scss'],
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
export class OpusMagnumArticulusInteriorTableComponent implements OnInit {
    @Input({ required: true }) articulusInteriorFormArray!: FormArray<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>;
    @Output() editArticulusInteriorFormGroup = new EventEmitter<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>();
    @Output() deleteArticulusInteriorFormGroup = new EventEmitter<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>();

    displayedColumns: string[] = [
        /*
        @tt{{{
            
            @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

            @replace-value-by-expression
            [ searchValue="scriptumTriviale" replaceByExpression="attribute.attributeName.camelCase" ]
            
        }}}@
         */
        'scriptumTriviale',
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        'numerusStupidus',
        /* @tt{{{   @end-ignore-text  }}}@ */
        'actions',
    ];
    dataSource: MatTableDataSource<OpusMagnumArticulusInteriorTableRow> = new MatTableDataSource<OpusMagnumArticulusInteriorTableRow>();

    selectedFormGroup: FormGroup | undefined = undefined;

    constructor(private readonly articulusInteriorFormService: OpusMagnumArticulusInteriorFormPartService) {
    }

    ngOnInit(): void {
        this.updateFormData()
        this.articulusInteriorFormArray.valueChanges.subscribe(() => this.updateFormData())
    }

    private toTableRow(formGroup: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>): OpusMagnumArticulusInteriorTableRow {
        return {
            /*
            @tt{{{
                
                @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

                @replace-value-by-expression
                [ searchValue="scriptumTriviale" replaceByExpression="attribute.attributeName.camelCase" ]
                [ searchValue="ScriptumTriviale" replaceByExpression="attribute.attributeName.pascalCase" ]
                
            }}}@
             */
            scriptumTriviale: JSON.stringify(formGroup.controls[OpusMagnumArticulusInteriorFormPartFieldName.scriptumTriviale].value),
            /* @tt{{{   @end-foreach  }}}@ */
            /* @tt{{{   @ignore-text  }}}@ */

            numerusStupidus: formGroup.controls[OpusMagnumArticulusInteriorFormPartFieldName.numerusStupidus].value,
            /* @tt{{{   @end-ignore-text  }}}@ */
            formGroup: formGroup,
        }
    }

    private updateFormData(): void {
        this.dataSource.data = this.articulusInteriorFormArray.controls.map((control) => this.toTableRow(control))
    }

    onAdd(): void {
        const newEntry = this.articulusInteriorFormService.createInitialArticulusInteriorForm()
        const indexOfSelected = this.selectedFormGroup ? this.articulusInteriorFormArray.controls.indexOf(this.selectedFormGroup) : -1
        if(indexOfSelected !== -1) {
            this.articulusInteriorFormArray.insert(indexOfSelected + 1, newEntry)
        } else {
            this.articulusInteriorFormArray.push(newEntry)
        }
        this.editArticulusInteriorFormGroup.emit(newEntry);
    }

    onSelect(opusMagnumArticulusInteriorFormGroup: FormGroup): void {
        this.selectedFormGroup = opusMagnumArticulusInteriorFormGroup
    }

    isSelected(opusMagnumArticulusInteriorFormGroup: FormGroup): boolean {
        return this.selectedFormGroup == opusMagnumArticulusInteriorFormGroup
    }

    onEdit(opusMagnumArticulusInteriorFormGroup: FormGroup): void {
        this.editArticulusInteriorFormGroup.emit(opusMagnumArticulusInteriorFormGroup);
    }

    onDelete(opusMagnumArticulusInteriorFormGroup: FormGroup): void {
        this.deleteArticulusInteriorFormGroup.emit(opusMagnumArticulusInteriorFormGroup);
    }
}
