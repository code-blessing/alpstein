/* @tt{{{
    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemFormPartInitialValueServiceRenderer"
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
import {FormGroup} from "@angular/forms";
/* @tt{{{   @if [ conditionExpression="model.item.containsUuidAttributes"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */

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
/* @tt{{{
    @foreach [ iteratorExpression="model.item.usedEnums" loopVariable="usedEnum" ]

    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="usedEnum.enumName.pascalCase" ]
        [ searchValue="appellatio-comis" replaceByExpression="usedEnum.enumName.kebabCase" ]
}}}@  */
import {AppellatioComisEnum} from "@app/wto/appellatio-comis.enum";
/* @tt{{{   @end-foreach  }}}@ */

@Injectable({providedIn: 'root'})
export class OpusMagnumSilvaOptionumFormPartInitialValueService {
    /* @tt{{{ 
        @foreach [ iteratorExpression="model.item.attributesWithAngularFormInitialValues" loopVariable="attribute" ]
        @replace-value-by-expression
            [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="string" replaceByExpression="attribute.angularInitialValueFormType(model.entity.entityName)" ]
            [ searchValue="''" replaceByExpression="attribute.angularFormInitialValue" ]

    }}}@  */
    campusTextusObligatoriusInitialValue(): string {
        return ''
    }
    /* @tt{{{  @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text }}}@ */

    campusTextusOptionalisInitialValue(): string {
        return ''
    }
    articulusInteriorIteratusInitialValue(): Array<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>> {
        return []
    }
    articulusInteriorOptionalisIteratusInitialValue(): Array<FormGroup<OpusMagnumArticulusInteriorFormPartGroup>> {
        return []
    }
    campusDieiInitialValue(): Date {
        return new Date();
    }
    campusBivalensInitialValue(): boolean {
        return false;
    }
    campusNumerorumInitialValue(): number {
        return 0;
    }
    appellatioInitialValue(): AppellatioComisEnum {
        return AppellatioComisEnum.FEMINA_HONESTA;
    }
    appellatioOptionalisIteratusInitialValue(): AppellatioComisEnum {
        return AppellatioComisEnum.FEMINA_HONESTA;
    }
    indexUnicusInitialValue(): UUID {
        return crypto.randomUUID()
    }
    iteratioSimpliciumTextuumInitialValue(): string {
        return ''
    }
    /* @tt{{{   @end-ignore-text }}}@ */
}
