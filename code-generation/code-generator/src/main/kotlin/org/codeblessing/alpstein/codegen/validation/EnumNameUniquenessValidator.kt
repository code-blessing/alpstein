package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** Every enum name is declared only once within the whole schema. */
class EnumNameUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.enums.withIndex()
            .groupBy { (_, enumType) -> enumType.enumName }
            .filterValues { enumsWithSameName -> enumsWithSameName.size > 1 }
            .forEach { (enumName, enumsWithSameName) ->
                val declarations = enumsWithSameName
                    .joinToString { (index, enumType) -> "enums[$index] (${enumType.enumId})" }
                validationError(
                    path.child("enums"),
                    "The enum name '$enumName' is declared by ${enumsWithSameName.size} enum types, namely by " +
                            "$declarations. Every enum name is declared only once.",
                )
            }
    }
}
