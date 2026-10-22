package org.codeblessing.alpstein.server.exampledata.framework.datafaker

import net.datafaker.Faker
import org.springframework.stereotype.Service
import java.util.*


@Service
class DataFakerDataContextFactoryService {

    fun createContext(): DataFakerDataContext {
        val random = Random(42)
        val faker = Faker(Locale.ENGLISH, random)
        return DataFakerDataContext(faker)
    }
}
