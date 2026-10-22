
import {Inject, Injectable, InjectionToken} from '@angular/core';
import {ValidatorFn, Validators} from "@angular/forms";
import {EntitasRelataMembrumRelatumFormPartFieldName} from "@app/entitas-relata/entitas-relata-form/entitas-relata-membrum-relatum-form-part/entitas-relata-membrum-relatum-form-part-field-name";
import {NamedValidator} from "@app/shared/form-controls/named-validator";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";



@Injectable({providedIn: 'root'})
export class EntitasRelataMembrumRelatumFormPartValidationService {


    validatorFunctions(field: EntitasRelataMembrumRelatumFormPartFieldName): Array<ValidatorFn> {
        return this.namedValidators(field).map(namedValidator => namedValidator.validatorFunction)
    }

    validatorNames(field: EntitasRelataMembrumRelatumFormPartFieldName): Array<ValidatorTranslation> {
        return this.namedValidators(field)
            .map(namedValidator => this.toValidatorTranslation(namedValidator))
    }

    private toValidatorTranslation(namedValidator: NamedValidator): ValidatorTranslation {
        return {
            validatorName: namedValidator.validatorName,
            validatorTranslationKey: namedValidator.validatorTranslationKey,
            validatorTranslationParams: namedValidator.validatorTranslationParams,
        }
    }

    /**
     * t(validator.required)
     */
    namedValidators(field: EntitasRelataMembrumRelatumFormPartFieldName): ReadonlyArray<NamedValidator> {
        switch(field) {
            case EntitasRelataMembrumRelatumFormPartFieldName.clavisPrimaria: return [
                {
                    validatorName: "required",
                    validatorFunction: Validators.required,
                    validatorTranslationKey: "validator.required",
                },
            ]
            case EntitasRelataMembrumRelatumFormPartFieldName.descriptioExDistanti: return [
                {
                    validatorName: "required",
                    validatorFunction: Validators.required,
                    validatorTranslationKey: "validator.required",
                },
            ]
            default: return []
        }
    };
} 
