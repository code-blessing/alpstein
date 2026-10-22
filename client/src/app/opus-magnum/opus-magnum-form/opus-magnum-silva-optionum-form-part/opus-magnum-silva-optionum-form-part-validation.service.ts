/* @tt{{{
    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemFormPartValidationServiceRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityFormViewItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui.entityform"
        modelName="model"
    ]

    @replace-value-by-expression
      [ searchValue="OpusMagnum" replaceByExpression="model.entity.entityName.pascalCase" ]
      [ searchValue="opusMagnum" replaceByExpression="model.entity.entityName.camelCase" ]
      [ searchValue="opus-magnum" replaceByExpression="model.entity.entityName.kebabCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.item.itemName.kebabCase" ]
        [ searchValue="SilvaOptionum" replaceByExpression="model.item.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.item.itemName.camelCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */

import {Inject, Injectable, InjectionToken} from '@angular/core';
import {ValidatorFn, Validators} from "@angular/forms";
import {OpusMagnumSilvaOptionumFormPartFieldName} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-field-name";
import {NamedValidator} from "@app/shared/form-controls/named-validator";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";

/* @tt{{{
    @foreach [ iteratorExpression="model.item.attributesWithCustomValidation" loopVariable="attributeWithCustomValidation" ]
    @replace-value-by-expression
        [ searchValue="campusTextusObligatorius" replaceByExpression="attributeWithCustomValidation.attributeName.camelCase" ]
        [ searchValue="CampusTextusObligatorius" replaceByExpression="attributeWithCustomValidation.attributeName.pascalCase" ]

}}}@  */
export const OpusMagnumSilvaOptionumCampusTextusObligatoriusNamedValidators = new InjectionToken<NamedValidator>('OpusMagnumSilvaOptionumCampusTextusObligatoriusNamedValidators');
/* @tt{{{   @end-foreach  }}}@ */


@Injectable({providedIn: 'root'})
export class OpusMagnumSilvaOptionumFormPartValidationService {

    /* @tt{{{   @if [conditionExpression="model.item.attributesWithCustomValidation.isNotEmpty()"]  }}}@ */
    constructor(
        /* @tt{{{
            @foreach [ iteratorExpression="model.item.attributesWithCustomValidation" loopVariable="attributeWithCustomValidation" ]
            @replace-value-by-expression
                [ searchValue="campusTextusObligatorius" replaceByExpression="attributeWithCustomValidation.attributeName.camelCase" ]
                [ searchValue="CampusTextusObligatorius" replaceByExpression="attributeWithCustomValidation.attributeName.pascalCase" ]

        }}}@  */
        @Inject(OpusMagnumSilvaOptionumCampusTextusObligatoriusNamedValidators) private campusTextusObligatoriusNamedValidators: ReadonlyArray<NamedValidator>,
        /* @tt{{{   @end-foreach  }}}@ */
    ) {}
    /* @tt{{{   @end-if  }}}@ */

    validatorFunctions(field: OpusMagnumSilvaOptionumFormPartFieldName): Array<ValidatorFn> {
        return this.namedValidators(field).map(namedValidator => namedValidator.validatorFunction)
    }

    validatorNames(field: OpusMagnumSilvaOptionumFormPartFieldName): Array<ValidatorTranslation> {
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
    namedValidators(field: OpusMagnumSilvaOptionumFormPartFieldName): ReadonlyArray<NamedValidator> {
        switch(field) {
            /* @tt{{{ 
                @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

                @replace-value-by-expression
                    [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]

            }}}@  */
            case OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius: return [
                /* @tt{{{ @if [ conditionExpression="!attribute.isList" ] }}}@  */
                {
                    validatorName: "required",
                    validatorFunction: Validators.required,
                    validatorTranslationKey: "validator.required",
                },
                /* @tt{{{   @end-if  }}}@ */
                /* @tt{{{ @if [ conditionExpression="attribute.hasCustomValidation" ] }}}@  */
                ...this.campusTextusObligatoriusNamedValidators,
                /* @tt{{{   @end-if  }}}@ */
            ]
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{   @ignore-text  }}}@ */
            case OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis: return [
                {
                    validatorName: "required",
                    validatorFunction: Validators.required,
                    validatorTranslationKey: "validator.required",
                },
            ]
            case OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis: return [
                {
                    validatorName: "required",
                    validatorFunction: Validators.required,
                    validatorTranslationKey: "validator.required",
                },
            ]
            case OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum: return [
            ]
            /* @tt{{{   @end-ignore-text  }}}@ */
            default: return []
        }
    };
} 
