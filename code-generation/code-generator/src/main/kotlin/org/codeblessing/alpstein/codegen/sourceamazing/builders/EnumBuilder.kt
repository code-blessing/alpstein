package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.Builder
import org.codeblessing.sourceamazing.builder.api.annotations.BuilderMethod
import org.codeblessing.sourceamazing.builder.api.annotations.ExpectedClazzModelFromSuperiorBuilder
import org.codeblessing.sourceamazing.builder.api.annotations.SetAsValue
import org.codeblessing.alpstein.model.builders.EnumDsl
import org.codeblessing.alpstein.model.schema.EnumType

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = EnumType::class, alias = "enum")
interface EnumBuilder: EnumDsl {

    @BuilderMethod
    override fun enumValue(
        @SetAsValue(alias = "enum", clazzProperty = "enumValues")
        name: String
    )
}
