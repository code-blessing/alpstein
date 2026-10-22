package org.codeblessing.alpstein.server.exampledata.framework.datagenerator

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper

@Component
class RandomBooleanDataGenerator(): DataGenerator<Boolean> {
    override fun generateData(dataContext: DataContext): Boolean {
        return FakerHelper.randomBoolean(dataContext)
    }
}
