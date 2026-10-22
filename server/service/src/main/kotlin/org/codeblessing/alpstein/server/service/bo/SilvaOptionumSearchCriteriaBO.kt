/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemSearchCriteriaBORenderer"
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

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.service.bo

/**
 * Business object for the search criteria of the SilvaOptionum aggregates.
 *
 * Holds a single free text [query]; a blank query matches every [SilvaOptionumBO].
 */
data class SilvaOptionumSearchCriteriaBO(
    val query: String,
)
