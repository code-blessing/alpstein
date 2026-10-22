package org.codeblessing.alpstein.server.exampledata

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.DataFakerDataContextFactoryService

/**
 * Orchestrates the creation of example data: one populator per item with a primary key,
 * each of them filling the repository of that item.
 */
@Component
class ExampleDataCreatorsRunner(
    private val dataContextFactoryService: DataFakerDataContextFactoryService,
    private val itemsExampleDataCreator: ItemsExampleDataCreator,
    private val generatedItemsExampleDataCreator: GeneratedItemsExampleDataCreator,
    @param:Value($$"${org.codeblessing.alpstein.exampledata.createRandomData.forGeneratedItems:false}")
    private val createDataForGeneratedItems: Boolean,
) {

    fun createExampleData() {
        val dataContext = dataContextFactoryService.createContext()
        itemsExampleDataCreator.createExampleData(dataContext)
        if (createDataForGeneratedItems) {
            generatedItemsExampleDataCreator.createExampleData(dataContext)
        }
    }
}
