import {Component, Input} from '@angular/core';
import {FormArray, FormControl} from "@angular/forms";
import {SingleFormFieldTableComponent} from "@app/shared/form-controls/single-form-field-table/single-form-field-table.component";
import {NumberInputComponent} from "@app/shared/form-controls/number-input/number-input.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";

@Component({
    selector: 'app-single-number-form-field-table',
    templateUrl: './single-number-form-field-table.component.html',
    standalone: true,
    imports: [
        SingleFormFieldTableComponent,
        NumberInputComponent,
    ]
})
export class SingleNumberFormFieldTableComponent {
    @Input({required: true}) formArray!: FormArray;
    @Input() columnHeader: string = '';
    @Input() placeholder: string = '';
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    createControl = (): FormControl => new FormControl(null);
}
