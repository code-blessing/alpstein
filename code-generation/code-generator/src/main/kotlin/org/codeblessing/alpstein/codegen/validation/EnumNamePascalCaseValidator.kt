package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** Every enum name is in PascalCase. */
class EnumNamePascalCaseValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.enums.forEachIndexed { index, enumType ->
            if (!PascalCase.isPascalCase(enumType.enumName)) {
                validationError(
                    path.child("enums", index, enumType.enumName),
                    "The enum name ${PascalCase.violationDescription(enumType.enumName)}",
                )
            }
        }
    }
}
