package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.*
import org.codeblessing.alpstein.model.builders.UiBlockEditorDsl
import org.codeblessing.alpstein.model.builders.UiEditorForNestedItemDsl
import org.codeblessing.alpstein.model.schema.UiEntityEditorColumn
import org.codeblessing.alpstein.model.schema.UiItem

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = UiItem::class, alias = "uiItem")
interface UiItemDefaultNestedItemEditorBuilder: UiEditorForNestedItemDsl {

    @BuilderMethod
    @NewClazzModel(clazz = UiEntityEditorColumn::class, alias = "uiColumn")
    @SetClazzModelOfAlias(alias = "uiItem", clazzProperty = "defaultNestedItemEditor", referencedAlias = "uiColumn")
    fun columnInternal(
        @InjectBuilder builder: UiBlockBuilder.() -> Unit
    )

    override fun column(builder: UiBlockEditorDsl.() -> Unit) {
        columnInternal(builder)
    }
}
