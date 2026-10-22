/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="DatabaseEnumsRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeEnumsRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        isList="yes"
        modelClassName="BeEnumModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="models"
    ]

    @replace-value-by-value
        [ searchValue="DatabaseEnums.kt" replaceByValue="GeneratedDatabaseEnums.kt" ]
    @modify-provided-filepath-by-replacements
    @end-replace-value-by-value

}}}@ */
package org.codeblessing.alpstein.server.persistence.postgres

/* @tt{{{
    @foreach [ iteratorExpression="models" loopVariable="enumType" ]
    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="enumType.enumName.pascalCase" ]
}}}@ */
import org.codeblessing.alpstein.server.service.bo.AppellatioComis
/* @tt{{{   @end-replace-value-by-expression @end-foreach  }}}@ */

/* @tt{{{
    @replace-value-by-value
        [ searchValue="DATABASE_ENUMS" replaceByValue="GENERATED_DATABASE_ENUMS" ]
}}}@ */
/**
 * How the values of every business enum are spelled in the database.
 */
internal val DATABASE_ENUMS: List<DatabaseEnum> = listOf(
    /* @tt{{{ @end-replace-value-by-value }}}@ */
    /* @tt{{{
        @foreach [ iteratorExpression="models" loopVariable="enumType" ]
        @replace-value-by-expression
            [ searchValue="AppellatioComis" replaceByExpression="enumType.enumName.pascalCase" ]
    }}}@ */
    DatabaseEnum(
        enumClass = AppellatioComis::class.java,
        databaseValueByEnumValue = mapOf(
            /* @tt{{{
                @foreach [ iteratorExpression="enumType.dbEnum.values" loopVariable="enumValue" ]
                @replace-value-by-expression
                    [ searchValue="VIR_HONORATUS" replaceByExpression="enumValue.enumValue.screamingSnakeCase" ]
                    [ searchValue="vir-honoratus" replaceByExpression="enumValue.databaseValue" ]
            }}}@ */
            AppellatioComis.VIR_HONORATUS to "vir-honoratus",
            /* @tt{{{   @end-replace-value-by-expression @end-foreach @ignore-text  }}}@ */
            AppellatioComis.FEMINA_HONESTA to "femina-honesta",
            /* @tt{{{   @end-ignore-text  }}}@ */
        ),
    ),
    /* @tt{{{   @end-replace-value-by-expression @end-foreach  }}}@ */
)
