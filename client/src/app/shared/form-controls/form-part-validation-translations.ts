import {AbstractControl} from "@angular/forms";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";

/**
 * The part of a `*FormPartValidationService` that is needed to translate the
 * validation errors of the fields of a form part.
 */
export interface FormPartValidationTranslations {
    validatorNames(field: string): Array<ValidatorTranslation>;
}

const validationTranslationsByFormPart = new WeakMap<AbstractControl, FormPartValidationTranslations>();

/**
 * Links a form part (the form group created by a `*FormPartService`) with the validation
 * service knowing the validator translations of its fields.
 *
 * Components rendering the errors of a whole form tree instead of the errors of a single
 * field (see `FormValidationSummaryComponent`) have no way to inject the validation service
 * of every nested form part, so the form parts themselves provide it here.
 */
export function registerFormPartValidationTranslations<T extends AbstractControl>(
    formPart: T,
    validationTranslations: FormPartValidationTranslations,
): T {
    validationTranslationsByFormPart.set(formPart, validationTranslations);
    return formPart;
}

/**
 * Returns the validator translations registered for the given form part,
 * or undefined if the form part did not register any.
 */
export function formPartValidationTranslations(formPart: AbstractControl): FormPartValidationTranslations | undefined {
    return validationTranslationsByFormPart.get(formPart);
}
