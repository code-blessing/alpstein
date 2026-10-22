/* @tt{{{
    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemFormPartFieldNameRenderer"
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

/* @tt{{{   @ignore-text  }}}@ */
import {AppellatioComisEnum} from "@app/wto/appellatio-comis.enum";
/* @tt{{{   @end-ignore-text  }}}@ */

export enum OpusMagnumSilvaOptionumFormPartFieldName {
    /* @tt{{{ 
        @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

        @replace-value-by-expression
            [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="campusTextusOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]

    }}}@  */
    campusTextusOptionalis = "campusTextusOptionalis",
    /* @tt{{{   @if [ conditionExpression="attribute.isNullable"]  }}}@ */
    campusTextusOptionalisIsNotNull = "campusTextusOptionalisIsNotNull",
    /* @tt{{{   @end-if  }}}@ */
/* @tt{{{  @end-foreach  }}}@ */
/* @tt{{{   @ignore-text  }}}@ */
    campusTextusObligatorius = "campusTextusObligatorius",
    articulusInteriorSingularis = "articulusInteriorSingularis",
    articulusInteriorSingularisOptionalis = "articulusInteriorSingularisOptionalis",
    articulusInteriorSingularisOptionalisIsNotNull = "articulusInteriorSingularisOptionalisIsNotNull",
    articulusInteriorIteratus = "articulusInteriorIteratus",
    articulusInteriorOptionalisIteratus = "articulusInteriorOptionalisIteratus",
    articulusInteriorOptionalisIteratusIsNotNull = "articulusInteriorOptionalisIteratusIsNotNull",
    appellatioOptionalisIteratus = "appellatioOptionalisIteratus",
    appellatioOptionalisIteratusIsNotNull = "appellatioOptionalisIteratusIsNotNull",
    campusDieiIsNotNull = "campusDieiIsNotNull",
    campusDiei = "campusDiei",
    campusBivalens = "campusBivalens",
    appellatio = "appellatio",
    campusNumerorum = "campusNumerorum",
    indexUnicus = "indexUnicus",
    iteratioSimpliciumTextuum = "iteratioSimpliciumTextuum",
    relatioAdEntitatemOptionalisIteratus = "relatioAdEntitatemOptionalisIteratus",
    relatioAdEntitatemOptionalisIteratusIsNotNull = "relatioAdEntitatemOptionalisIteratusIsNotNull",
    relatioAdEntitatemOptionalis = "relatioAdEntitatemOptionalis",
    relatioAdEntitatemOptionalisIsNotNull = "relatioAdEntitatemOptionalisIsNotNull",
/* @tt{{{   @end-ignore-text  }}}@ */
}

