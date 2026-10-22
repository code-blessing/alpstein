/* @tt{{{
    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemFormPartServiceRenderer"
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

import {Injectable} from '@angular/core';
import {SilvaOptionumWTO} from "@app/wto/silva-optionum.wto";
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {
    OpusMagnumSilvaOptionumFormPartValidationService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-validation.service";
import {
    OpusMagnumSilvaOptionumFormPartInitialValueService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-initial-value.service";
import {OpusMagnumSilvaOptionumFormPartGroup} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-group";
import {OpusMagnumSilvaOptionumFormPartFieldName} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-field-name";
import {
    registerFormPartValidationTranslations
} from "@app/shared/form-controls/form-part-validation-translations";
/* @tt{{{   @if [ conditionExpression="model.item.containsUuidAttributes"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */

/* @tt{{{
    @foreach [ iteratorExpression="model.item.usedEnums" loopVariable="usedEnum" ]

    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="usedEnum.enumName.pascalCase" ]
        [ searchValue="appellatio-comis" replaceByExpression="usedEnum.enumName.kebabCase" ]
}}}@  */
import {AppellatioComisEnum} from "@app/wto/appellatio-comis.enum";
/* @tt{{{   @end-foreach  }}}@ */

/* @tt{{{ 
    @foreach [ iteratorExpression="model.item.directlyNestedItems" loopVariable="nestedItem" ]

    @replace-value-by-expression
        [ searchValue="ArticulusInterior" replaceByExpression="nestedItem.itemName.pascalCase" ]
        [ searchValue="articulus-interior" replaceByExpression="nestedItem.itemName.kebabCase" ]

}}}@  */
import {
    OpusMagnumArticulusInteriorFormPartService
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part.service";
/* @tt{{{   @end-foreach  }}}@ */

@Injectable({providedIn: 'root'})
export class OpusMagnumSilvaOptionumFormPartService {

    constructor(
        private silvaOptionumFormValidationService: OpusMagnumSilvaOptionumFormPartValidationService,
        private silvaOptionumFormInitialValueService: OpusMagnumSilvaOptionumFormPartInitialValueService,
        /* @tt{{{ 
            @foreach [ iteratorExpression="model.item.directlyNestedItems" loopVariable="nestedItem" ]
            @replace-value-by-expression
                [ searchValue="articulusInterior" replaceByExpression="nestedItem.itemName.camelCase" ]
                [ searchValue="ArticulusInterior" replaceByExpression="nestedItem.itemName.pascalCase" ]

        }}}@  */
        private articulusInteriorFormPartService: OpusMagnumArticulusInteriorFormPartService,
        /* @tt{{{   @end-foreach  }}}@ */
    ) {}

    public createInitialSilvaOptionumForm(): FormGroup<OpusMagnumSilvaOptionumFormPartGroup> {
        const silvaOptionumForm = new FormGroup({
            /* @tt{{{   @ignore-text  }}}@ */
            [OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius]: new FormControl<string>(
                this.silvaOptionumFormInitialValueService.campusTextusObligatoriusInitialValue(),
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius)
                },
            ),
            /* @tt{{{   @end-ignore-text  }}}@ */
            /* @tt{{{ 
                @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

                @replace-value-by-expression
                    [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="FormControl<string>" replaceByExpression="attribute.angularFormControlType(model.entity.entityName)" ]


            }}}@  */
            /* @tt{{{   @if [ conditionExpression="!attribute.isItem && !attribute.isList && !attribute.isItemReference"]  }}}@ */
            [OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis]: new FormControl<string>(
                this.silvaOptionumFormInitialValueService.campusTextusOptionalisInitialValue(),
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis)
                },
            ),
            /* @tt{{{   @end-if  }}}@ */
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{
                @foreach [ iteratorExpression="model.item.attributesWithItemReference.filter { !it.isList }" loopVariable="referenceAttribute" ]

                @replace-value-by-expression
                    [ searchValue="relatioAdEntitatemOptionalis" replaceByExpression="referenceAttribute.attributeName.camelCase" ]
                    [ searchValue="FormControl<UUID | null>" replaceByExpression="referenceAttribute.angularFormControlType(model.entity.entityName)" ]

            }}}@  */
            [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis]: new FormControl<UUID | null>(
                null,
                {
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis)
                },
            ),
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{
                @foreach [ iteratorExpression="model.item.attributesWithItemType.filter { !it.isList }" loopVariable="attributeWithItemType" ]

                @replace-value-by-expression
                    [ searchValue="articulusInteriorSingularis" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                    [ searchValue="articulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.camelCase" ]
                    [ searchValue="ArticulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.pascalCase" ]

            }}}@  */
            [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularis]: this.articulusInteriorFormPartService.createInitialArticulusInteriorForm(),
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{ 
                @foreach [ iteratorExpression="model.item.attributesWithItemType.filter { it.isList }" loopVariable="attributeWithItemType" ]

                @replace-value-by-expression
                    [ searchValue="articulusInteriorIteratus" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                    [ searchValue="articulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.camelCase" ]
                    [ searchValue="ArticulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.pascalCase" ]

            }}}@  */
            [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus]: new FormArray(
                this.silvaOptionumFormInitialValueService.articulusInteriorIteratusInitialValue(),
                {
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus)
                },
            ),
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{
                @foreach [ iteratorExpression="model.item.builtInTypeAndEnumAttributes.filter { it.isList }" loopVariable="attribute" ]

                @replace-value-by-expression
                    [ searchValue="iteratioSimpliciumTextuum" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="FormArray<FormControl<string>>" replaceByExpression="attribute.angularFormControlTypeWithCollection(model.entity.entityName)" ]

            }}}@  */
            [OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum]: new FormArray<FormControl<string>>(
                [],
                {
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum)
                },
            ),
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{   @ignore-text  }}}@ */
            [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus]: new FormArray(
                this.silvaOptionumFormInitialValueService.articulusInteriorOptionalisIteratusInitialValue(),
                {
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus]: new FormArray<FormControl<AppellatioComisEnum>>(
                [],
                {
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus]: new FormArray<FormControl<UUID>>(
                [],
                {
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalis]: this.articulusInteriorFormPartService.createInitialArticulusInteriorForm(),
            [OpusMagnumSilvaOptionumFormPartFieldName.campusDiei]: new FormControl<Date>(
                this.silvaOptionumFormInitialValueService.campusDieiInitialValue(),
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.campusDiei)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.campusBivalens]: new FormControl<boolean>(
                this.silvaOptionumFormInitialValueService.campusBivalensInitialValue(),
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.campusBivalens)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.appellatio]: new FormControl<AppellatioComisEnum>(
                this.silvaOptionumFormInitialValueService.appellatioInitialValue(),
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.appellatio)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.campusNumerorum]: new FormControl<number>(
                this.silvaOptionumFormInitialValueService.campusNumerorumInitialValue(),
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.campusNumerorum)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.indexUnicus]: new FormControl<UUID>(
                this.silvaOptionumFormInitialValueService.indexUnicusInitialValue(),
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.indexUnicus)
                },
            ),
            /* @tt{{{   @end-ignore-text  }}}@ */
            /* @tt{{{
                @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

                @replace-value-by-expression
                    [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="FormControl<string | null>" replaceByExpression="attribute.angularFormControlType(model.entity.entityName)" ]

            }}}@  */
            /* @tt{{{   @if [ conditionExpression="attribute.isNullable"]  }}}@ */
            [OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull]: new FormControl<boolean>(
                false,
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull)
                },
            ),
            /* @tt{{{   @end-if  }}}@ */
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{   @ignore-text  }}}@ */
            [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull]: new FormControl<boolean>(
                false,
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull]: new FormControl<boolean>(
                false,
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull]: new FormControl<boolean>(
                false,
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull]: new FormControl<boolean>(
                false,
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull]: new FormControl<boolean>(
                false,
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull)
                },
            ),
            [OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull]: new FormControl<boolean>(
                false,
                {
                    nonNullable: true,
                    validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull)
                },
            ),
            /* @tt{{{   @end-ignore-text  }}}@ */

        });
        return registerFormPartValidationTranslations(silvaOptionumForm, this.silvaOptionumFormValidationService);
    }

    /* @tt{{{
        @foreach [ iteratorExpression="model.item.builtInTypeAndEnumAttributes.filter { it.isList }" loopVariable="attribute" ]

        @replace-value-by-expression
            [ searchValue="iteratioSimpliciumTextuum" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="IteratioSimpliciumTextuum" replaceByExpression="attribute.attributeName.pascalCase" ]
            [ searchValue="FormControl<string>" replaceByExpression="attribute.angularFormControlType(model.entity.entityName)" ]

    }}}@  */
    public createInitialIteratioSimpliciumTextuumForm(): FormControl<string> {
        return new FormControl<string>(
            this.silvaOptionumFormInitialValueService.iteratioSimpliciumTextuumInitialValue(),
            {
                nonNullable: true,
                validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum)
            },
        )
    }
    /* @tt{{{   @end-foreach  }}}@ */

    /* @tt{{{   @ignore-text  }}}@ */
    public createInitialAppellatioOptionalisIteratusForm(): FormControl<AppellatioComisEnum> {
        return new FormControl<AppellatioComisEnum>(
            this.silvaOptionumFormInitialValueService.appellatioOptionalisIteratusInitialValue(),
            {
                nonNullable: true,
                validators: this.silvaOptionumFormValidationService.validatorFunctions(OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus)
            },
        )
    }
    /* @tt{{{   @end-ignore-text  }}}@ */

    public patchSilvaOptionumForm(form: FormGroup<OpusMagnumSilvaOptionumFormPartGroup>, silvaOptionum: SilvaOptionumWTO): void {
        this.patchPreparation(form, silvaOptionum);

        /* @tt{{{
            @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

            @replace-value-by-expression
                [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
                [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]

            
        }}}@  */
        /* @tt{{{   @if [ conditionExpression="!attribute.isNullable"]  }}}@ */
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius].patchValue(silvaOptionum.campusTextusObligatorius);
        /* @tt{{{   @end-if  }}}@ */
        /* @tt{{{   @if [ conditionExpression="attribute.isNullable"]  }}}@ */
        if(silvaOptionum.campusTextusOptionalis != null) {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull].patchValue(true);
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis].patchValue(silvaOptionum.campusTextusOptionalis);
        } else {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull].patchValue(false);
        }
        /* @tt{{{   @end-if  }}}@ */
        /* @tt{{{  @end-foreach  }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus].patchValue(silvaOptionum.articulusInteriorIteratus)
        if(silvaOptionum.articulusInteriorOptionalisIteratus != null) {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull].patchValue(true);
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus].patchValue(silvaOptionum.articulusInteriorOptionalisIteratus);
        } else {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull].patchValue(false);
        }
        if(silvaOptionum.appellatioOptionalisIteratus != null) {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull].patchValue(true);
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus].patchValue(silvaOptionum.appellatioOptionalisIteratus);
        } else {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull].patchValue(false);
        }
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularis].patchValue(silvaOptionum.articulusInteriorSingularis);
        if(silvaOptionum.articulusInteriorSingularisOptionalis != null) {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull].patchValue(true);
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalis].patchValue(silvaOptionum.articulusInteriorSingularisOptionalis);
        } else {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull].patchValue(false);
        }
        if(silvaOptionum.campusDiei != null) {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull].patchValue(true);
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusDiei].patchValue(silvaOptionum.campusDiei);
        } else {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull].patchValue(false);
        }
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusBivalens].patchValue(silvaOptionum.campusBivalens);
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatio].patchValue(silvaOptionum.appellatio);
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusNumerorum].patchValue(silvaOptionum.campusNumerorum);
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.indexUnicus].patchValue(silvaOptionum.indexUnicus);
        form.controls[OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum].patchValue(silvaOptionum.iteratioSimpliciumTextuum);
        if(silvaOptionum.relatioAdEntitatemOptionalisIteratus != null) {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull].patchValue(true);
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus].patchValue(silvaOptionum.relatioAdEntitatemOptionalisIteratus);
        } else {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull].patchValue(false);
        }
        if(silvaOptionum.relatioAdEntitatemOptionalis != null) {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull].patchValue(true);
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis].patchValue(silvaOptionum.relatioAdEntitatemOptionalis);
        } else {
            form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull].patchValue(false);
        }
        /* @tt{{{   @end-ignore-text  }}}@ */

        this.patchNestedItems(form, silvaOptionum);
    }

    /**
     * patchValue does not create missing FormGroups inside the FormArray.
     * So if your FormArray is empty (or shorter than the incoming data), nothing (or only the first N) gets patched.
     * We need to prefill the FormArray with empty values first
     */
    private patchPreparation(form: FormGroup<OpusMagnumSilvaOptionumFormPartGroup>, silvaOptionum: SilvaOptionumWTO): void {
        /* @tt{{{
            @foreach [ iteratorExpression="model.item.attributesWithItemType.filter { it.isList }" loopVariable="attributeWithItemType" ]
            @replace-value-by-expression
                    [ searchValue="articulusInteriorIteratus" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                    [ searchValue="ArticulusInteriorIteratus" replaceByExpression="attributeWithItemType.attributeName.pascalCase" ]
                    [ searchValue="articulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.camelCase" ]
                    [ searchValue="ArticulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.pascalCase" ]

            }}}@  */
        if(silvaOptionum.articulusInteriorIteratus != null) {
            const articulusInteriorIteratusLength = form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus].controls.length
            if (articulusInteriorIteratusLength < silvaOptionum.articulusInteriorIteratus.length) {
                for (let i = articulusInteriorIteratusLength; i < silvaOptionum.articulusInteriorIteratus.length; i++) {
                    form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus].push(this.articulusInteriorFormPartService.createInitialArticulusInteriorForm())
                }
            }
        }
        /* @tt{{{ @end-foreach }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.item.builtInTypeAndEnumAttributes.filter { it.isList }" loopVariable="attribute" ]
            @replace-value-by-expression
                    [ searchValue="iteratioSimpliciumTextuum" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="IteratioSimpliciumTextuum" replaceByExpression="attribute.attributeName.pascalCase" ]

        }}}@  */
        if(silvaOptionum.iteratioSimpliciumTextuum != null) {
            const iteratioSimpliciumTextuumLength = form.controls[OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum].controls.length
            if (iteratioSimpliciumTextuumLength < silvaOptionum.iteratioSimpliciumTextuum.length) {
                for (let i = iteratioSimpliciumTextuumLength; i < silvaOptionum.iteratioSimpliciumTextuum.length; i++) {
                    form.controls[OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum].push(this.createInitialIteratioSimpliciumTextuumForm())
                }
            }
        }
        /* @tt{{{ @end-foreach }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        if(silvaOptionum.articulusInteriorOptionalisIteratus != null) {
            const articulusInteriorIteratusOptionalisLength = form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus].controls.length
            if (articulusInteriorIteratusOptionalisLength < silvaOptionum.articulusInteriorOptionalisIteratus.length) {
                for (let i = articulusInteriorIteratusOptionalisLength; i < silvaOptionum.articulusInteriorOptionalisIteratus.length; i++) {
                    form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus].push(this.articulusInteriorFormPartService.createInitialArticulusInteriorForm())
                }
            }
        }
        if(silvaOptionum.appellatioOptionalisIteratus != null) {
            const appellatioOptionalisIteratusLength = form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus].controls.length
            if (appellatioOptionalisIteratusLength < silvaOptionum.appellatioOptionalisIteratus.length) {
                for (let i = appellatioOptionalisIteratusLength; i < silvaOptionum.appellatioOptionalisIteratus.length; i++) {
                    form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus].push(this.createInitialAppellatioOptionalisIteratusForm())
                }
            }
        }
        if(silvaOptionum.relatioAdEntitatemOptionalisIteratus != null) {
            const relatioAdEntitatemOptionalisIteratusLength = form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus].controls.length
            if (relatioAdEntitatemOptionalisIteratusLength < silvaOptionum.relatioAdEntitatemOptionalisIteratus.length) {
                for (let i = relatioAdEntitatemOptionalisIteratusLength; i < silvaOptionum.relatioAdEntitatemOptionalisIteratus.length; i++) {
                    form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus].push(
                        new FormControl<UUID>(silvaOptionum.relatioAdEntitatemOptionalisIteratus[i], {nonNullable: true})
                    )
                }
            }
        }
        /* @tt{{{   @end-ignore-text  }}}@ */
    }


    private patchNestedItems(form: FormGroup<OpusMagnumSilvaOptionumFormPartGroup>, silvaOptionum: SilvaOptionumWTO): void {
        /* @tt{{{
            @foreach [ iteratorExpression="model.item.attributesWithItemType.filter { it.isList }" loopVariable="attributeWithItemType" ]
            @replace-value-by-expression
                    [ searchValue="articulusInteriorIteratus" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                    [ searchValue="ArticulusInteriorIteratus" replaceByExpression="attributeWithItemType.attributeName.pascalCase" ]
                    [ searchValue="articulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.camelCase" ]
                    [ searchValue="ArticulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.pascalCase" ]

            }}}@  */
        if(silvaOptionum.articulusInteriorIteratus != null) {
            for (let i = 0; i < silvaOptionum.articulusInteriorIteratus.length; i++) {
                this.articulusInteriorFormPartService.patchArticulusInteriorForm(
                    form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus].at(i),
                    silvaOptionum.articulusInteriorIteratus[i]
                )
            }
        }
        /* @tt{{{ @end-foreach }}}@ */

        /* @tt{{{
            @foreach [ iteratorExpression="model.item.attributesWithItemType.filter { !it.isList }" loopVariable="attributeWithItemType" ]
            @replace-value-by-expression
                    [ searchValue="articulusInteriorSingularisOptionalis" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                    [ searchValue="articulusInteriorSingularis" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                    [ searchValue="articulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.camelCase" ]
                    [ searchValue="ArticulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.pascalCase" ]

            }}}@  */
        /* @tt{{{ @if [conditionExpression="attributeWithItemType.isNullable"] }}}@ */
        if(silvaOptionum.articulusInteriorSingularisOptionalis != null) {
            this.articulusInteriorFormPartService.patchArticulusInteriorForm(form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalis], silvaOptionum.articulusInteriorSingularisOptionalis)
        }
        /* @tt{{{ @else }}}@ */
        this.articulusInteriorFormPartService.patchArticulusInteriorForm(form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularis], silvaOptionum.articulusInteriorSingularis)
        /* @tt{{{ @end-if }}}@ */
        /* @tt{{{ @end-foreach }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        if(silvaOptionum.articulusInteriorOptionalisIteratus != null) {
            for (let i = 0; i < silvaOptionum.articulusInteriorOptionalisIteratus.length; i++) {
                this.articulusInteriorFormPartService.patchArticulusInteriorForm(
                    form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus].at(i),
                    silvaOptionum.articulusInteriorOptionalisIteratus[i]
                )
            }
        }
        /* @tt{{{   @end-ignore-text  }}}@ */
    }

    public createSilvaOptionumWTOFromForm(form: FormGroup<OpusMagnumSilvaOptionumFormPartGroup>): SilvaOptionumWTO {
        return {
            /* @tt{{{
                @foreach [ iteratorExpression="model.item.attributesWithItemType" loopVariable="attributeWithItemType" ]
                @replace-value-by-expression
                        [ searchValue="articulusInteriorSingularisOptionalis" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                        [ searchValue="articulusInteriorSingularis" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                        [ searchValue="articulusInteriorOptionalisIteratus" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                        [ searchValue="articulusInteriorIteratus" replaceByExpression="attributeWithItemType.attributeName.camelCase" ]
                        [ searchValue="articulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.camelCase" ]
                        [ searchValue="ArticulusInterior" replaceByExpression="attributeWithItemType.referencedItem.itemName.pascalCase" ]

                }}}@  */
            /* @tt{{{   @if [ conditionExpression="attributeWithItemType.isList"]  }}}@ */
            /* @tt{{{   @if [ conditionExpression="attributeWithItemType.isNullable"]  }}}@ */
            articulusInteriorOptionalisIteratus: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull].value
                ? form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus].controls.map(
                    (controlEntry) => this.articulusInteriorFormPartService.createArticulusInteriorWTOFromForm(controlEntry))
                : null,
            /* @tt{{{   @else  }}}@ */
            articulusInteriorIteratus: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus].controls.map(
                (controlEntry) => this.articulusInteriorFormPartService.createArticulusInteriorWTOFromForm(controlEntry)
            ),
            /* @tt{{{   @end-if  }}}@ */
            /* @tt{{{   @else  }}}@ */
            /* @tt{{{   @if [ conditionExpression="attributeWithItemType.isNullable"]  }}}@ */
            articulusInteriorSingularisOptionalis: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull].value
                ? this.articulusInteriorFormPartService.createArticulusInteriorWTOFromForm(form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalis])
                : null,
            /* @tt{{{   @else  }}}@ */
            articulusInteriorSingularis: this.articulusInteriorFormPartService.createArticulusInteriorWTOFromForm(form.controls[OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularis]),
            /* @tt{{{   @end-if  }}}@ */
            /* @tt{{{   @end-if  }}}@ */

            /* @tt{{{  @end-foreach  }}}@ */

            /* @tt{{{
                @foreach [ iteratorExpression="model.item.builtInTypeAndEnumAttributes" loopVariable="attribute" ]
            }}}@  */
            /* @tt{{{
                @replace-value-by-expression
                    [ searchValue="iteratioSimpliciumTextuum" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="articulusInteriorOptionalisIteratus" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="articulusInteriorIteratus" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="articulusInteriorSingularisOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="articulusInteriorSingularis" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="articulusInterior" replaceByExpression="attribute.attributeName.camelCase" ]
                    [ searchValue="ArticulusInterior" replaceByExpression="attribute.attributeName.pascalCase" ]
                
            }}}@  */
            /* @tt{{{   @if [ conditionExpression="attribute.isNullable"]  }}}@ */
            campusTextusOptionalis: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull].value
                ? form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis].getRawValue()
                : null,
            /* @tt{{{   @else  }}}@ */
            // the non-null assertion is only needed for a mandatory reference, whose control holds
            // `null` until an entry is picked; for every other attribute it is a no-op
            campusTextusObligatorius: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius].getRawValue()!,
            /* @tt{{{   @end-if  }}}@ */
            /* @tt{{{  @end-foreach  }}}@ */
            /* @tt{{{   @ignore-text  }}}@ */
            appellatioOptionalisIteratus: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull].value
                ? form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus].getRawValue()
                : null,
            iteratioSimpliciumTextuum: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum].getRawValue(),
            relatioAdEntitatemOptionalisIteratus: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull].value
                ? form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus].getRawValue()
                : null,
            relatioAdEntitatemOptionalis: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull].value
                ? form.controls[OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis].getRawValue()
                : null,
            campusDiei: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull].value ? form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusDiei].getRawValue() : null,
            campusBivalens: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusBivalens].getRawValue(),
            campusNumerorum: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.campusNumerorum].getRawValue(),
            appellatio: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.appellatio].getRawValue(),
            indexUnicus: form.controls[OpusMagnumSilvaOptionumFormPartFieldName.indexUnicus].getRawValue(),
            /* @tt{{{   @end-ignore-text  }}}@ */
        };
    }
}
