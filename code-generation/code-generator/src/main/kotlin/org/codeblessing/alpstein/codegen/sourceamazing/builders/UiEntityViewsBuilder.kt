package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.*
import org.codeblessing.alpstein.model.builders.UiEditorDsl
import org.codeblessing.alpstein.model.builders.UiSearchResultDsl
import org.codeblessing.alpstein.model.builders.UiViewsDsl
import org.codeblessing.alpstein.model.schema.UiEntity
import org.codeblessing.alpstein.model.schema.UiEntityEditorView
import org.codeblessing.alpstein.model.schema.UiEntitySearchResultView

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = UiEntity::class, alias = "uiEntity")
interface UiEntityViewsBuilder: UiViewsDsl {

    @BuilderMethod
    @NewClazzModel(clazz = UiEntityEditorView::class, alias = "uiEditor")
    @SetClazzModelOfAlias(alias = "uiEntity", clazzProperty = "editorView", referencedAlias = "uiEditor")
    fun editorInternal(
        @InjectBuilder builder: UiEditorViewBuilder.() -> Unit
    )

    override fun editor(builder: UiEditorDsl.() -> Unit) {
        editorInternal(builder)
    }

    @BuilderMethod
    @NewClazzModel(clazz = UiEntitySearchResultView::class, alias = "uiSearchResult")
    @SetClazzModelOfAlias(alias = "uiEntity", clazzProperty = "searchResultView", referencedAlias = "uiSearchResult")
    fun searchResultInternal(
        @InjectBuilder builder: UiSearchResultViewBuilder.() -> Unit
    )

    override fun searchResult(builder: UiSearchResultDsl.() -> Unit) {
        searchResultInternal(builder)
    }
}
