package org.codeblessing.alpstein.codegen

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.codeblessing.alpstein.codegen.renderer.model.db.DbColumnModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbNameDefaults
import org.codeblessing.alpstein.codegen.renderer.model.db.DbSqlType
import org.codeblessing.alpstein.codegen.renderer.model.db.DbTableModel

class CodeGenPlaygroundTest {

    @Test
    fun `test code generation definition data`() {
        val schemaData = fetchSchemaData()
        assertTrue(schemaData.items.isNotEmpty())
        assertTrue(schemaData.enums.isNotEmpty())
        assertTrue(schemaData.uiEntities.isNotEmpty())
    }

    @Test
    fun `the essential model data passes all validations`() {
        validateSchemaData(fetchSchemaData())
    }

    @Test
    fun `test renderer model converter`() {
        val schemaData = fetchSchemaData()
        val schemaModel = convertToSchemaModel(schemaData)
        assertTrue(schemaModel.uiModel.uiEntities.isNotEmpty())
        assertTrue(schemaModel.uiModel.uiEnums.isNotEmpty())
        assertTrue(schemaModel.uiModel.uiItems.isNotEmpty())
        assertTrue(schemaModel.uiModel.uiEntitiesViews.isNotEmpty())

        assertTrue(schemaModel.beModel.items.isNotEmpty())
        assertTrue(schemaModel.beModel.enums.isNotEmpty())

        assertTrue(schemaModel.dbModel.tables.isNotEmpty())
    }
}
