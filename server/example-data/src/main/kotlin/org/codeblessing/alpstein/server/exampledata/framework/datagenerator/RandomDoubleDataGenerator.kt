package org.codeblessing.alpstein.server.exampledata.framework.datagenerator

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper

@Component
class RandomDoubleDataGenerator(): DataGenerator<Double> {
    override fun generateData(dataContext: DataContext): Double {
        return FakerHelper.randomDouble(dataContext)
    }
}
