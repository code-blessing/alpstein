import {AbstractControl, FormArray, FormGroup} from '@angular/forms';
import {formPartValidationTranslations} from '@app/shared/form-controls/form-part-validation-translations';
import {mergedValidatorTranslationParams, ValidatorTranslation} from '@app/shared/form-controls/validator-translation';

/** A single validation error of a form tree, ready to be rendered. */
export interface FormValidationError {
    /** The path to the failing field, e.g. `MandatoryAddresses › 2 › Street`, empty for the form itself. */
    fieldLabel: string;
    validatorTranslationKey: string;
    validatorTranslationParams: Record<string, unknown>;
}

const FIELD_LABEL_SEPARATOR = ' › ';

/**
 * Collects the validation errors of a form and of all its nested form parts, fields and
 * list elements, in the order in which the fields are declared in the form.
 *
 * The errors are translated with the validator translations the form parts registered
 * (see `registerFormPartValidationTranslations`); for a form part without registered
 * translations the name of the validator is used as translation key, e.g. `validator.required`.
 */
export function collectFormValidationErrors(form: AbstractControl): ReadonlyArray<FormValidationError> {
    return collectValidationErrors(form, [], []);
}

/**
 * @param validatorTranslations the translations of the validators of the field the control
 *        belongs to, taken from the validation service of the enclosing form part.
 */
function collectValidationErrors(
    control: AbstractControl,
    fieldPath: ReadonlyArray<string>,
    validatorTranslations: ReadonlyArray<ValidatorTranslation>,
): Array<FormValidationError> {
    // A disabled control (e.g. a nullable field switched to null) is not validated.
    if (control.disabled) {
        return [];
    }
    const validationErrors = validationErrorsOfControl(control, fieldPath, validatorTranslations);
    if (control instanceof FormGroup) {
        const formPartTranslations = formPartValidationTranslations(control);
        Object.entries(control.controls).forEach(([fieldName, fieldControl]) =>
            validationErrors.push(...collectValidationErrors(
                fieldControl,
                [...fieldPath, fieldName],
                formPartTranslations?.validatorNames(fieldName) ?? [],
            ))
        );
    } else if (control instanceof FormArray) {
        // The elements of a list field are validated with the validators of that field.
        control.controls.forEach((elementControl, index) =>
            validationErrors.push(...collectValidationErrors(
                elementControl,
                [...fieldPath, `${index + 1}`],
                validatorTranslations,
            ))
        );
    }
    return validationErrors;
}

function validationErrorsOfControl(
    control: AbstractControl,
    fieldPath: ReadonlyArray<string>,
    validatorTranslations: ReadonlyArray<ValidatorTranslation>,
): Array<FormValidationError> {
    return Object.entries(control.errors ?? {}).map(([validatorName, errorPayload]) => {
        const validatorTranslation = validatorTranslations
            .find(candidate => candidate.validatorName === validatorName);
        return {
            fieldLabel: fieldLabel(fieldPath),
            validatorTranslationKey: validatorTranslation?.validatorTranslationKey ?? `validator.${validatorName}`,
            validatorTranslationParams: mergedValidatorTranslationParams(validatorTranslation, errorPayload),
        };
    });
}

/**
 * Builds the label of a field out of its path, using the same wording as the label of the
 * field wrapper, e.g. `['mandatoryAddresses', '2', 'street']` -> `MandatoryAddresses › 2 › Street`.
 */
function fieldLabel(fieldPath: ReadonlyArray<string>): string {
    return fieldPath
        .map(fieldPathPart => fieldPathPart.charAt(0).toUpperCase() + fieldPathPart.slice(1))
        .join(FIELD_LABEL_SEPARATOR);
}
