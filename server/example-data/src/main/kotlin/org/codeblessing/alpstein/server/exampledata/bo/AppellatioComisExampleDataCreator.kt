/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EnumExampleDataCreatorRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeEnumRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        modelClassName="BeEnumModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="AppellatioComis" replaceByExpression="model.enumName.pascalCase" ]
        [ searchValue="silvaoptionum" replaceByExpression="model.itemName.lowerCase" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.exampledata.bo

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
import org.codeblessing.alpstein.server.exampledata.framework.datagenerator.RandomEnumValueDataGenerator
import org.codeblessing.alpstein.server.service.bo.AppellatioComis

/**
 * Creates example data for the [AppellatioComis] business enum.
 */
@Component
class AppellatioComisExampleDataCreator(
    private val randomEnumValueDataGenerator: RandomEnumValueDataGenerator,
) {

    fun create(dataContext: DataContext): AppellatioComis =
        randomEnumValueDataGenerator.generateData(
            dataContext = dataContext,
            enumClass = AppellatioComis::class,
        )

    fun createList(dataContext: DataContext, size: Int): List<AppellatioComis> =
        randomEnumValueDataGenerator.generateDataList(
            dataContext = dataContext,
            enumClass = AppellatioComis::class,
            size = size,
        )
}
