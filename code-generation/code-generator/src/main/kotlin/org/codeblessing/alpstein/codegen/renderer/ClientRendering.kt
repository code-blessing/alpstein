package org.codeblessing.alpstein.codegen.renderer

import org.codeblessing.alpstein.codegen.renderer.angular.*
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.isDirectory
import kotlin.io.path.writeText

object ClientRendering {

    fun renderClientFiles(pathToGeneratedAngularFiles: Path, uiModel: UiModel) {
        val worker = RenderingWorker(pathToGeneratedAngularFiles)
        worker.renderClientFiles(uiModel)
    }

    private data class RenderingWorker(
        val pathToGeneratedAngularFiles: Path,
    ) {

        fun renderClientFiles(uiModel: UiModel) {
            renderNavigation(uiModel.uiEntities)

            uiModel.uiItems.forEach { uiItemModel ->
                renderWTO(uiItemModel)
            }

            // Service, search/by-ids WTOs and the reference components only exist for an item
            // that can be addressed by a primary key.
            uiModel.uiItemsWithPrimaryKey.forEach { uiItemModel ->
                renderItemService(uiItemModel)
                renderItemReference(uiItemModel)
            }

            uiModel.uiEnums.forEach { uiEnumModel ->
                renderEnum(uiEnumModel)
            }

            uiModel.uiEntitiesViews.forEach { uiEntityView ->
                uiEntityView.formView.entityItems.forEach { entityItem ->
                    renderFormPart(entityItem)
                    renderTableComponent(entityItem)
                }
            }

            uiModel.uiEntities.forEach { uiEntityModel ->
                renderEntityBoard(uiEntityModel)
                renderEntityForm(uiEntityModel)
            }
        }

        private fun renderNavigation(uiEntities: List<UiEntityModel>) {
            val entityListRenderer: List<UiEntitiesRenderer> = listOf(
                TypescriptEntitiesRoutingListRenderer,
                TypescriptSideNavLinkListRenderer,
                TypescriptEntityViewRegistrationListRenderer,
            )

            entityListRenderer.forEach { renderer ->
                writeFile(
                    filePath = pathToGeneratedAngularFiles.resolve(renderer.filePath(uiEntities)),
                    content = renderer.renderTemplate(uiEntities),
                )
            }
        }

        private fun renderWTO(uiItemModel: UiItemModel) {
            renderAll(listOf(ItemWTOInterfaceRenderer), uiItemModel)
        }

        /**
         * The service that talks to the REST endpoints of this item, together with the transfer
         * objects of its search and by-ids calls. They live next to the WTOs, one level above the
         * UiEntity directories, because an item is not owned by a single UiEntity.
         */
        private fun renderItemService(uiItemModel: UiItemModel) {
            val itemRenderers: List<UiItemRenderer> = listOf(
                ItemServiceRenderer,
                ItemSearchCriteriaWtoRenderer,
                ItemSearchResultWtoRenderer,
                ItemByIdsCriteriaWtoRenderer,
                ItemByIdsResultWtoRenderer,
            )

            renderAll(itemRenderers, uiItemModel)
        }

        /**
         * The components with which an attribute of another item references this item: the
         * typeahead that searches it and the field and table that show the picked references by
         * their display attributes instead of the stored UUIDs. They are rendered for every item
         * with a primary key, as any such item may be referenced.
         */
        private fun renderItemReference(uiItemModel: UiItemModel) {
            val itemRenderers: List<UiItemRenderer> = listOf(
                ItemReferenceDisplayRenderer,
                ItemTypeaheadComponentHtmlRenderer,
                ItemTypeaheadComponentScssRenderer,
                ItemTypeaheadComponentTypescriptRenderer,
                ItemReferenceFieldComponentHtmlRenderer,
                ItemReferenceFieldComponentScssRenderer,
                ItemReferenceFieldComponentTypescriptRenderer,
                ItemReferenceTableComponentHtmlRenderer,
                ItemReferenceTableComponentScssRenderer,
                ItemReferenceTableComponentTypescriptRenderer,
                ItemReferenceTableRowRenderer,
            )

            renderAll(itemRenderers, uiItemModel)
        }

        private fun renderAll(renderers: List<UiItemRenderer>, uiItemModel: UiItemModel) {
            renderers.forEach { renderer ->
                writeFile(
                    filePath = pathToGeneratedAngularFiles.resolve(renderer.filePath(uiItemModel)),
                    content = renderer.renderTemplate(uiItemModel),
                )
            }
        }

        private fun renderEnum(uiEnumModel: UiEnumModel) {
            val enumRenderer: List<UiEnumRenderer> = listOf(
                EnumDefinitionTypescriptRenderer,
                EnumI18nComponentHtmlRenderer,
                EnumI18nComponentScssRenderer,
                EnumI18nComponentTypescriptRenderer,
                EnumI18nComponentSpecTypescriptRenderer,
                EnumSelectorComponentHtmlRenderer,
                EnumSelectorComponentScssRenderer,
                EnumSelectorComponentTypescriptRenderer,
                EnumListFormFieldTableComponentHtmlRenderer,
                EnumListFormFieldTableComponentTypescriptRenderer,
            )

            enumRenderer.forEach { renderer ->
                writeFile(
                    filePath = pathToGeneratedAngularFiles.resolve(renderer.filePath(uiEnumModel)),
                    content = renderer.renderTemplate(uiEnumModel),
                )
            }
        }

        private fun renderEntityBoard(uiEntityModel: UiEntityModel) {
            val entityRenderer: List<UiEntityRenderer> = listOf(
                EntityBoardComponentHtmlRenderer,
                EntityBoardComponentScssRenderer,
                EntityBoardComponentTypescriptRenderer,
                EntityRoutableEditComponentHtmlRenderer,
                EntityRoutableEditComponentScssRenderer,
                EntityRoutableEditComponentTypescriptRenderer,
                EntityConfirmDeleteDialogComponentHtmlRenderer,
                EntityConfirmDeleteDialogComponentScssRenderer,
                EntityConfirmDeleteDialogComponentTypescriptRenderer,
                EntityResultComponentHtmlRenderer,
                EntityResultComponentScssRenderer,
                EntityResultComponentTypescriptRenderer,
                EntitySearchComponentHtmlRenderer,
                EntitySearchComponentScssRenderer,
                EntitySearchComponentTypescriptRenderer,
                EntityFirstEntryGuardRenderer,
                EntityRoutingRenderer,
            )

            entityRenderer.forEach { renderer ->
                writeFile(
                    filePath = pathToGeneratedAngularFiles.resolve(renderer.filePath(uiEntityModel)),
                    content = renderer.renderTemplate(uiEntityModel),
                )
            }
        }

        private fun renderEntityForm(uiEntityModel: UiEntityModel) {
            val entityRenderer: List<UiEntityRenderer> = listOf(
                EntityFormComponentHtmlRenderer,
                EntityFormComponentScssRenderer,
                EntityFormComponentTypescriptRenderer,
            )

            entityRenderer.forEach { renderer ->
                writeFile(
                    filePath = pathToGeneratedAngularFiles.resolve(renderer.filePath(uiEntityModel)),
                    content = renderer.renderTemplate(uiEntityModel),
                )
            }
        }

        private fun renderFormPart(
            formViewItemModel: UiEntityFormViewItemModel,
        ) {
            val uiEntityItemRenderer: List<UiEntityItemRenderer> = listOf(
                EntityItemFormPartComponentHtmlRenderer,
                EntityItemFormPartComponentScssRenderer,
                EntityItemFormPartComponentTypescriptRenderer,
                EntityItemFormPartFieldNameRenderer,
                EntityItemFormPartValidationServiceRenderer,
                EntityItemFormPartInitialValueServiceRenderer,
                EntityItemFormPartServiceRenderer,
                EntityItemFormPartGroupRenderer,
            )

            uiEntityItemRenderer.forEach { renderer ->
                writeFile(
                    filePath = pathToGeneratedAngularFiles.resolve(renderer.filePath(model = formViewItemModel)),
                    content = renderer.renderTemplate(model = formViewItemModel),
                )
            }

        }

        private fun renderTableComponent(
            formViewItemModel: UiEntityFormViewItemModel,
        ) {
            val uiEntityItemRenderer: List<UiEntityItemRenderer> = listOf(
                EntityItemTableComponentHtmlRenderer,
                EntityItemTableComponentScssRenderer,
                EntityItemTableComponentTypescriptRenderer,
                EntityItemTableRowComponentTypescriptRenderer,
            )


            uiEntityItemRenderer.forEach { renderer ->
                writeFile(
                    filePath = pathToGeneratedAngularFiles.resolve(renderer.filePath(model = formViewItemModel)),
                    content = renderer.renderTemplate(model = formViewItemModel),
                )
            }

        }

        private fun writeFile(filePath: Path, content: String) {
            require(!filePath.isDirectory()) { "$filePath is a directory" }
            // createDirectories() is not reliably a no-op for an existing directory on every
            // file system, and several files share one directory, so check first.
            val parent = filePath.parent
            if (!parent.isDirectory()) {
                parent.createDirectories()
            }
            filePath.writeText(content)
        }
    }
}
