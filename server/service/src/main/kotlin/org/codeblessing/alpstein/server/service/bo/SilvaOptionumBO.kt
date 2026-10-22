/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemBORenderer"
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

/* @tt{{{   @if [ conditionExpression="model.containsUuidAttributes"]  }}}@ */
import java.util.UUID
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{   @ignore-text  }}}@ */
import java.time.LocalDate
/* @tt{{{   @end-ignore-text  }}}@ */

/**
 * Business object for the [SilvaOptionumBO] item.
 */
data class SilvaOptionumBO(
    /* @tt{{{
        @foreach [ iteratorExpression="model.attributes" loopVariable="attribute" ]
        @replace-value-by-expression
            [ searchValue="indexUnicus" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="UUID" replaceByExpression="attribute.kotlinAttributeType" ]
    }}}@ */
    val indexUnicus: UUID,
    /* @tt{{{   @end-foreach  }}}@ */
    /* @tt{{{   @ignore-text  }}}@ */
    val campusTextusObligatorius: String,
    val campusTextusOptionalis: String?,
    val appellatio: AppellatioComis,
    val articulusInteriorSingularis: ArticulusInteriorBO,
    val articulusInteriorIteratus: List<ArticulusInteriorBO>,
    val articulusInteriorSingularisOptionalis: ArticulusInteriorBO?,
    val articulusInteriorOptionalisIteratus: List<ArticulusInteriorBO>?,
    val appellatioOptionalisIteratus: List<AppellatioComis>?,
    val campusDiei: LocalDate?,
    val campusBivalens: Boolean,
    val campusNumerorum: Int,
    val iteratioSimpliciumTextuum: List<String>,
    val relatioAdEntitatemOptionalisIteratus: List<UUID>?,
    val relatioAdEntitatemOptionalis: UUID?,
    /* @tt{{{   @end-ignore-text  }}}@ */
)
