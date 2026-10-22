/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemWTORenderer"
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
package org.codeblessing.alpstein.server.restapi.wto

/* @tt{{{   @if [ conditionExpression="model.containsUuidAttributes"]  }}}@ */
import java.util.UUID
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @ignore-text  }}}@ */
import java.time.LocalDate
/* @tt{{{   @end-ignore-text  }}}@ */

/**
 * Root WTO (Web Transfer Object), mirroring the Angular `SilvaOptionumWTO` interface
 * field by field so it serializes 1:1 for the client.
 */
data class SilvaOptionumWTO(
    /* @tt{{{
        @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]
        @replace-value-by-expression
            [ searchValue="indexUnicus" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="UUID" replaceByExpression="attribute.wtoAttributeType" ]
    }}}@ */
    val indexUnicus: UUID,
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    val campusTextusObligatorius: String,
    val campusTextusOptionalis: String?,
    val appellatio: AppellatioComisEnum,
    val articulusInteriorSingularis: ArticulusInteriorWTO,
    val articulusInteriorIteratus: List<ArticulusInteriorWTO>,
    val articulusInteriorSingularisOptionalis: ArticulusInteriorWTO?,
    val articulusInteriorOptionalisIteratus: List<ArticulusInteriorWTO>?,
    val appellatioOptionalisIteratus: List<AppellatioComisEnum>?,
    val campusDiei: LocalDate?,
    val campusBivalens: Boolean,
    val campusNumerorum: Int,
    val iteratioSimpliciumTextuum: List<String>,
    val relatioAdEntitatemOptionalisIteratus: List<UUID>?,
    val relatioAdEntitatemOptionalis: UUID?,
    /* @tt{{{   @end-ignore-text  }}}@ */
)
