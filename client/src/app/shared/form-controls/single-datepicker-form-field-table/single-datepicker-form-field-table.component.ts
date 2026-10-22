import {Component, Input} from '@angular/core';
import {FormArray, FormControl} from "@angular/forms";
import {SingleFormFieldTableComponent} from "@app/shared/form-controls/single-form-field-table/single-form-field-table.component";
import {DatepickerInputComponent} from "@app/shared/form-controls/datepicker-input/datepicker-input.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";

@Component({
    selector: 'app-single-datepicker-form-field-table',
    templateUrl: './single-datepicker-form-field-table.component.html',
    standalone: true,
    imports: [
        SingleFormFieldTableComponent,
        DatepickerInputComponent,
    ]
})
export class SingleDatepickerFormFieldTableComponent {
    @Input({required: true}) formArray!: FormArray;
    @Input() columnHeader: string = '';
    @Input() placeholder: string = '';
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    createControl = (): FormControl => new FormControl(null);
}
