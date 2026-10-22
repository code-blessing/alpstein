package org.codeblessing.alpstein.codegen.validation

/**
 * The breadcrumb to the place within the `SchemaData` a validation message is about,
 * e.g. `SchemaData > items[0] 'Contact' > attributes[3] 'Nickname'`.
 */
class ValidationPath private constructor(
    private val breadcrumbs: List<String>,
) {
    fun child(name: String): ValidationPath = ValidationPath(breadcrumbs + name)

    fun child(property: String, index: Int): ValidationPath = child("$property[$index]")

    fun child(property: String, index: Int, name: String): ValidationPath = child("$property[$index] '$name'")

    override fun toString(): String = breadcrumbs.joinToString(separator = " > ")

    companion object {
        val schemaData: ValidationPath = ValidationPath(listOf("SchemaData"))
    }
}
