package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/** A database configuration names a database value only for a value of its own enum type, and only once. */
class DbEnumValueValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.dbEnums.forEachIndexed { dbEnumIndex, dbEnum ->
            val dbEnumPath = path.child("dbEnums", dbEnumIndex, dbEnum.enumId.enumName)
            val enumType = schemaData.enums.firstOrNull { it.enumId == dbEnum.enumId } ?: return@forEachIndexed

            dbEnum.values.forEachIndexed { valueIndex, value ->
                if (value.enumValue !in enumType.enumValues) {
                    validationError(
                        dbEnumPath.child("values", valueIndex, value.enumValue),
                        "The database configuration names a database value for the enum value " +
                                "'${value.enumValue}', but the enum type '${enumType.enumName}' has no such " +
                                "value. Available are ${enumType.enumValues}.",
                    )
                }
            }

            dbEnum.values.withIndex()
                .groupBy { (_, value) -> value.enumValue }
                .filterValues { declarationsOfSameValue -> declarationsOfSameValue.size > 1 }
                .forEach { (enumValue, declarationsOfSameValue) ->
                    val declarations = declarationsOfSameValue.joinToString { (index, _) -> "values[$index]" }
                    validationError(
                        dbEnumPath,
                        "The database configuration names a database value for the enum value '$enumValue' " +
                                "${declarationsOfSameValue.size} times, namely in $declarations. " +
                                "Every enum value is named only once.",
                    )
                }
        }
    }
}
