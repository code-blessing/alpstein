/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemMapperRenderer"
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

import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumWTO
/* @tt{{{
    @foreach [ iteratorExpression="model.usedEnums" loopVariable="usedEnum" ]
    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="usedEnum.enumName.pascalCase" ]
}}}@ */
import org.codeblessing.alpstein.server.restapi.wto.mapper.AppellatioComisMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.AppellatioComisMapper.toWto
/* @tt{{{   @end-foreach  }}}@ */
/* @tt{{{
    @foreach [ iteratorExpression="model.directlyNestedItems" loopVariable="nestedItem" ]
    @replace-value-by-expression
        [ searchValue="ArticulusInterior" replaceByExpression="nestedItem.itemName.pascalCase" ]
}}}@ */
import org.codeblessing.alpstein.server.restapi.wto.mapper.ArticulusInteriorMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.ArticulusInteriorMapper.toWto
/* @tt{{{   @end-foreach  }}}@ */
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumBO

/**
 * Maps between the WTOs (transport layer) and BOs (business layer).
 */
object SilvaOptionumMapper {

    fun SilvaOptionumWTO.toBo(): SilvaOptionumBO = SilvaOptionumBO(
        /* @tt{{{
            @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]
            @replace-value-by-expression
                [ searchValue="appellatio" replaceByExpression="attribute.attributeName.camelCase" ]
                [ searchValue=".toBo()" replaceByExpression="attribute.toBoMappingSuffix" ]
        }}}@ */
        appellatio = appellatio.toBo(),
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        indexUnicus = indexUnicus,
        campusTextusObligatorius = campusTextusObligatorius,
        campusTextusOptionalis = campusTextusOptionalis,
        articulusInteriorSingularis = articulusInteriorSingularis.toBo(),
        articulusInteriorIteratus = articulusInteriorIteratus.map { it.toBo() },
        articulusInteriorSingularisOptionalis = articulusInteriorSingularisOptionalis?.toBo(),
        articulusInteriorOptionalisIteratus = articulusInteriorOptionalisIteratus?.map { it.toBo() },
        appellatioOptionalisIteratus = appellatioOptionalisIteratus?.map { it.toBo() },
        campusDiei = campusDiei,
        campusBivalens = campusBivalens,
        campusNumerorum = campusNumerorum,
        iteratioSimpliciumTextuum = iteratioSimpliciumTextuum,
        relatioAdEntitatemOptionalisIteratus = relatioAdEntitatemOptionalisIteratus,
        relatioAdEntitatemOptionalis = relatioAdEntitatemOptionalis,
        /* @tt{{{   @end-ignore-text  }}}@ */
    )

    fun SilvaOptionumBO.toWto(): SilvaOptionumWTO = SilvaOptionumWTO(
        /* @tt{{{
            @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]
            @replace-value-by-expression
                [ searchValue="appellatio" replaceByExpression="attribute.attributeName.camelCase" ]
                [ searchValue=".toWto()" replaceByExpression="attribute.toWtoMappingSuffix" ]
        }}}@ */
        appellatio = appellatio.toWto(),
        /* @tt{{{   @end-foreach  }}}@ */
        /* @tt{{{   @ignore-text  }}}@ */
        indexUnicus = indexUnicus,
        campusTextusObligatorius = campusTextusObligatorius,
        campusTextusOptionalis = campusTextusOptionalis,
        articulusInteriorSingularis = articulusInteriorSingularis.toWto(),
        articulusInteriorIteratus = articulusInteriorIteratus.map { it.toWto() },
        articulusInteriorSingularisOptionalis = articulusInteriorSingularisOptionalis?.toWto(),
        articulusInteriorOptionalisIteratus = articulusInteriorOptionalisIteratus?.map { it.toWto() },
        appellatioOptionalisIteratus = appellatioOptionalisIteratus?.map { it.toWto() },
        campusDiei = campusDiei,
        campusBivalens = campusBivalens,
        campusNumerorum = campusNumerorum,
        iteratioSimpliciumTextuum = iteratioSimpliciumTextuum,
        relatioAdEntitatemOptionalisIteratus = relatioAdEntitatemOptionalisIteratus,
        relatioAdEntitatemOptionalis = relatioAdEntitatemOptionalis,
        /* @tt{{{   @end-ignore-text  }}}@ */
    )
}
