import {Component, Input} from '@angular/core';
import {FormArray, FormControl} from "@angular/forms";
import {SingleFormFieldTableComponent} from "@app/shared/form-controls/single-form-field-table/single-form-field-table.component";
import {BooleanInputComponent} from "@app/shared/form-controls/boolean-input/boolean-input.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";

@Component({
    selector: 'app-single-boolean-form-field-table',
    templateUrl: './single-boolean-form-field-table.component.html',
    standalone: true,
    imports: [
        SingleFormFieldTableComponent,
        BooleanInputComponent,
    ]
})
export class SingleBooleanFormFieldTableComponent {
    @Input({required: true}) formArray!: FormArray;
    @Input() columnHeader: string = '';
    @Input() label: string = '';
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    createControl = (): FormControl => new FormControl(false);
}
