package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.Builder
import org.codeblessing.sourceamazing.builder.api.annotations.BuilderMethod
import org.codeblessing.sourceamazing.builder.api.annotations.ExpectedClazzModelFromSuperiorBuilder
import org.codeblessing.sourceamazing.builder.api.annotations.SetAsValue
import org.codeblessing.alpstein.model.builders.UiSearchResultDsl
import org.codeblessing.alpstein.model.schema.UiEntitySearchResultView

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = UiEntitySearchResultView::class, alias = "uiSearchResult")
interface UiSearchResultViewBuilder: UiSearchResultDsl {

    @BuilderMethod
    override fun attribute(
        @SetAsValue(alias = "uiSearchResult", clazzProperty = "attributeNames")
        attributeName: String,
    )
}
