package org.codeblessing.alpstein.codegen.sourceamazing

import org.codeblessing.sourceamazing.builder.api.BuilderApi
import org.codeblessing.sourceamazing.schema.api.SchemaApi
import org.codeblessing.alpstein.model.EssentialModelData.collectData
import org.codeblessing.alpstein.model.schema.SchemaData
import org.codeblessing.alpstein.codegen.sourceamazing.builders.RootBuilder

object DefinitionDataCollection {

    fun collectSchemaData(): SchemaData {
        return SchemaApi.withSchema(SchemaData::class) { schemaContext ->
            BuilderApi.withBuilder(
                schemaContext = schemaContext,
                builderClass = RootBuilder::class,
            ) { builder ->
                builder.collectData()
            }
        }
    }
}
