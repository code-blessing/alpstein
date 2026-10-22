import {Component, ContentChild, Input, OnInit, TemplateRef} from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {AbstractControl, FormArray, FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {CommonModule} from "@angular/common";
import {FormUtil} from "@app/shared/form-controls/form.util";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-single-form-field-table',
    templateUrl: './single-form-field-table.component.html',
    styleUrls: ['./single-form-field-table.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatTableModule,
        MatIconModule,
        TranslocoPipe,
    ]
})
export class SingleFormFieldTableComponent implements OnInit {
    @Input({required: true}) formArray!: FormArray;
    @Input() columnHeader: string = '';
    /**
     * t(table.addEntry)
     */
    @Input() addButtonLabelTranslationKey: string = 'table.addEntry';
    @Input() createControl: () => AbstractControl = () => new FormControl('');

    /** Template for a single row's form field. Receives the row's FormControl as $implicit context. */
    @ContentChild(TemplateRef) fieldTemplate!: TemplateRef<{ $implicit: FormControl }>;

    displayedColumns: string[] = ['field', 'actions'];
    dataSource: MatTableDataSource<AbstractControl> = new MatTableDataSource<AbstractControl>();

    selectedControl: AbstractControl | undefined = undefined;

    ngOnInit(): void {
        this.dataSource.data = this.formArray.controls;
        this.formArray.valueChanges.subscribe(() => this.updateFormData());
    }

    private updateFormData(): void {
        this.dataSource.data = this.formArray.controls;
    }

    onAdd(): void {
        const newEntry = this.createControl();
        const indexOfSelected = this.selectedControl ? this.formArray.controls.indexOf(this.selectedControl) : -1;
        if (indexOfSelected !== -1) {
            this.formArray.insert(indexOfSelected + 1, newEntry);
        } else {
            this.formArray.push(newEntry);
        }
    }

    onSelect(control: AbstractControl): void {
        this.selectedControl = control;
    }

    isSelected(control: AbstractControl): boolean {
        return this.selectedControl == control;
    }

    onDelete(control: AbstractControl): void {
        FormUtil.removeControl(this.formArray, control);
    }
}
