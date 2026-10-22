/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemExampleDataPopulatorRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        modelClassName="BeItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="silvaoptionum" replaceByExpression="model.itemName.lowerCase" ]
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.exampledata.silvaoptionum

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.ExampleDataCreator
import org.codeblessing.alpstein.server.exampledata.bo.SilvaOptionumExampleDataCreator
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumBO
import org.codeblessing.alpstein.server.service.silvaoptionum.SilvaOptionumRepository

/**
 * Orchestrates the creation of SilvaOptionum example data.
 *
 * Builds a list of [SilvaOptionumBO] aggregates by delegating to the per-business-object
 * example data creators and persists the result through the [SilvaOptionumRepository] port.
 */
@Component
class SilvaOptionumExampleDataPopulator(
    private val silvaOptionumExampleDataCreator: SilvaOptionumExampleDataCreator,
    private val silvaOptionumRepository: SilvaOptionumRepository,
): ExampleDataCreator {

    /**
     * Creates the example [SilvaOptionumBO] aggregates and writes each of them to the
     * persistence via the [SilvaOptionumRepository].
     *
     * Every aggregate is stored under the key the persistence hands out rather than the one the
     * example data creator generated, so that each of them gets a key of its own.
     */
    override fun createExampleData(dataContext: DataContext) {
        val created = silvaOptionumExampleDataCreator.createList(dataContext, FakerHelper.itemListRandomSize(dataContext))
        created.forEach { silvaOptionumRepository.create(it) }
    }
}
