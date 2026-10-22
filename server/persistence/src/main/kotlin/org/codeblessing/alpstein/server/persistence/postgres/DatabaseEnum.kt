package org.codeblessing.alpstein.server.persistence.postgres

/**
 * How the values of one business enum are spelled in the database, which is free of the
 * PascalCase rule the model imposes on an enum value: `MpaaRating.P_G13` is stored as `PG-13`.
 *
 * The declarations live in [DATABASE_ENUMS] for the enums written by hand and in
 * `GENERATED_DATABASE_ENUMS` for the enums the code generator writes.
 */
internal class DatabaseEnum(
    val enumClass: Class<*>,
    val databaseValueByEnumValue: Map<out Enum<*>, String>,
) {
    val enumValueByDatabaseValue: Map<String, Enum<*>> =
        databaseValueByEnumValue.entries.associate { (enumValue, databaseValue) -> databaseValue to enumValue }
}
