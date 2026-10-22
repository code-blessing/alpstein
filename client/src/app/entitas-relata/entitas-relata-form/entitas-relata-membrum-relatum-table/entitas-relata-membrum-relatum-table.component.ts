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
    EntitasRelataMembrumRelatumTableRow
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-table/entitas-relata-membrum-relatum-table-row.model";
import {
    EntitasRelataMembrumRelatumFormPartFieldName
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-field-name";
import {
    EntitasRelataMembrumRelatumFormPartService
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part.service";
import {
    EntitasRelataMembrumRelatumFormPartGroup
} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-group";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-entitas-relata-membrum-relatum-table',
    templateUrl: './entitas-relata-membrum-relatum-table.component.html',
    styleUrls: ['./entitas-relata-membrum-relatum-table.component.scss'],
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
export class EntitasRelataMembrumRelatumTableComponent implements OnInit {
    @Input({ required: true }) membrumRelatumFormArray!: FormArray<FormGroup<EntitasRelataMembrumRelatumFormPartGroup>>;
    @Output() editMembrumRelatumFormGroup = new EventEmitter<FormGroup<EntitasRelataMembrumRelatumFormPartGroup>>();
    @Output() deleteMembrumRelatumFormGroup = new EventEmitter<FormGroup<EntitasRelataMembrumRelatumFormPartGroup>>();

    displayedColumns: string[] = [
        'clavisPrimaria',
        'descriptioExDistanti',
        'actions',
    ];
    dataSource: MatTableDataSource<EntitasRelataMembrumRelatumTableRow> = new MatTableDataSource<EntitasRelataMembrumRelatumTableRow>();

    selectedFormGroup: FormGroup | undefined = undefined;

    constructor(private readonly membrumRelatumFormService: EntitasRelataMembrumRelatumFormPartService) {
    }

    ngOnInit(): void {
        this.updateFormData()
        this.membrumRelatumFormArray.valueChanges.subscribe(() => this.updateFormData())
    }

    private toTableRow(formGroup: FormGroup<EntitasRelataMembrumRelatumFormPartGroup>): EntitasRelataMembrumRelatumTableRow {
        return {
            clavisPrimaria: JSON.stringify(formGroup.controls[EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria].value),
            descriptioExDistanti: JSON.stringify(formGroup.controls[EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti].value),
            formGroup: formGroup,
        }
    }

    private updateFormData(): void {
        this.dataSource.data = this.membrumRelatumFormArray.controls.map((control) => this.toTableRow(control))
    }

    onAdd(): void {
        const newEntry = this.membrumRelatumFormService.createInitialMembrumRelatumForm()
        const indexOfSelected = this.selectedFormGroup ? this.membrumRelatumFormArray.controls.indexOf(this.selectedFormGroup) : -1
        if(indexOfSelected !== -1) {
            this.membrumRelatumFormArray.insert(indexOfSelected + 1, newEntry)
        } else {
            this.membrumRelatumFormArray.push(newEntry)
        }
        this.editMembrumRelatumFormGroup.emit(newEntry);
    }

    onSelect(entitasRelataMembrumRelatumFormGroup: FormGroup): void {
        this.selectedFormGroup = entitasRelataMembrumRelatumFormGroup
    }

    isSelected(entitasRelataMembrumRelatumFormGroup: FormGroup): boolean {
        return this.selectedFormGroup == entitasRelataMembrumRelatumFormGroup
    }

    onEdit(entitasRelataMembrumRelatumFormGroup: FormGroup): void {
        this.editMembrumRelatumFormGroup.emit(entitasRelataMembrumRelatumFormGroup);
    }

    onDelete(entitasRelataMembrumRelatumFormGroup: FormGroup): void {
        this.deleteMembrumRelatumFormGroup.emit(entitasRelataMembrumRelatumFormGroup);
    }
}
