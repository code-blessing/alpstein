package org.codeblessing.alpstein.codegen.renderer

import org.codeblessing.alpstein.codegen.renderer.db.DbEnumRenderer
import org.codeblessing.alpstein.codegen.renderer.db.DbItemRenderer
import org.codeblessing.alpstein.codegen.renderer.db.EnumTypeSchemaRenderer
import org.codeblessing.alpstein.codegen.renderer.db.ItemTableSchemaRenderer
import org.codeblessing.alpstein.codegen.renderer.model.db.DbEnumModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbTableModel
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.isDirectory
import kotlin.io.path.writeText

object DatabaseRendering {

    fun renderDatabaseFiles(pathToGeneratedDatabaseMigrationScripts: Path, dbModel: DbModel) {
        val worker = RenderingWorker(
            pathToGeneratedDatabaseMigrationScripts = pathToGeneratedDatabaseMigrationScripts,
        )
        worker.renderDatabaseFiles(dbModel)

    }

    private data class RenderingWorker(
        val pathToGeneratedDatabaseMigrationScripts: Path,

        ) {

        fun renderDatabaseFiles(dbModel: DbModel) {
            // The SQL enum types are created before the tables using them: a repeatable Flyway
            // migration runs in the alphabetical order of its description, and 'enum_' < 'schema_'.
            dbModel.enumsWithEnumType.forEach { dbEnumModel ->
                renderEnumType(dbEnumModel)
            }

            dbModel.tables.forEach { dbTableModel ->
                renderItemWithPrimaryKey(dbTableModel)
            }
        }

        private fun renderEnumType(dbEnumModel: DbEnumModel) {
            val enumRenderers: List<Pair<DbEnumRenderer, Path>> = listOf(
                EnumTypeSchemaRenderer to pathToGeneratedDatabaseMigrationScripts,
            )

            enumRenderers.forEach { (renderer, basePath) ->
                writeFile(
                    filePath = basePath.resolve(renderer.filePath(dbEnumModel)),
                    content = renderer.renderTemplate(dbEnumModel),
                )
            }
        }

        private fun renderItemWithPrimaryKey(dbTableModel: DbTableModel) {
            val itemRenderers: List<Pair<DbItemRenderer, Path>> = listOf(
                ItemTableSchemaRenderer to pathToGeneratedDatabaseMigrationScripts,
            )

            renderAll(itemRenderers, dbTableModel)
        }

        private fun renderAll(renderers: List<Pair<DbItemRenderer, Path>>, dbTableModel: DbTableModel) {
            renderers.forEach { (renderer, basePath) ->
                writeFile(
                    filePath = basePath.resolve(renderer.filePath(dbTableModel)),
                    content = renderer.renderTemplate(dbTableModel),
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
