import {Component, Input} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {MatIconModule} from '@angular/material/icon';
import {TranslocoPipe} from '@jsverse/transloco';
import {
    collectFormValidationErrors,
    FormValidationError
} from '@app/shared/form-controls/form-validation-summary/form-validation-errors';

/**
 * Renders all validation errors of a whole form tree as one list.
 *
 *   <app-form-validation-summary [form]="someForm" />
 *
 * In contrast to `FieldErrorMessagesComponent`, which renders the errors of a single control
 * next to that control once it has been touched, this summary lists the errors of every
 * control of the form - including the ones on a tab that is not selected or in a table row
 * that is not opened for editing - so that it is always visible why a form cannot be saved.
 */
@Component({
    selector: 'app-form-validation-summary',
    templateUrl: './form-validation-summary.component.html',
    styleUrls: ['./form-validation-summary.component.scss'],
    standalone: true,
    imports: [
        MatIconModule,
        TranslocoPipe,
    ],
})
export class FormValidationSummaryComponent {
    @Input({required: true}) form!: AbstractControl;

    validationErrors(): ReadonlyArray<FormValidationError> {
        return collectFormValidationErrors(this.form);
    }
}
