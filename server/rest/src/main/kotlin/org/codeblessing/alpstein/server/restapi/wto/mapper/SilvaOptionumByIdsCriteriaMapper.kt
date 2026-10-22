/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemByIdsCriteriaMapperRenderer"
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

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.restapi.wto.mapper

import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumByIdsCriteriaWTO
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumByIdsCriteriaBO

object SilvaOptionumByIdsCriteriaMapper {

    fun SilvaOptionumByIdsCriteriaWTO.toBo(): SilvaOptionumByIdsCriteriaBO = SilvaOptionumByIdsCriteriaBO(
        indexUnicusList = indexUnicusList,
    )
}
