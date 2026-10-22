/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemSearchCriteriaWtoRenderer"
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

    @modify-provided-filepath-by-replacements

}}}@ */

/**
 * The SilvaOptionum search criteria WTO (Web Transfer Object), sent as the request body
 * of the search endpoint. A blank query matches every SilvaOptionum.
 */
export interface SilvaOptionumSearchCriteriaWTO {
    query: string;
}
