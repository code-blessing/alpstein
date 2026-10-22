package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.*
import org.codeblessing.alpstein.model.builders.UiEditorDsl
import org.codeblessing.alpstein.model.builders.UiEditorForMainItemDsl
import org.codeblessing.alpstein.model.builders.UiEditorForNestedItemDsl
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.UiEntityEditorRootItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorEntityNestedItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorView

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = UiEntityEditorView::class, alias = "uiEditor")
interface UiEditorViewBuilder: UiEditorDsl {

    @BuilderMethod
    @NewClazzModel(clazz = UiEntityEditorRootItemConfiguration::class, alias = "mainItemConfiguration")
    @SetClazzModelOfAlias(alias = "uiEditor", clazzProperty = "itemConfiguration", referencedAlias = "mainItemConfiguration")
    fun configureEditorForMainItemInternal(
        @InjectBuilder builder: UiEditorConfigForMainEntityItemBuilder.() -> Unit
    )

    override fun configureEditorForMainItem(builder: UiEditorForMainItemDsl.() -> Unit) {
        configureEditorForMainItemInternal(builder)
    }

    @BuilderMethod
    @NewClazzModel(clazz = UiEntityEditorEntityNestedItemConfiguration::class, alias = "nestedItemConfiguration")
    @SetClazzModelOfAlias(alias = "uiEditor", clazzProperty = "itemConfiguration", referencedAlias = "nestedItemConfiguration")
    fun configureEditorForNestedItemInternal(
        @SetAsValue(alias = "nestedItemConfiguration", clazzProperty = "itemId")
        itemId: ItemId,
        @InjectBuilder builder: UiEditorConfigForNestedEntityItemBuilder.() -> Unit
    )

    override fun configureEditorForNestedItem(itemId: ItemId, builder: UiEditorForNestedItemDsl.() -> Unit) {
        configureEditorForNestedItemInternal(itemId, builder)
    }
}
