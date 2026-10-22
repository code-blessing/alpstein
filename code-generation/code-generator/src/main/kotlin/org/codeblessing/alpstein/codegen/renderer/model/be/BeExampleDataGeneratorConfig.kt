package org.codeblessing.alpstein.codegen.renderer.model.be

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import java.util.Locale.getDefault

data class BeExampleDataGeneratorConfig(
    val generatorNamePrefix: NameCase,
    val isNullable: Boolean,
    val numberOfEntries: Int
) {
    val isMultiple: Boolean = numberOfEntries > 1

    val exampleDataGeneratorVariableName: String = "${generatorNamePrefix.camelCase}DataGenerator"

    val exampleDataGeneratorClassName: String = "${generatorNamePrefix.pascalCase}DataGenerator"

    val exampleDataGeneratorPackageName: String = "org.codeblessing.alpstein.server.exampledata.framework.datagenerator"

    val fullQualifiedName: String = "${exampleDataGeneratorPackageName}.${exampleDataGeneratorClassName}"

    val exampleDataGeneratorCallExpression: String = if(isMultiple) {
        "${exampleDataGeneratorVariableName}.generateDataList(dataContext)"
    } else {
        "${exampleDataGeneratorVariableName}.generateData(dataContext)"
    }

    private fun decapitalize(value: String): String =
        value.replaceFirstChar { it.lowercase(getDefault()) }
    private fun capitalize(value: String): String =
        value.replaceFirstChar { if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString() }
}
