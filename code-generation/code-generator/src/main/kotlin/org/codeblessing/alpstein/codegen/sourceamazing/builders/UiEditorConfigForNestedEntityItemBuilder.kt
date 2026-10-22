package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.*
import org.codeblessing.alpstein.model.builders.UiBlockEditorDsl
import org.codeblessing.alpstein.model.builders.UiEditorForNestedItemDsl
import org.codeblessing.alpstein.model.schema.UiEntityEditorColumn
import org.codeblessing.alpstein.model.schema.UiEntityEditorEntityNestedItemConfiguration

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = UiEntityEditorEntityNestedItemConfiguration::class, alias = "nestedItemConfiguration")
interface UiEditorConfigForNestedEntityItemBuilder: UiEditorForNestedItemDsl {

    @BuilderMethod
    @NewClazzModel(clazz = UiEntityEditorColumn::class, alias = "uiColumn")
    @SetClazzModelOfAlias(alias = "nestedItemConfiguration", clazzProperty = "noTab", referencedAlias = "uiColumn")
    fun columnInternal(
        @InjectBuilder builder: UiBlockBuilder.() -> Unit
    )

    override fun column(builder: UiBlockEditorDsl.() -> Unit) {
        columnInternal(builder)
    }
}
