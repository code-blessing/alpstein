package org.codeblessing.alpstein.server

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.ExampleDataCreatorsRunner

/**
 * Triggers the creation of example data on application startup and persists it.
 *
 * Only active when `org.codeblessing.alpstein.exampledata.createRandomData=true`; without that property no
 * example data is created at all.
 */
@Component
@ConditionalOnProperty(name = ["org.codeblessing.alpstein.exampledata.createRandomData"], havingValue = "true")
class ExampleDataInitializer(
    private val exampleDataCreatorsRunner: ExampleDataCreatorsRunner,
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String) {
        logger.info("Create and persist example data...")
        exampleDataCreatorsRunner.createExampleData()
        logger.info("Create and persist example data. Done")
    }
}
