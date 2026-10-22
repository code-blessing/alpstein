import {Component, Input} from '@angular/core';
import {FormArray, FormControl} from "@angular/forms";
import {SingleFormFieldTableComponent} from "@app/shared/form-controls/single-form-field-table/single-form-field-table.component";
import {TextInputComponent} from "@app/shared/form-controls/text-input/text-input.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";

@Component({
    selector: 'app-single-text-form-field-table',
    templateUrl: './single-text-form-field-table.component.html',
    standalone: true,
    imports: [
        SingleFormFieldTableComponent,
        TextInputComponent,
    ]
})
export class SingleTextFormFieldTableComponent {
    @Input({required: true}) formArray!: FormArray;
    @Input() columnHeader: string = '';
    @Input() placeholder: string = '';
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    createControl = (): FormControl => new FormControl('');
}
