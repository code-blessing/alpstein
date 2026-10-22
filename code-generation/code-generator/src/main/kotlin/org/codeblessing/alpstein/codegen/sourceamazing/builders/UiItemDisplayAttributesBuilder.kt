package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.Builder
import org.codeblessing.sourceamazing.builder.api.annotations.BuilderMethod
import org.codeblessing.sourceamazing.builder.api.annotations.ExpectedClazzModelFromSuperiorBuilder
import org.codeblessing.sourceamazing.builder.api.annotations.SetAsValue
import org.codeblessing.alpstein.model.builders.UiDisplayAttributesDsl
import org.codeblessing.alpstein.model.schema.UiItem

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = UiItem::class, alias = "uiItem")
interface UiItemDisplayAttributesBuilder: UiDisplayAttributesDsl {

    @BuilderMethod
    override fun attribute(
        @SetAsValue(alias = "uiItem", clazzProperty = "displayAttributeNames")
        attributeName: String,
    )
}
