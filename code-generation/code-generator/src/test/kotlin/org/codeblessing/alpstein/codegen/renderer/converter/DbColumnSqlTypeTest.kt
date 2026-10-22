package org.codeblessing.alpstein.codegen.renderer.converter

import org.junit.jupiter.api.Test
import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.DbEnum
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.EnumType
import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.ItemAttribute
import org.codeblessing.alpstein.model.schema.ItemAttributeType
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.SchemaData
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private data class TestItemId(override val itemName: String) : ItemId

private data class TestEnumId(override val enumName: String) : EnumId

/**
 * The SQL type every kind of attribute is stored as. This is the contract the runtime helpers
 * in `PostgresSqlSupport` have to agree with: whatever gets an array column here has to be
 * written and read as an array there.
 */
internal class DbColumnSqlTypeTest {

    private val salutationId = TestEnumId("Salutation")
    private val nicknameId = TestEnumId("Nickname")
    private val addressId = TestItemId("Address")
    private val companyId = TestItemId("Company")
    private val contactId = TestItemId("Contact")

    @Test
    fun `stores a single value in a column of its own type`() {
        assertEquals("uuid", sqlTypeNameOf("contactId"))
        assertEquals("text", sqlTypeNameOf("name"))
        assertEquals("integer", sqlTypeNameOf("age"))
        assertEquals("double precision", sqlTypeNameOf("height"))
        assertEquals("boolean", sqlTypeNameOf("vegetarian"))
        assertEquals("uuid", sqlTypeNameOf("externalId"))
        assertEquals("salutation_type", sqlTypeNameOf("salutation"))
        assertEquals("text", sqlTypeNameOf("nickname"))
        assertEquals("integer", sqlTypeNameOf("employer"))
    }

    @Test
    fun `stores a list of values with a flat representation in an array column`() {
        assertEquals("text[]", sqlTypeNameOf("names"))
        assertEquals("integer[]", sqlTypeNameOf("ages"))
        assertEquals("double precision[]", sqlTypeNameOf("heights"))
        assertEquals("boolean[]", sqlTypeNameOf("flags"))
        assertEquals("uuid[]", sqlTypeNameOf("externalIds"))
        assertEquals("salutation_type[]", sqlTypeNameOf("salutations"))
        assertEquals("text[]", sqlTypeNameOf("nicknames"))
        // a reference stores the primary key of the item it refers to
        assertEquals("integer[]", sqlTypeNameOf("employers"))
    }

    @Test
    fun `stores a nested item and a list of them as jsonb`() {
        assertEquals("jsonb", sqlTypeNameOf("home"))
        assertEquals("jsonb", sqlTypeNameOf("addresses"))
    }

    @Test
    fun `casts the parameter of exactly the array and jsonb columns`() {
        assertTrue(columnOf("names").isArray)
        assertTrue(columnOf("names").requiresParameterCast)
        assertTrue(columnOf("addresses").requiresParameterCast)
        assertFalse(columnOf("addresses").isArray)
        assertFalse(columnOf("name").requiresParameterCast)
        assertFalse(columnOf("salutation").requiresParameterCast)
    }

    private fun sqlTypeNameOf(attributeName: String) = columnOf(attributeName).sqlTypeName

    private fun columnOf(attributeName: String) = RendererModelConverter
        .convertSchemaDataToSchemaModel(schemaData)
        .dbModel
        .tables
        .single { it.itemId == contactId }
        .columns
        .single { it.attributeName.camelCase == attributeName }

    private val schemaData = SchemaData(
        items = listOf(
            Item(itemId = addressId, attributes = listOf(attribute("street"))),
            Item(
                itemId = companyId,
                attributes = listOf(attribute("companyNumber", BuiltInType.NUMBER, isPrimaryKey = true)),
            ),
            Item(
                itemId = contactId,
                attributes = listOf(
                    attribute("contactId", BuiltInType.UUID, isPrimaryKey = true),
                    attribute("name"),
                    attribute("age", BuiltInType.NUMBER),
                    attribute("height", BuiltInType.DOUBLE),
                    attribute("vegetarian", BuiltInType.BOOLEAN),
                    attribute("externalId", BuiltInType.UUID),
                    attribute("salutation", salutationId),
                    attribute("nickname", nicknameId),
                    attribute("employer", companyId, isReference = true),
                    attribute("home", addressId),
                    attribute("names", isMultiple = true),
                    attribute("ages", BuiltInType.NUMBER, isMultiple = true),
                    attribute("heights", BuiltInType.DOUBLE, isMultiple = true),
                    attribute("flags", BuiltInType.BOOLEAN, isMultiple = true),
                    attribute("externalIds", BuiltInType.UUID, isMultiple = true),
                    attribute("salutations", salutationId, isMultiple = true),
                    attribute("nicknames", nicknameId, isMultiple = true),
                    attribute("employers", companyId, isReference = true, isMultiple = true),
                    attribute("addresses", addressId, isMultiple = true),
                ),
            ),
        ),
        enums = listOf(
            EnumType(enumId = salutationId, enumValues = listOf("MR", "MS")),
            EnumType(enumId = nicknameId, enumValues = listOf("BUDDY")),
        ),
        uiItems = emptyList(),
        uiEntities = emptyList(),
        dbItems = emptyList(),
        // the nickname enum declares no SQL type and is therefore stored as text
        dbEnums = listOf(DbEnum(enumId = salutationId, enumTypeName = "salutation_type", values = emptyList())),
    )

    private fun attribute(
        name: String,
        type: ItemAttributeType = BuiltInType.STRING,
        isMultiple: Boolean = false,
        isPrimaryKey: Boolean = false,
        isReference: Boolean = false,
    ): ItemAttribute = ItemAttribute(
        attributeName = name,
        isNullable = false,
        isMultiple = isMultiple,
        type = type,
        exampleDataCategory = null,
        isPrimaryKey = isPrimaryKey,
        isReference = isReference,
    )
}
