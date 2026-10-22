/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemSearchCriteriaMapperRenderer"
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
package org.codeblessing.alpstein.server.restapi.wto.mapper

import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumSearchCriteriaWTO
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumSearchCriteriaBO

object SilvaOptionumSearchCriteriaMapper {

    fun SilvaOptionumSearchCriteriaWTO.toBo(): SilvaOptionumSearchCriteriaBO = SilvaOptionumSearchCriteriaBO(
        query = query,
    )
}
