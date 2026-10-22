/* @tt{{{

    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityItemTableRowComponentTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityFormViewItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui.entityform"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="articulus-interior" replaceByExpression="model.item.itemName.kebabCase" ]
        [ searchValue="articulusInterior" replaceByExpression="model.item.itemName.camelCase" ]
        [ searchValue="ArticulusInterior" replaceByExpression="model.item.itemName.pascalCase" ]
        [ searchValue="opus-magnum" replaceByExpression="model.entity.entityName.kebabCase" ]
        [ searchValue="OpusMagnum" replaceByExpression="model.entity.entityName.pascalCase" ]
        [ searchValue="opusMagnum" replaceByExpression="model.entity.entityName.camelCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */

import {FormGroup} from "@angular/forms";
import {
    OpusMagnumArticulusInteriorFormPartGroup
} from "@app/opus-magnum/opus-magnum-form/opus-magnum-articulus-interior-form-part/opus-magnum-articulus-interior-form-part-group";

export interface OpusMagnumArticulusInteriorTableRow {
    /*
    @tt{{{
        
        @foreach [ iteratorExpression="model.item.attributes" loopVariable="attribute" ]

        @replace-value-by-expression
        [ searchValue="scriptumTriviale" replaceByExpression="attribute.attributeName.camelCase" ]
        [ searchValue="ScriptumTriviale" replaceByExpression="attribute.attributeName.pascalCase" ]
        
    }}}@
     */
    scriptumTriviale: string
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    numerusStupidus: number
    /* @tt{{{   @end-ignore-text  }}}@ */
    formGroup: FormGroup<OpusMagnumArticulusInteriorFormPartGroup>
}
