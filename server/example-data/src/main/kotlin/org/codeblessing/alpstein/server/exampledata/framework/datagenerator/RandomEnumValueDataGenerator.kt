package org.codeblessing.alpstein.server.exampledata.framework.datagenerator

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
import kotlin.reflect.KClass

@Component
class RandomEnumValueDataGenerator {
    fun <T> generateData(dataContext: DataContext, enumClass: KClass<T>): T where T : Enum<T> {
        return FakerHelper.oneRandomOf(dataContext, enumClass.java.enumConstants)
    }

    fun <T> generateDataList(dataContext: DataContext, enumClass: KClass<T>, size: Int): List<T> where T : Enum<T> =
        List( size = size) { generateData(dataContext, enumClass) }
}
