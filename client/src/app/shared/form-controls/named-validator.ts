import {ValidatorFn} from "@angular/forms";

export interface NamedValidator {
    validatorName: string;
    validatorFunction: ValidatorFn;
    validatorTranslationKey: string;
    validatorTranslationParams?: Record<string, unknown>;
}
