package org.codeblessing.alpstein.codegen.renderercreator

import org.codeblessing.tavnit.FileSearchLocation
import org.codeblessing.tavnit.TemplateRendererConfiguration
import org.codeblessing.tavnit.TemplatingConfiguration
import org.codeblessing.tavnit.TavnitApi
import java.nio.file.Path
import kotlin.system.exitProcess

private const val PRINT_CREATED_TEMPLATE_RENDERERS = true

fun main(args: Array<String>) {
    val cliArgs = createCommandLineArguments(args) ?: exitProcess(0)

    println("Generating template renderer with tavnit")
    val configs = gatherTemplatingConfigurations(cliArgs)
    val createdTemplateRenderers = TavnitApi.runTavnit(configs)

    if(PRINT_CREATED_TEMPLATE_RENDERERS) {
        printCreatedTemplateRenders(createdTemplateRenderers)
    }
}

private fun printCreatedTemplateRenders(createdTemplateRenderers: Map<TemplatingConfiguration, List<Path>>) {
    for ((configuration, paths) in createdTemplateRenderers) {
        println("Configuration: $configuration created paths:")
        for (path in paths) {
            println("- TemplateRenderer $path")
        }
    }
}

private fun gatherTemplatingConfigurations(
    cliArgs: CommandLineArguments,
): List<TemplatingConfiguration> {
    val rootDirectoriesToSearch = listOf(
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForAngularSource,
            filenameMatchingPattern = Regex(".*\\.html"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForAngularSource,
            filenameMatchingPattern = Regex(".*\\.scss"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForAngularSource,
            filenameMatchingPattern = Regex(".*\\.ts"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForRestSource,
            filenameMatchingPattern = Regex(".*\\.kt"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForServiceSource,
            filenameMatchingPattern = Regex(".*\\.kt"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForPersistenceSource,
            filenameMatchingPattern = Regex(".*\\.kt"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForExampleDataSource,
            filenameMatchingPattern = Regex(".*\\.kt"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForDatabaseMigration,
            filenameMatchingPattern = Regex(".*\\.sql"),
        ),
        FileSearchLocation(
            rootDirectoryToSearch = cliArgs.directoryForDocumentation,
            filenameMatchingPattern = Regex(".*\\.md"),
        ),
    )

    val templateRendererConfiguration = TemplateRendererConfiguration(
        templateRendererTargetSourceBasePath = cliArgs.generatedTemplateRenderersBaseDir,
    )
    val configuration = TemplatingConfiguration(
        fileSearchLocations = rootDirectoriesToSearch,
        templateRendererConfiguration = templateRendererConfiguration,
    )
    return listOf(configuration)
}
