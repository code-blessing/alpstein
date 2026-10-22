package org.codeblessing.alpstein.server.exampledata.framework.datagenerator

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper

@Component
class CountryNameStringDataGenerator: DataGenerator<String> {
    override fun generateData(dataContext: DataContext): String {
        return FakerHelper.countryName(dataContext)
    }
}
