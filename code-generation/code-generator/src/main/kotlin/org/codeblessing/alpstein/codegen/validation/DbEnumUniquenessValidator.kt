package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** Every enum type is configured for the database only once. */
class DbEnumUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.dbEnums.withIndex()
            .groupBy { (_, dbEnum) -> dbEnum.enumId }
            .filterValues { dbEnumsOfSameEnumType -> dbEnumsOfSameEnumType.size > 1 }
            .forEach { (enumId, dbEnumsOfSameEnumType) ->
                val declarations = dbEnumsOfSameEnumType.joinToString { (index, _) -> "dbEnums[$index]" }
                validationError(
                    path.child("dbEnums"),
                    "The enum type '${enumId.enumName}' is configured for the database by " +
                            "${dbEnumsOfSameEnumType.size} declarations, namely by $declarations. " +
                            "An enum type is configured for the database only once.",
                )
            }
    }
}
