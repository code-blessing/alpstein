package org.codeblessing.alpstein.codegen.renderer.db

import org.codeblessing.alpstein.codegen.renderer.model.db.DbTableModel

interface DbItemRenderer {
    fun renderTemplate(model: DbTableModel): String
    fun filePath(model: DbTableModel): String
}
