/* @tt{{{
    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemFormPartGroupRenderer"
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

import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {OpusMagnumSilvaOptionumFormPartFieldName} from "@app/opus-magnum/opus-magnum-form/opus-magnum-silva-optionum-form-part/opus-magnum-silva-optionum-form-part-field-name";
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
    @foreach [ iteratorExpression="model.item.directlyNestedItems" loopVariable="directlyNestedItem" ]

    @replace-value-by-expression
        [ searchValue="ArticulusInterior" replaceByExpression="directlyNestedItem.itemName.pascalCase" ]
        [ searchValue="articulus-interior" replaceByExpression="directlyNestedItem.itemName.kebabCase" ]

}}}@  */

import {
    OpusMagnumArticulusInteriorFormPartGroup
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-group";
/* @tt{{{  @end-foreach  }}}@ */

export interface OpusMagnumSilvaOptionumFormPartGroup {
    /* @tt{{{ 
        @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

        @replace-value-by-expression
            [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="FormControl<string>" replaceByExpression="attribute.angularFormControlTypeWithCollection(model.entity.entityName)" ]

    }}}@  */
    [OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalis]: FormControl<string>,
    /* @tt{{{   @if [ conditionExpression="attribute.isNullable"] }}}@ */
    [OpusMagnumSilvaOptionumFormPartFieldName.campusTextusOptionalisIsNotNull]: FormControl<boolean>,
    /* @tt{{{   @end-if }}}@ */
    /* @tt{{{  @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    [OpusMagnumSilvaOptionumFormPartFieldName.campusTextusObligatorius]: FormControl<string>,
    [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularis]: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>,
    [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalis]: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>,
    [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorSingularisOptionalisIsNotNull]: FormControl<boolean>,
    [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorIteratus]: FormArray<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>,
    [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratus]: FormArray<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>>,
    [OpusMagnumSilvaOptionumFormPartFieldName.articulusInteriorOptionalisIteratusIsNotNull]: FormControl<boolean>,
    [OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratus]: FormArray<FormControl<AppellatioComisEnum>>,
    [OpusMagnumSilvaOptionumFormPartFieldName.appellatioOptionalisIteratusIsNotNull]: FormControl<boolean>,
    [OpusMagnumSilvaOptionumFormPartFieldName.campusDieiIsNotNull]: FormControl<boolean>,
    [OpusMagnumSilvaOptionumFormPartFieldName.campusDiei]: FormControl<Date>,
    [OpusMagnumSilvaOptionumFormPartFieldName.campusBivalens]: FormControl<boolean>,
    [OpusMagnumSilvaOptionumFormPartFieldName.appellatio]: FormControl<AppellatioComisEnum>,
    [OpusMagnumSilvaOptionumFormPartFieldName.campusNumerorum]: FormControl<number>,
    [OpusMagnumSilvaOptionumFormPartFieldName.indexUnicus]: FormControl<UUID>,
    [OpusMagnumSilvaOptionumFormPartFieldName.iteratioSimpliciumTextuum]: FormArray<FormControl<string>>,
    [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratus]: FormArray<FormControl<UUID>>,
    [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIteratusIsNotNull]: FormControl<boolean>,
    [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalis]: FormControl<UUID | null>,
    [OpusMagnumSilvaOptionumFormPartFieldName.relatioAdEntitatemOptionalisIsNotNull]: FormControl<boolean>,
    /* @tt{{{   @end-ignore-text  }}}@ */
}
