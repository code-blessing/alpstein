package org.codeblessing.alpstein.codegen

import org.codeblessing.alpstein.codegen.renderer.BackendRendering
import org.codeblessing.alpstein.codegen.renderer.ClientRendering
import org.codeblessing.alpstein.codegen.renderer.DatabaseRendering
import org.codeblessing.alpstein.codegen.renderer.DocumentationRendering
import org.codeblessing.alpstein.codegen.renderer.converter.RendererModelConverter
import org.codeblessing.alpstein.codegen.renderer.model.SchemaModel
import org.codeblessing.alpstein.model.schema.SchemaData
import org.codeblessing.alpstein.codegen.sourceamazing.DefinitionDataCollection
import org.codeblessing.alpstein.codegen.validation.SchemaDataValidator
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val cliArgs = createCommandLineArguments(args) ?: exitProcess(0)

    val schemaData = fetchSchemaData()
    validateSchemaData(schemaData)
    val schemaModel = convertToSchemaModel(schemaData)
    ClientRendering.renderClientFiles(
        pathToGeneratedAngularFiles = cliArgs.directoryForAngularGeneratedSource,
        uiModel = schemaModel.uiModel
    )
    BackendRendering.renderBackendFiles(
        pathToGeneratedBackendRestFiles = cliArgs.directoryForRestGeneratedSource,
        pathToGeneratedBackendServiceFiles = cliArgs.directoryForServiceGeneratedSource,
        pathToGeneratedBackendPersistenceFiles = cliArgs.directoryForPersistenceGeneratedSource,
        pathToGeneratedBackendExampleDataFiles = cliArgs.directoryForExampleDataGeneratedSource,
        beModel = schemaModel.beModel,
    )

    DatabaseRendering.renderDatabaseFiles(
        pathToGeneratedDatabaseMigrationScripts = cliArgs.directoryForDatabaseMigrationGeneratedSource,
        dbModel = schemaModel.dbModel,
    )

    DocumentationRendering.renderDocumentationFiles(
        pathToGeneratedDocumentation = cliArgs.directoryForDocumentationGeneratedSource,
        beModel = schemaModel.beModel,
    )

}

internal fun fetchSchemaData(): SchemaData = DefinitionDataCollection.collectSchemaData()

internal fun validateSchemaData(schemaData: SchemaData) = SchemaDataValidator().validate(schemaData)

internal fun convertToSchemaModel(schemaData: SchemaData): SchemaModel = RendererModelConverter.convertSchemaDataToSchemaModel(schemaData)
