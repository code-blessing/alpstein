package org.codeblessing.alpstein.codegen.renderer

import org.codeblessing.alpstein.codegen.renderer.be.BeEnumRenderer
import org.codeblessing.alpstein.codegen.renderer.be.BeEnumsRenderer
import org.codeblessing.alpstein.codegen.renderer.be.BeItemRenderer
import org.codeblessing.alpstein.codegen.renderer.be.BeRenderer
import org.codeblessing.alpstein.codegen.renderer.be.DatabaseEnumsRenderer
import org.codeblessing.alpstein.codegen.renderer.be.EnumBORenderer
import org.codeblessing.alpstein.codegen.renderer.be.EnumExampleDataCreatorRenderer
import org.codeblessing.alpstein.codegen.renderer.be.EnumMapperRenderer
import org.codeblessing.alpstein.codegen.renderer.be.EnumWTORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemBORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemByIdsCriteriaBORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemByIdsCriteriaMapperRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemByIdsCriteriaWTORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemByIdsResultWTORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemControllerRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemExampleDataCreatorRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemExampleDataFetcherRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemExampleDataPopulatorRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemInMemoryRepositoryRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemMapperRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemPostgresSqlRepositoryRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemRepositoryRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemSearchCriteriaBORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemSearchCriteriaMapperRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemSearchCriteriaWTORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemSearchResultWTORenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemsExampleDataCreatorRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemServiceRenderer
import org.codeblessing.alpstein.codegen.renderer.be.ItemWTORenderer
import org.codeblessing.alpstein.codegen.renderer.model.be.BeEnumModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeModel
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.isDirectory
import kotlin.io.path.writeText

object BackendRendering {

    fun renderBackendFiles(
        pathToGeneratedBackendRestFiles: Path,
        pathToGeneratedBackendServiceFiles: Path,
        pathToGeneratedBackendPersistenceFiles: Path,
        pathToGeneratedBackendExampleDataFiles: Path,
        beModel: BeModel,
    ) {
        val worker = RenderingWorker(
            pathToGeneratedBackendRestFiles = pathToGeneratedBackendRestFiles,
            pathToGeneratedBackendServiceFiles = pathToGeneratedBackendServiceFiles,
            pathToGeneratedBackendPersistenceFiles = pathToGeneratedBackendPersistenceFiles,
            pathToGeneratedBackendExampleDataFiles = pathToGeneratedBackendExampleDataFiles,
        )
        worker.renderBackendFiles(beModel)
    }

    private data class RenderingWorker(
        val pathToGeneratedBackendRestFiles: Path,
        val pathToGeneratedBackendServiceFiles: Path,
        val pathToGeneratedBackendPersistenceFiles: Path,
        val pathToGeneratedBackendExampleDataFiles: Path,

        ) {

        fun renderBackendFiles(beModel: BeModel) {
            beModel.items.forEach { beItemModel ->
                renderItem(beItemModel)
            }

            // Controller, service, persistence and the example data initializer only exist for
            // an item that can be addressed by a primary key.
            beModel.itemsWithPrimaryKey.forEach { beItemModel ->
                renderItemWithPrimaryKey(beItemModel)
            }

            renderAllItem(beModel)

            beModel.enums.forEach { beEnumModel ->
                renderEnum(beEnumModel)
            }

            renderAllEnums(beModel.enums)
        }

        /**
         * The files that describe all items with a primary key at once instead of one of them,
         * currently only the example data creator that runs every generated populator.
         */
        private fun renderAllItem(beModel: BeModel) {
            val itemsRenderers: List<Pair<BeRenderer, Path>> = listOf(
                ItemsExampleDataCreatorRenderer to pathToGeneratedBackendExampleDataFiles,
            )

            itemsRenderers.forEach { (renderer, basePath) ->
                writeFile(
                    filePath = basePath.resolve(renderer.filePath(beModel)),
                    content = renderer.renderTemplate(beModel),
                )
            }
        }

        /**
         * The files that describe all enum types at once instead of one of them, currently only
         * the table telling the PostgreSQL repositories how an enum value is stored.
         */
        private fun renderAllEnums(beEnumModels: List<BeEnumModel>) {
            val enumsRenderers: List<Pair<BeEnumsRenderer, Path>> = listOf(
                DatabaseEnumsRenderer to pathToGeneratedBackendPersistenceFiles,
            )

            enumsRenderers.forEach { (renderer, basePath) ->
                writeFile(
                    filePath = basePath.resolve(renderer.filePath(beEnumModels)),
                    content = renderer.renderTemplate(beEnumModels),
                )
            }
        }

        private fun renderItem(beItemModel: BeItemModel) {
            val itemRenderers: List<Pair<BeItemRenderer, Path>> = listOf(
                ItemExampleDataCreatorRenderer to pathToGeneratedBackendExampleDataFiles,
                ItemBORenderer to pathToGeneratedBackendServiceFiles,
                ItemMapperRenderer to pathToGeneratedBackendRestFiles,
                ItemWTORenderer to pathToGeneratedBackendRestFiles,
            )

            renderAll(itemRenderers, beItemModel)
        }

        private fun renderItemWithPrimaryKey(beItemModel: BeItemModel) {
            val itemRenderers: List<Pair<BeItemRenderer, Path>> = listOf(
                ItemExampleDataPopulatorRenderer to pathToGeneratedBackendExampleDataFiles,
                ItemRepositoryRenderer to pathToGeneratedBackendServiceFiles,
                ItemServiceRenderer to pathToGeneratedBackendServiceFiles,
                ItemInMemoryRepositoryRenderer to pathToGeneratedBackendPersistenceFiles,
                ItemPostgresSqlRepositoryRenderer to pathToGeneratedBackendPersistenceFiles,
                ItemControllerRenderer to pathToGeneratedBackendRestFiles,
                ItemSearchCriteriaBORenderer to pathToGeneratedBackendServiceFiles,
                ItemSearchCriteriaWTORenderer to pathToGeneratedBackendRestFiles,
                ItemSearchResultWTORenderer to pathToGeneratedBackendRestFiles,
                ItemSearchCriteriaMapperRenderer to pathToGeneratedBackendRestFiles,
                ItemByIdsCriteriaBORenderer to pathToGeneratedBackendServiceFiles,
                ItemByIdsCriteriaWTORenderer to pathToGeneratedBackendRestFiles,
                ItemByIdsResultWTORenderer to pathToGeneratedBackendRestFiles,
                ItemByIdsCriteriaMapperRenderer to pathToGeneratedBackendRestFiles,
                ItemExampleDataFetcherRenderer to pathToGeneratedBackendExampleDataFiles,
            )

            renderAll(itemRenderers, beItemModel)
        }

        private fun renderAll(renderers: List<Pair<BeItemRenderer, Path>>, beItemModel: BeItemModel) {
            renderers.forEach { (renderer, basePath) ->
                writeFile(
                    filePath = basePath.resolve(renderer.filePath(beItemModel)),
                    content = renderer.renderTemplate(beItemModel),
                )
            }
        }

        private fun renderEnum(beEnumModel: BeEnumModel) {
            val enumRenderer: List<Pair<BeEnumRenderer, Path>> = listOf(
                EnumExampleDataCreatorRenderer to pathToGeneratedBackendExampleDataFiles,
                EnumBORenderer to pathToGeneratedBackendServiceFiles,
                EnumMapperRenderer to pathToGeneratedBackendRestFiles,
                EnumWTORenderer to pathToGeneratedBackendRestFiles,
            )

            enumRenderer.forEach { (renderer, basePath) ->
                writeFile(
                    filePath = basePath.resolve(renderer.filePath(beEnumModel)),
                    content = renderer.renderTemplate(beEnumModel),
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
