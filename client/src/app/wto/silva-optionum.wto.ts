/* @tt{{{
    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemWTOInterfaceRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="silva-optionum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */

/* @tt{{{
    @foreach [ iteratorExpression="model.directlyNestedItems" loopVariable="nestedItem" ]

    @replace-value-by-expression
        [ searchValue="ArticulusInterior" replaceByExpression="nestedItem.itemName.pascalCase" ]
        [ searchValue="articulus-interior" replaceByExpression="nestedItem.itemName.kebabCase" ]
}}}@  */
import {ArticulusInteriorWTO} from "@app/wto/articulus-interior.wto";
/* @tt{{{   @end-foreach  }}}@ */
/* @tt{{{   @if [ conditionExpression="model.containsUuidAttributes"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{
    @foreach [ iteratorExpression="model.usedEnums" loopVariable="usedEnum" ]

    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="usedEnum.enumName.pascalCase" ]
        [ searchValue="appellatio-comis" replaceByExpression="usedEnum.enumName.kebabCase" ]
}}}@  */
import {AppellatioComisEnum} from "@app/wto/appellatio-comis.enum";
/* @tt{{{   @end-foreach  }}}@ */

/**
 * The SilvaOptionum WTO (Web Transfer Object) class.
 */
export interface SilvaOptionumWTO {
    /* @tt{{{
        @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]

        @replace-value-by-expression
            [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="string" replaceByExpression="attribute.typescriptAttributeType" ]

    }}}@  */
    campusTextusObligatorius: string;
    /* @tt{{{
        
        @end-foreach
        
    }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    campusTextusOptionalis: string | null;
    appellatio: AppellatioComisEnum;
    articulusInteriorSingularis: ArticulusInteriorWTO;
    articulusInteriorIteratus: Array<ArticulusInteriorWTO>;
    articulusInteriorSingularisOptionalis: ArticulusInteriorWTO | null;
    articulusInteriorOptionalisIteratus: Array<ArticulusInteriorWTO> | null;
    appellatioOptionalisIteratus: Array<AppellatioComisEnum> | null;
    campusDiei: Date | null;
    campusBivalens: boolean;
    campusNumerorum: number;
    indexUnicus: UUID;
    iteratioSimpliciumTextuum: Array<string>;
    relatioAdEntitatemOptionalisIteratus: Array<UUID> | null;
    relatioAdEntitatemOptionalis: UUID | null;
    /* @tt{{{   @end-ignore-text }}}@ */
}
