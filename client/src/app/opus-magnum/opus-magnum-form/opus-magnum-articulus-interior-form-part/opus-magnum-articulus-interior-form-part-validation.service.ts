import {Injectable} from '@angular/core';
import {ValidatorFn, Validators} from "@angular/forms";
import {NamedValidator} from "@app/shared/form-controls/named-validator";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
import {
    OpusMagnumArticulusInteriorFormPartFieldName
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-field-name";

@Injectable({providedIn: 'root'})
export class OpusMagnumArticulusInteriorFormPartValidationService {

    validatorFunctions(field: OpusMagnumArticulusInteriorFormPartFieldName): Array<ValidatorFn> {
        return this.namedValidators(field).map(namedValidator => namedValidator.validatorFunction)
    }

    validatorNames(field: OpusMagnumArticulusInteriorFormPartFieldName): Array<ValidatorTranslation> {
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

    namedValidators(field: OpusMagnumArticulusInteriorFormPartFieldName): ReadonlyArray<NamedValidator> {
        switch (field) {
            default:
                return []
        }
    };
} 
