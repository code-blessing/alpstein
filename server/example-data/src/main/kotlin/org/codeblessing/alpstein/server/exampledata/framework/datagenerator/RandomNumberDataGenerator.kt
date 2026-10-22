package org.codeblessing.alpstein.server.exampledata.framework.datagenerator

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper

@Component
class RandomNumberDataGenerator(): DataGenerator<Int> {
    override fun generateData(dataContext: DataContext): Int {
        return FakerHelper.randomInt(dataContext)
    }
}
