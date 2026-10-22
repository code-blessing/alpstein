package org.codeblessing.alpstein.codegen.validation

/**
 * A name in PascalCase starts with an uppercase letter and consists of
 * letters and digits only, e.g. `MaritalStatus`.
 */
object PascalCase {
    private val pascalCasePattern = Regex("[A-Z][A-Za-z0-9]*")

    fun isPascalCase(name: String): Boolean = pascalCasePattern.matches(name)

    /**
     * Describes why [name] is not in PascalCase, with a hint how to write it
     * instead as long as a PascalCase name can be derived from it at all.
     */
    fun violationDescription(name: String): String {
        val derivedName = name.filter { it.isLetterOrDigit() }.replaceFirstChar { it.uppercase() }
        val hint = if (isPascalCase(derivedName)) " Declare it as '$derivedName' instead." else ""
        return "'$name' is not in PascalCase: it has to start with an uppercase letter and " +
                "consist of letters and digits only.$hint"
    }
}
