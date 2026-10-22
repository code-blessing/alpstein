package org.codeblessing.alpstein.codegen.renderer.model.ui

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.EnumType

data class UiEnumModel(
    private val enumType: EnumType,
) {
    val enumId: EnumId = enumType.enumId
    val enumName: NameCase = NameCase(enumType.enumId.enumName)
    val enumValues: List<NameCase> = enumType.enumValues.map { NameCase(it) }

    /** The TypeScript enum type name, e.g. `AppellatioComisEnum`. */
    val enumClassName: String = "${enumName.pascalCase}Enum"

    /** The import path of the generated enum definition, e.g. `@app/wto/appellatio-comis.enum`. */
    val enumImportPath: String = "@app/wto/${enumName.kebabCase}.enum"

    /** The generated enum selector component class name, e.g. `AppellatioComisSelectorComponent`. */
    val selectorComponentClassName: String = "${enumName.pascalCase}SelectorComponent"

    /** The import path of the generated enum selector component. */
    val selectorComponentImportPath: String =
        "@app/enum/${enumName.kebabCase}-input-selection/${enumName.kebabCase}-selector.component"

    /** The angular selector tag of the generated enum selector component, e.g. `app-appellatio-comis-selector`. */
    val selectorComponentTagName: String = "app-${enumName.kebabCase}-selector"

    /**
     * A valid initial value expression for a form control of this enum,
     * e.g. `AppellatioComisEnum.VIR_HONORATUS`. Uses the first enum value.
     */
    val angularFormInitialValue: String = "$enumClassName.${enumValues.first().screamingSnakeCase}"
}
