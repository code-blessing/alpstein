/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemSearchResultWtoRenderer"
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
 * The SilvaOptionum search result WTO (Web Transfer Object), returned by the search
 * endpoint. It wraps the found SilvaOptionum instances.
 */
export interface SilvaOptionumSearchResultWTO {
    silvaOptionumList: Array<SilvaOptionumWTO>;
}
