package org.codeblessing.alpstein.codegen.renderer

import org.codeblessing.alpstein.codegen.renderer.be.BeRenderer
import org.codeblessing.alpstein.codegen.renderer.be.MarkdownMermaidERDDocumentationRenderer
import org.codeblessing.alpstein.codegen.renderer.model.be.BeModel
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.isDirectory
import kotlin.io.path.writeText

object DocumentationRendering {

    fun renderDocumentationFiles(
        pathToGeneratedDocumentation: Path,
        beModel: BeModel,
    ) {
        val worker = RenderingWorker(
            pathToGeneratedDocumentation = pathToGeneratedDocumentation,
        )
        worker.renderDocumentationFiles(beModel)
    }

    private data class RenderingWorker(
        val pathToGeneratedDocumentation: Path,

        ) {

        fun renderDocumentationFiles(beModel: BeModel) {
            renderAllItem(beModel)
        }

        private fun renderAllItem(beModel: BeModel) {
            val itemsRenderers: List<Pair<BeRenderer, Path>> = listOf(
                MarkdownMermaidERDDocumentationRenderer to pathToGeneratedDocumentation,
            )

            itemsRenderers.forEach { (renderer, basePath) ->
                writeFile(
                    filePath = basePath.resolve(renderer.filePath(beModel)),
                    content = renderer.renderTemplate(beModel),
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
