package org.codeblessing.alpstein.server.exampledata.framework.datagenerator

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
import java.util.UUID

@Component
class RandomUuidDataGenerator: DataGenerator<UUID> {
    override fun generateData(dataContext: DataContext): UUID {
        return FakerHelper.randomUuid(dataContext)
    }
}
