package org.codeblessing.alpstein.codegen.renderer.model

import org.codeblessing.alpstein.codegen.renderer.model.be.BeModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiModel

data class SchemaModel(
    val uiModel: UiModel,
    val beModel: BeModel,
    val dbModel: DbModel,
)
