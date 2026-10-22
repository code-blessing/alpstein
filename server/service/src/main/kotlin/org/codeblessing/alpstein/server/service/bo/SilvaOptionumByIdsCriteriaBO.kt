/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemByIdsCriteriaBORenderer"
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
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.kotlinAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.service.bo

/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import java.util.UUID

/* @tt{{{   @end-if  }}}@ */
/**
 * Business object for resolving a whole set of references to SilvaOptionum aggregates at once.
 *
 * Holds the identifiers a reference to this item is stored as; unknown ones are simply not
 * part of the result.
 */
data class SilvaOptionumByIdsCriteriaBO(
    val indexUnicusList: List<UUID>,
)
