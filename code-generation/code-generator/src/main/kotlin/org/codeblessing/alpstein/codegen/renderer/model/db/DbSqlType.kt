package org.codeblessing.alpstein.codegen.renderer.model.db

/**
 * The SQL type a column is declared with, named as PostgreSQL spells it.
 *
 * A nested item has no flat relational representation and is therefore stored as [JSONB]; an
 * enum value is stored as [TEXT]. A list of values becomes an array of the value's type,
 * spelled by `DbColumnModel.sqlTypeName`; only a list of nested items stays [JSONB].
 */
enum class DbSqlType(val sqlTypeName: String) {
    UUID("uuid"),
    TEXT("text"),
    INTEGER("integer"),
    DOUBLE_PRECISION("double precision"),
    BOOLEAN("boolean"),
    JSONB("jsonb"),
}
