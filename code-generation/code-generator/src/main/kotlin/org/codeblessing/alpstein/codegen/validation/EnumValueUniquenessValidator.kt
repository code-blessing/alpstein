package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** Every value of an enum type is declared only once within that enum type. */
class EnumValueUniquenessValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.enums.forEachIndexed { enumIndex, enumType ->
            enumType.enumValues.withIndex()
                .groupBy { (_, enumValue) -> enumValue }
                .filterValues { sameEnumValues -> sameEnumValues.size > 1 }
                .forEach { (enumValue, sameEnumValues) ->
                    val declarations = sameEnumValues.joinToString { (index, _) -> "enumValues[$index]" }
                    validationError(
                        path.child("enums", enumIndex, enumType.enumName),
                        "The enum type declares the value '$enumValue' ${sameEnumValues.size} times, namely as " +
                                "$declarations. Every enum value is declared only once per enum type.",
                    )
                }
        }
    }
}
