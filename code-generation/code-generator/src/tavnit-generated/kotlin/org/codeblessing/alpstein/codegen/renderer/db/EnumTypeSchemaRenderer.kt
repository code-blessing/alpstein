/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.db

import org.codeblessing.alpstein.codegen.renderer.model.db.DbEnumModel

/**
 * Generate the content for the template `EnumTypeSchemaRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `R__enum_APPELLATIO_COMIS.sql`
 * - path: `R__enum_APPELLATIO_COMIS.sql`
 */
object EnumTypeSchemaRenderer : DbEnumRenderer {

    override fun renderTemplate(model: DbEnumModel): String {
        return """
          |-- An enum type is created before the tables using it: Flyway runs the repeatable migrations in
          |-- the alphabetical order of their description, and 'enum_' comes before 'schema_'. Dropping the
          |-- type takes the columns of that type with it, so a changed enum needs the R__schema_* migrations
          |-- to run again as well.
          |DROP TYPE IF EXISTS ${model.enumTypeName} CASCADE;
          |
          |CREATE TYPE ${model.enumTypeName} AS ENUM (${model.databaseValuesAsSqlLiterals});
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: DbEnumModel): String {
      return "R__enum_${model.enumTypeName}.sql"
    }
}