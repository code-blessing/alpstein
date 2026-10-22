package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** A database configuration exists only for an enum type of the schema. */
class DbEnumEnumTypeExistenceValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.dbEnums.forEachIndexed { dbEnumIndex, dbEnum ->
            if (schemaData.enums.none { it.enumId == dbEnum.enumId }) {
                validationError(
                    path.child("dbEnums", dbEnumIndex, dbEnum.enumId.enumName),
                    "There is a database configuration for the enum type '${dbEnum.enumId.enumName}', but no " +
                            "such enum type is declared in the schema. " +
                            "Available are ${schemaData.enums.map { it.enumName }}.",
                )
            }
        }
    }
}
