/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemByIdsResultWtoRenderer"
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
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.itemName.kebabCase" ]

    @modify-provided-filepath-by-replacements

}}}@ */
import {SilvaOptionumWTO} from "@app/wto/silva-optionum.wto";

/**
 * The SilvaOptionum by-ids result WTO (Web Transfer Object), returned by the by-ids endpoint.
 * It wraps the resolved SilvaOptionum instances; unknown identifiers are omitted.
 */
export interface SilvaOptionumByIdsResultWTO {
    silvaOptionumList: Array<SilvaOptionumWTO>;
}
