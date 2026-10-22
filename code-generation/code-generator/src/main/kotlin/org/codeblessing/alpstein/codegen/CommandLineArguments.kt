package org.codeblessing.alpstein.codegen

import org.codeblessing.alpstein.codegen.CommandLineArgument.ANGULAR
import org.codeblessing.alpstein.codegen.CommandLineArgument.DATABASE_MIGRATION
import org.codeblessing.alpstein.codegen.CommandLineArgument.DOCUMENTATION
import org.codeblessing.alpstein.codegen.CommandLineArgument.EXAMPLE_DATA
import org.codeblessing.alpstein.codegen.CommandLineArgument.PERSISTENCE
import org.codeblessing.alpstein.codegen.CommandLineArgument.REST
import org.codeblessing.alpstein.codegen.CommandLineArgument.SERVICE
import java.nio.file.Path
import java.nio.file.Paths

enum class CommandLineArgument(val description: String) {
    ANGULAR("Path to angular generated sources"),
    REST("Path to rest generated sources"),
    SERVICE("Path to service generated sources"),
    PERSISTENCE("Path to persistence generated sources"),
    EXAMPLE_DATA("Path to example_data generated sources"),
    DATABASE_MIGRATION("Path to generated database migrations"),
    DOCUMENTATION("Path to generated documentation sources"),
    ;
}

data class CommandLineArguments(
    private val arguments: Map<CommandLineArgument, String>,
) {
    val directoryForAngularGeneratedSource: Path = Paths.get(requireNotNull(arguments[ANGULAR]))
    val directoryForRestGeneratedSource: Path = Paths.get(requireNotNull(arguments[REST]))
    val directoryForServiceGeneratedSource: Path = Paths.get(requireNotNull(arguments[SERVICE]))
    val directoryForPersistenceGeneratedSource: Path = Paths.get(requireNotNull(arguments[PERSISTENCE]))
    val directoryForExampleDataGeneratedSource: Path = Paths.get(requireNotNull(arguments[EXAMPLE_DATA]))
    val directoryForDatabaseMigrationGeneratedSource: Path = Paths.get(requireNotNull(arguments[DATABASE_MIGRATION]))
    val directoryForDocumentationGeneratedSource: Path = Paths.get(requireNotNull(arguments[DOCUMENTATION]))
}

fun createCommandLineArguments(args: Array<String>): CommandLineArguments? {
    if (args.size != CommandLineArgument.entries.size) {
        val commandLineArgumentDescription = CommandLineArgument.entries.joinToString(separator = " ", prefix = "<", postfix = ">") { it.description }
        println("Wrong number of arguments! Expected arguments: $commandLineArgumentDescription")
        return null
    }
    return CommandLineArguments(args
        .mapIndexed { index, argument -> Pair(CommandLineArgument.entries[index], argument) }
        .toMap())

}
