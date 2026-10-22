package org.codeblessing.alpstein.codegen.renderer.model.ui

import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewModel

/**
 * Represents one entity ("author" directory)
 */
data class UiEntityViewsModel(
    val uiEntity: UiEntityModel,
    val formView: UiEntityFormViewModel,
    // TODO hier eine Liste aller Formulare,
    // TODO hier eine Liste aller (nested) Komponenten, jeweils mit dem Block-Konzept
)
