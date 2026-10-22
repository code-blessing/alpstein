package org.codeblessing.alpstein.codegen.validation

/**
 * Thrown as soon as one aspect of the `SchemaData` is violated. The [path] tells
 * where exactly in the schema declaration the violation is, the [problem] what it is.
 */
class SchemaDataValidationException(
    val path: ValidationPath,
    val problem: String,
) : RuntimeException("Invalid schema data: $problem\n    at: $path")

fun validationError(path: ValidationPath, problem: String): Nothing = throw SchemaDataValidationException(path, problem)
