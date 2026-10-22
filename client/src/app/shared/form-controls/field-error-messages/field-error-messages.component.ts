import {Component, Input} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {TranslocoPipe} from '@jsverse/transloco';
import {mergedValidatorTranslationParams, ValidatorTranslation} from '@app/shared/form-controls/validator-translation';

/**
 * Renders the list of validation error messages for a single form control.
 *
 *   <app-field-error-messages
 *       [control]="someControl" [validatorTranslations]="someTranslations" />
 */
@Component({
    selector: 'app-field-error-messages',
    templateUrl: './field-error-messages.component.html',
    standalone: true,
    imports: [
        MatFormFieldModule,
        TranslocoPipe,
    ],
})
export class FieldErrorMessagesComponent {
    @Input({required: true}) control!: AbstractControl;
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    hasError(errorName: string): boolean {
        return this.control.hasError(errorName) && this.control.touched;
    }

    translationParams(validatorTranslation: ValidatorTranslation): Record<string, unknown> {
        return mergedValidatorTranslationParams(
            validatorTranslation,
            this.control.getError(validatorTranslation.validatorName),
        );
    }
}
