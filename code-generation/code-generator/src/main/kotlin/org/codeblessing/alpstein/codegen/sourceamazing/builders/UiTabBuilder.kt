package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.*
import org.codeblessing.alpstein.model.builders.UiBlockEditorDsl
import org.codeblessing.alpstein.model.builders.UiTabDsl
import org.codeblessing.alpstein.model.schema.UiEntityEditorColumn
import org.codeblessing.alpstein.model.schema.UiEntityEditorTab

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = UiEntityEditorTab::class, alias = "uiTab")
interface UiTabBuilder: UiTabDsl {

    @BuilderMethod
    @NewClazzModel(clazz = UiEntityEditorColumn::class, alias = "uiColumn")
    @SetClazzModelOfAlias(alias = "uiTab", clazzProperty = "columns", referencedAlias = "uiColumn")
    fun columnInternal(
        @InjectBuilder builder: UiBlockBuilder.() -> Unit
    )

    override fun column(builder: UiBlockEditorDsl.() -> Unit) {
        columnInternal(builder)
    }
}
