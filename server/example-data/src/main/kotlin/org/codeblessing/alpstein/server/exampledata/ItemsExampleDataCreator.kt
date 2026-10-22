/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemsExampleDataCreatorRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        modelClassName="BeModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="model"
    ]

    @replace-value-by-value
        [ searchValue="ItemsExampleDataCreator.kt" replaceByValue="GeneratedItemsExampleDataCreator.kt" ]
    @modify-provided-filepath-by-replacements
    @end-replace-value-by-value

}}}@ */
package org.codeblessing.alpstein.server.exampledata

import org.springframework.stereotype.Component
/* @tt{{{
    @foreach [ iteratorExpression="model.itemsWithPrimaryKey" loopVariable="item" ]
    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="item.itemName.pascalCase" ]
        [ searchValue="silvaoptionum" replaceByExpression="item.itemName.lowerCase" ]
}}}@ */
import org.codeblessing.alpstein.server.exampledata.silvaoptionum.SilvaOptionumExampleDataPopulator
/* @tt{{{ @end-replace-value-by-expression @end-foreach @ignore-text }}}@ */
import org.codeblessing.alpstein.server.exampledata.membrumrelatum.MembrumRelatumExampleDataPopulator
/* @tt{{{ @end-ignore-text }}}@ */

/* @tt{{{
    @replace-value-by-value
        [ searchValue="ItemsExampleDataCreator" replaceByValue="GeneratedItemsExampleDataCreator" ]
}}}@ */
@Component
class ItemsExampleDataCreator(
/* @tt{{{ @end-replace-value-by-value }}}@ */
    /* @tt{{{
        @foreach [ iteratorExpression="model.itemsWithPrimaryKey" loopVariable="item" ]
        @replace-value-by-expression
            [ searchValue="SilvaOptionum" replaceByExpression="item.itemName.pascalCase" ]
            [ searchValue="silvaOptionum" replaceByExpression="item.itemName.camelCase" ]
    }}}@ */
    private val silvaOptionumExampleDataPopulator: SilvaOptionumExampleDataPopulator,
    /* @tt{{{ @end-replace-value-by-expression @end-foreach @ignore-text }}}@ */
    private val membrumRelatumExampleDataPopulator: MembrumRelatumExampleDataPopulator,
    /* @tt{{{ @end-ignore-text }}}@ */
): ExampleDataCreator {

    override fun createExampleData(dataContext: DataContext) {
        // the order matters: an item that references another one can only pick an existing
        // instance, so the referenced item has to create its example data first

        /* @tt{{{
            @foreach [ iteratorExpression="model.itemsWithPrimaryKey" loopVariable="item" ]
            @replace-value-by-expression
                [ searchValue="membrumRelatum" replaceByExpression="item.itemName.camelCase" ]
        }}}@ */
        membrumRelatumExampleDataPopulator.createExampleData(dataContext)
        /* @tt{{{ @end-replace-value-by-expression @end-foreach @ignore-text }}}@ */
        silvaOptionumExampleDataPopulator.createExampleData(dataContext)
        /* @tt{{{ @end-ignore-text }}}@ */
    }
}
