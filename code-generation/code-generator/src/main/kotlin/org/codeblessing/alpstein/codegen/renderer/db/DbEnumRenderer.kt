package org.codeblessing.alpstein.codegen.renderer.db

import org.codeblessing.alpstein.codegen.renderer.model.db.DbEnumModel

interface DbEnumRenderer {
    fun renderTemplate(model: DbEnumModel): String
    fun filePath(model: DbEnumModel): String
}
