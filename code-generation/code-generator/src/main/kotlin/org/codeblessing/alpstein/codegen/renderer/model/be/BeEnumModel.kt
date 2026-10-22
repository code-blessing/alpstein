package org.codeblessing.alpstein.codegen.renderer.model.be

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.codegen.renderer.model.db.DbEnumModel
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.EnumType

data class BeEnumModel(
    private val enumType: EnumType,
    /** How the values of this enum type are spelled in the database. */
    val dbEnum: DbEnumModel,
) {
    val enumId: EnumId = enumType.enumId
    val enumName: NameCase = NameCase(enumType.enumId.enumName)
    val enumValues: List<NameCase> = enumType.enumValues.map { NameCase(it) }
}
