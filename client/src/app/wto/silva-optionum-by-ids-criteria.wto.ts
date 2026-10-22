/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemByIdsCriteriaWtoRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.typescriptAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";

/* @tt{{{   @end-if  }}}@ */
/**
 * The SilvaOptionum by-ids criteria WTO (Web Transfer Object), sent as the request body of
 * the by-ids endpoint. It carries the identifier of every SilvaOptionum to resolve, which is
 * what a reference to this entity is stored as.
 */
export interface SilvaOptionumByIdsCriteriaWTO {
    indexUnicusList: Array<UUID>;
}
