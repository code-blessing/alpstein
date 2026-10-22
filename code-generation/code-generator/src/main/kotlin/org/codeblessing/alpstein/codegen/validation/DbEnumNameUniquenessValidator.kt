package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.codegen.renderer.model.db.DbNameDefaults
import org.codeblessing.alpstein.model.schema.DbEnum
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.EnumType
import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * Every SQL enum type name is used by one enum type only and, within an enum type, every
 * database value by one enum value only.
 *
 * Two enum values stored as the same database value could not be told apart when reading a
 * row back, therefore they are rejected even though the database would accept them.
 */
class DbEnumNameUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        val dbEnumPerEnumType: Map<EnumId, DbEnum> = schemaData.dbEnums.associateBy { it.enumId }
        val dbEnumsPath = path.child("dbEnums")

        schemaData.enums
            .mapNotNull { enumType -> dbEnumPerEnumType[enumType.enumId]?.enumTypeName?.let { it to enumType } }
            .groupBy({ (enumTypeName, _) -> enumTypeName }, { (_, enumType) -> enumType })
            .filterValues { enumTypesOfSameName -> enumTypesOfSameName.size > 1 }
            .forEach { (enumTypeName, enumTypesOfSameName) ->
                validationError(
                    dbEnumsPath,
                    "The SQL enum type '$enumTypeName' is used by ${enumTypesOfSameName.size} enum types, " +
                            "namely by ${enumTypesOfSameName.map { it.enumName }}. " +
                            "Every enum type is stored in a SQL enum type of its own.",
                )
            }

        schemaData.enums.forEach { enumType ->
            validateDatabaseValues(enumType, dbEnumPerEnumType[enumType.enumId], dbEnumsPath)
        }
    }

    private fun validateDatabaseValues(enumType: EnumType, dbEnum: DbEnum?, path: ValidationPath) {
        enumType.enumValues
            .groupBy { DbNameDefaults.databaseValue(it, dbEnum) }
            .filterValues { enumValuesOfSameDatabaseValue -> enumValuesOfSameDatabaseValue.size > 1 }
            .forEach { (databaseValue, enumValuesOfSameDatabaseValue) ->
                validationError(
                    path,
                    "The database value '$databaseValue' of the enum type '${enumType.enumName}' stores " +
                            "${enumValuesOfSameDatabaseValue.size} enum values, namely " +
                            "$enumValuesOfSameDatabaseValue. Every enum value is stored as a database value " +
                            "of its own.",
                )
            }
    }
}
