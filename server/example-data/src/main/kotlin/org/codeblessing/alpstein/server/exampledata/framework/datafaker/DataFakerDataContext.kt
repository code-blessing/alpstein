package org.codeblessing.alpstein.server.exampledata.framework.datafaker

import net.datafaker.Faker
import org.codeblessing.alpstein.server.exampledata.DataContext

data class DataFakerDataContext(
    val faker: Faker
): DataContext
