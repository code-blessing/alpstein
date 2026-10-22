import {Component, Input} from '@angular/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {CommonModule} from '@angular/common';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
import {FieldErrorMessagesComponent} from "@app/shared/form-controls/field-error-messages/field-error-messages.component";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-text-input',
    templateUrl: './text-input.component.html',
    styleUrls: ['./text-input.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        FieldErrorMessagesComponent,
        TranslocoPipe,
    ]
})
export class TextInputComponent {
    @Input() label: string = '';
    @Input() placeholder: string = '';
    @Input({required: true}) textFormControl!: FormControl;
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];
    @Input() readonly: boolean = false;
}
