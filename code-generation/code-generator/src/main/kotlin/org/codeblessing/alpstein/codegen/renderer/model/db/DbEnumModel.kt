package org.codeblessing.alpstein.codegen.renderer.model.db

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.EnumId

/**
 * The database representation of one enum type: the SQL enum type its values are stored in,
 * if one is declared, and how every single value is spelled in the database.
 *
 * An enum type without an [enumTypeName] is stored as [DbSqlType.TEXT] and creates no SQL
 * type of its own.
 */
data class DbEnumModel(
    val enumId: EnumId,
    val enumName: NameCase,
    val enumTypeName: String?,
    val values: List<DbEnumValueModel>,
) {
    val hasEnumType: Boolean = enumTypeName != null

    /**
     * The values as the comma separated list of SQL string literals a `CREATE TYPE` needs,
     * e.g. `'G', 'PG', 'PG-13'`.
     */
    val databaseValuesAsSqlLiterals: String = values.joinToString { "'${it.databaseValue.replace("'", "''")}'" }
}

/**
 * How one value of an enum type is spelled in the database. The [databaseValue] is free of the
 * PascalCase rule an [enumValue] follows, therefore `PG13` can be stored as `PG-13`.
 */
data class DbEnumValueModel(
    val enumValue: NameCase,
    val databaseValue: String,
)
