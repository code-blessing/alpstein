/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemExampleDataFetcherRenderer"
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

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.kotlinAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.exampledata.silvaoptionum

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
import org.codeblessing.alpstein.server.service.bo.SilvaOptionumBO
import org.codeblessing.alpstein.server.service.silvaoptionum.SilvaOptionumRepository
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import java.util.UUID
/* @tt{{{   @end-if  }}}@ */

/**
 * Fetches already persisted SilvaOptionum example data so that other example data creators can
 * reference it.
 *
 * Unlike the example data creators, this does not create anything: it reads the
 * [SilvaOptionumBO] instances that the [SilvaOptionumExampleDataPopulator] has written before,
 * so that a reference is always a valid one. It therefore only returns something once
 * SilvaOptionum example data has been created.
 */
@Component
class SilvaOptionumExampleDataFetcher(
    private val silvaOptionumRepository: SilvaOptionumRepository,
) {

    /**
     * A random subset of the indexUnicus of the existing [SilvaOptionumBO] instances, or an
     * empty list if none exist yet.
     */
    fun fetchRandomKeysList(dataContext: DataContext): List<UUID> =
        FakerHelper.manyOfRandom(
            dataContext = dataContext,
            array = silvaOptionumRepository.findAll().toTypedArray(),
            size = FakerHelper.referenceListRandomSize(dataContext),
        ).map { it.indexUnicus }

    /**
     * The indexUnicus of one random existing [SilvaOptionumBO].
     *
     * A mandatory reference needs a value even where nothing can be referenced yet, for example
     * where an item references itself or where the referenced item has not created its
     * example data yet. Such a reference gets [UNRESOLVABLE_KEY], which resolves to nothing and
     * is therefore shown with the fallback of the display attributes.
     */
    fun fetchRandomKey(dataContext: DataContext): UUID {
        val silvaOptionumList = silvaOptionumRepository.findAll()
        if (silvaOptionumList.isEmpty()) {
            return UNRESOLVABLE_KEY
        }
        return FakerHelper.oneRandomOf(
            dataContext = dataContext,
            array = silvaOptionumList.toTypedArray(),
        ).indexUnicus
    }

    private companion object {
        /* @tt{{{
            @replace-value-by-expression
                [ searchValue="UUID(0L, 0L)" replaceByExpression="model.unresolvablePrimaryKeyValueExpression" ]
        }}}@ */
        /** A primary key no item is ever identified by. */
        private val UNRESOLVABLE_KEY: UUID = UUID(0L, 0L)
        /* @tt{{{   @end-replace-value-by-expression  }}}@ */
    }
}
