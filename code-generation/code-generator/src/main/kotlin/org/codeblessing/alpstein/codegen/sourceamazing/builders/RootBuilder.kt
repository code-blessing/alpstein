package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.Builder
import org.codeblessing.sourceamazing.builder.api.annotations.BuilderMethod
import org.codeblessing.sourceamazing.builder.api.annotations.ExpectedClazzModelFromSuperiorBuilder
import org.codeblessing.sourceamazing.builder.api.annotations.InjectBuilder
import org.codeblessing.sourceamazing.builder.api.annotations.RedeclareAliasForNestedBuilder
import org.codeblessing.alpstein.model.builders.RootDsl
import org.codeblessing.alpstein.model.builders.SchemaDsl
import org.codeblessing.alpstein.model.schema.SchemaData

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = SchemaData::class, alias = "root")
interface RootBuilder: RootDsl {

    @BuilderMethod
    @RedeclareAliasForNestedBuilder(alias = "root", newAlias = "schema")
    fun createSchemaInternal(
        @InjectBuilder builder: SchemaBuilder.() -> Unit
    )



    override fun schema(
        builder: SchemaDsl.() -> Unit
    ) {
        createSchemaInternal(builder)
    }
}
