/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemSearchResultWTORenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        modelClassName="BeItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.restapi.wto

data class SilvaOptionumSearchResultWTO(
    val silvaOptionumList: List<SilvaOptionumWTO>,
)
