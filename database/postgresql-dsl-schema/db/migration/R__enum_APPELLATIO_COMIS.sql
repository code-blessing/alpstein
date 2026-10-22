/* @tt{{{

    @template-renderer [
        templateRendererClassName="EnumTypeSchemaRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.db"
        templateRendererInterfaceName="DbEnumRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.db"
    ] [
        modelClassName="DbEnumModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.db"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="APPELLATIO_COMIS" replaceByExpression="model.enumTypeName" ]

    @modify-provided-filepath-by-replacements

}}}@ */
-- An enum type is created before the tables using it: Flyway runs the repeatable migrations in
-- the alphabetical order of their description, and 'enum_' comes before 'schema_'. Dropping the
-- type takes the columns of that type with it, so a changed enum needs the R__schema_* migrations
-- to run again as well.
DROP TYPE IF EXISTS APPELLATIO_COMIS CASCADE;

/* @tt{{{
    @replace-value-by-expression
        [ searchValue="'vir-honoratus', 'femina-honesta'" replaceByExpression="model.databaseValuesAsSqlLiterals" ]
}}}@ */
CREATE TYPE APPELLATIO_COMIS AS ENUM ('vir-honoratus', 'femina-honesta');
/* @tt{{{   @end-replace-value-by-expression  }}}@ */
