import {Component, Input} from '@angular/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {CommonModule} from '@angular/common';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
import {MatCheckbox} from "@angular/material/checkbox";
import {FieldErrorMessagesComponent} from "@app/shared/form-controls/field-error-messages/field-error-messages.component";

@Component({
    selector: 'app-boolean-input',
    templateUrl: './boolean-input.component.html',
    styleUrls: ['./boolean-input.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatCheckbox,
        FieldErrorMessagesComponent,
    ]
})
export class BooleanInputComponent {
    @Input() label: string = '';
    @Input({required: true}) booleanFormControl!: FormControl;
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];
}
