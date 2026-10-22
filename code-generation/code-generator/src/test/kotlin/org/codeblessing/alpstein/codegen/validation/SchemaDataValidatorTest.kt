package org.codeblessing.alpstein.codegen.validation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.DbColumn
import org.codeblessing.alpstein.model.schema.DbEnum
import org.codeblessing.alpstein.model.schema.DbEnumValue
import org.codeblessing.alpstein.model.schema.DbItem
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.EnumType
import org.codeblessing.alpstein.model.schema.ExampleDataCategory
import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.ItemAttribute
import org.codeblessing.alpstein.model.schema.ItemAttributeType
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.PrimaryKeyType
import org.codeblessing.alpstein.model.schema.SchemaData
import org.codeblessing.alpstein.model.schema.UiBlock
import org.codeblessing.alpstein.model.schema.UiEntity
import org.codeblessing.alpstein.model.schema.UiEntityEditorColumn
import org.codeblessing.alpstein.model.schema.UiEntityEditorEntityNestedItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorRootItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorTab
import org.codeblessing.alpstein.model.schema.UiEntityEditorView
import org.codeblessing.alpstein.model.schema.UiEntitySearchResultView
import org.codeblessing.alpstein.model.schema.UiItem
import org.codeblessing.alpstein.model.schema.UiItemAttributeBlock

class SchemaDataValidatorTest {

    @Test
    fun `a schema without any violation is valid`() {
        SchemaDataValidator().validate(
            schemaData(
                items = listOf(contact, address),
                enums = listOf(gender),
                uiItems = listOf(
                    uiItem(itemId = contactId, displayAttributeNames = listOf("Firstname", "ContactSalutation")),
                    uiItem(
                        itemId = addressId,
                        displayAttributeNames = listOf("Street"),
                        defaultNestedItemEditorAttributeNames = listOf("Street"),
                    ),
                ),
                uiEntities = listOf(
                    uiEntity(
                        rootItem = contact,
                        searchResultAttributeNames = listOf("ContactId", "Firstname", "ContactSalutation"),
                        itemConfigurations = listOf(
                            rootItemConfiguration(attributeNames = listOf("ContactId", "Firstname")),
                            nestedItemConfiguration(addressId, attributeNames = listOf("Street")),
                        ),
                    )
                ),
                dbItems = listOf(
                    dbItem(
                        itemId = contactId,
                        tableName = "T_CONTACT",
                        columns = listOf(DbColumn(attributeName = "ContactId", columnName = "CONTACT_PK")),
                    ),
                ),
                dbEnums = listOf(
                    dbEnum(
                        enumId = genderId,
                        enumTypeName = "gender",
                        values = listOf(DbEnumValue(enumValue = "Mr", databaseValue = "MR.")),
                    ),
                ),
            )
        )
    }

    @Test
    fun `an item name is declared only once`() {
        val exception = validationFailureOf(schemaData(items = listOf(contact, contact)))

        assertEquals("SchemaData > items", exception.path.toString())
        assertProblemContains(exception, "The item name 'Contact' is declared by 2 items")
    }

    @Test
    fun `an item name is in PascalCase`() {
        val lowercaseItem = Item(itemId = TestItemId("contact"), attributes = emptyList())

        val exception = validationFailureOf(schemaData(items = listOf(lowercaseItem)))

        assertEquals("SchemaData > items[0] 'contact'", exception.path.toString())
        assertProblemContains(exception, "Declare it as 'Contact' instead.")
    }

    @Test
    fun `an item is identified by one single attribute only`() {
        val itemWithTwoPrimaryKeys = Item(
            itemId = contactId,
            attributes = listOf(
                attribute(name = "ContactId", type = BuiltInType.UUID, isPrimaryKey = true),
                attribute(name = "OtherId", type = BuiltInType.UUID, isPrimaryKey = true),
            ),
        )

        val exception = validationFailureOf(schemaData(items = listOf(itemWithTwoPrimaryKeys)))

        assertEquals("SchemaData > items[0] 'Contact'", exception.path.toString())
        assertProblemContains(exception, "attributes[0] 'ContactId', attributes[1] 'OtherId'")
    }

    @Test
    fun `an item is identified by an attribute of a primary key built-in type only`() {
        val typesNoItemIsIdentifiedBy = BuiltInType.entries
            .filterNot { builtInType -> PrimaryKeyType.entries.any { it.builtInType == builtInType } }

        typesNoItemIsIdentifiedBy.forEach { builtInType ->
            val itemWithUnsupportedPrimaryKey = Item(
                itemId = contactId,
                attributes = listOf(attribute(name = "ContactId", type = builtInType, isPrimaryKey = true)),
            )

            val exception = validationFailureOf(schemaData(items = listOf(itemWithUnsupportedPrimaryKey)))

            assertEquals("SchemaData > items[0] 'Contact' > attributes[0] 'ContactId'", exception.path.toString())
            assertProblemContains(exception, "it is of the built-in type $builtInType")
        }
    }

    @Test
    fun `an item is identified by an attribute of any primary key built-in type`() {
        PrimaryKeyType.entries.forEach { primaryKeyType ->
            val identifiedItem = Item(
                itemId = contactId,
                attributes = listOf(
                    attribute(name = "ContactId", type = primaryKeyType.builtInType, isPrimaryKey = true),
                ),
            )

            SchemaDataValidator().validate(schemaData(items = listOf(identifiedItem)))
        }
    }

    @Test
    fun `an item is identified by a single mandatory attribute only`() {
        val itemWithNullablePrimaryKey = Item(
            itemId = contactId,
            attributes = listOf(
                attribute(name = "ContactId", type = BuiltInType.UUID, isNullable = true, isPrimaryKey = true),
            ),
        )

        val exception = validationFailureOf(schemaData(items = listOf(itemWithNullablePrimaryKey)))

        assertEquals("SchemaData > items[0] 'Contact' > attributes[0] 'ContactId'", exception.path.toString())
        assertProblemContains(exception, "nullable=true and multiple=false")
    }

    @Test
    fun `an attribute name is declared only once per item`() {
        val itemWithDuplicateAttribute = Item(
            itemId = contactId,
            attributes = listOf(attribute(name = "Firstname"), attribute(name = "Firstname")),
        )

        val exception = validationFailureOf(schemaData(items = listOf(itemWithDuplicateAttribute)))

        assertEquals("SchemaData > items[0] 'Contact'", exception.path.toString())
        assertProblemContains(exception, "the attribute name 'Firstname' 2 times")
    }

    @Test
    fun `an attribute name is in PascalCase`() {
        val itemWithCamelCaseAttribute = Item(
            itemId = contactId,
            attributes = listOf(attribute(name = "firstname")),
        )

        val exception = validationFailureOf(schemaData(items = listOf(itemWithCamelCaseAttribute)))

        assertEquals("SchemaData > items[0] 'Contact' > attributes[0] 'firstname'", exception.path.toString())
        assertProblemContains(exception, "Declare it as 'Firstname' instead.")
    }

    @Test
    fun `only an attribute of a built-in type declares an example data category`() {
        val itemWithEnumAttribute = Item(
            itemId = contactId,
            attributes = listOf(
                attribute(
                    name = "ContactSalutation",
                    type = genderId,
                    exampleDataCategory = ExampleDataCategory.FIRSTNAME,
                ),
            ),
        )

        val exception = validationFailureOf(schemaData(items = listOf(itemWithEnumAttribute)))

        assertEquals("SchemaData > items[0] 'Contact' > attributes[0] 'ContactSalutation'", exception.path.toString())
        assertProblemContains(exception, "it is of the enum type 'Gender'")
    }

    @Test
    fun `an enum name is declared only once`() {
        val exception = validationFailureOf(schemaData(enums = listOf(gender, gender)))

        assertEquals("SchemaData > enums", exception.path.toString())
        assertProblemContains(exception, "The enum name 'Gender' is declared by 2 enum types")
    }

    @Test
    fun `an enum name is in PascalCase`() {
        val lowercaseEnum = EnumType(enumId = TestEnumId("gender"), enumValues = listOf("Mr"))

        val exception = validationFailureOf(schemaData(enums = listOf(lowercaseEnum)))

        assertEquals("SchemaData > enums[0] 'gender'", exception.path.toString())
        assertProblemContains(exception, "Declare it as 'Gender' instead.")
    }

    @Test
    fun `an enum value is declared only once per enum type`() {
        val enumWithDuplicateValue = EnumType(enumId = genderId, enumValues = listOf("Mr", "Ms", "Mr"))

        val exception = validationFailureOf(schemaData(enums = listOf(enumWithDuplicateValue)))

        assertEquals("SchemaData > enums[0] 'Gender'", exception.path.toString())
        assertProblemContains(exception, "the value 'Mr' 2 times, namely as enumValues[0], enumValues[2]")
    }

    @Test
    fun `an enum value is in PascalCase`() {
        val enumWithLowercaseValue = EnumType(enumId = genderId, enumValues = listOf("Mr", "mrs"))

        val exception = validationFailureOf(schemaData(enums = listOf(enumWithLowercaseValue)))

        assertEquals("SchemaData > enums[0] 'Gender' > enumValues[1] 'mrs'", exception.path.toString())
        assertProblemContains(exception, "Declare it as 'Mrs' instead.")
    }

    @Test
    fun `a ui configuration shows only attributes of its own item as display attributes`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiItems = listOf(uiItem(itemId = contactId, displayAttributeNames = listOf("Firstname", "Street"))),
            )
        )

        assertEquals(
            "SchemaData > uiItems[0] 'Contact' > displayAttributeNames[1] 'Street'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "the item 'Contact' has no such attribute")
    }

    @Test
    fun `a ui configuration is declared only for an item of the schema`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiItems = listOf(uiItem(itemId = addressId, displayAttributeNames = listOf("Street"))),
            )
        )

        assertEquals("SchemaData > uiItems[0] 'Address'", exception.path.toString())
        assertProblemContains(exception, "no such item is declared in the schema")
    }

    @Test
    fun `a ui configuration shows every display attribute only once`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiItems = listOf(
                    uiItem(itemId = contactId, displayAttributeNames = listOf("Firstname", "ContactId", "Firstname"))
                ),
            )
        )

        assertEquals("SchemaData > uiItems[0] 'Contact'", exception.path.toString())
        assertProblemContains(
            exception,
            "'Firstname' as a display attribute 2 times, namely as displayAttributeNames[0], " +
                    "displayAttributeNames[2]",
        )
    }

    @Test
    fun `a ui configuration shows no attribute of an item type as a display attribute`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiItems = listOf(
                    uiItem(itemId = contactId, displayAttributeNames = listOf("Firstname", "HomeAddress"))
                ),
            )
        )

        assertEquals(
            "SchemaData > uiItems[0] 'Contact' > displayAttributeNames[1] 'HomeAddress'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "that attribute is of the item 'Address'")
    }

    @Test
    fun `a ui configuration shows an attribute referencing another item as a display attribute`() {
        val contactWithReference = Item(
            itemId = contactId,
            attributes = listOf(
                attribute(name = "ContactId", type = BuiltInType.UUID, isPrimaryKey = true),
                attribute(name = "InvoiceAddress", type = addressId, isReference = true),
            ),
        )

        SchemaDataValidator().validate(
            schemaData(
                items = listOf(contactWithReference, address),
                uiItems = listOf(uiItem(itemId = contactId, displayAttributeNames = listOf("InvoiceAddress"))),
            )
        )
    }

    @Test
    fun `a search result shows only attributes of the root item`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiEntities = listOf(
                    uiEntity(rootItem = contact, searchResultAttributeNames = listOf("Firstname", "Street"))
                ),
            )
        )

        assertEquals(
            "SchemaData > uiEntities[0] 'Contact' > searchResultView > attributeNames[1] 'Street'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "the root item 'Contact' has no such attribute")
    }

    @Test
    fun `a search result shows no attribute of an item type`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiEntities = listOf(
                    uiEntity(rootItem = contact, searchResultAttributeNames = listOf("Firstname", "HomeAddress"))
                ),
            )
        )

        assertEquals(
            "SchemaData > uiEntities[0] 'Contact' > searchResultView > attributeNames[1] 'HomeAddress'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "that attribute is of the item 'Address'")
    }

    @Test
    fun `a search result shows an attribute referencing another item`() {
        val contactWithReference = Item(
            itemId = contactId,
            attributes = listOf(
                attribute(name = "ContactId", type = BuiltInType.UUID, isPrimaryKey = true),
                attribute(name = "InvoiceAddress", type = addressId, isReference = true),
            ),
        )

        SchemaDataValidator().validate(
            schemaData(
                items = listOf(contactWithReference, address),
                uiEntities = listOf(
                    uiEntity(
                        rootItem = contactWithReference,
                        searchResultAttributeNames = listOf("InvoiceAddress"),
                    )
                ),
            )
        )
    }

    @Test
    fun `an attribute block of the root item configuration shows only attributes of the root item`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiEntities = listOf(
                    uiEntity(
                        rootItem = contact,
                        itemConfigurations = listOf(
                            rootItemConfiguration(
                                attributeNames = listOf("Firstname"),
                                tabAttributeNames = listOf("Firstname", "Street"),
                            )
                        ),
                    )
                ),
            )
        )

        assertEquals(
            "SchemaData > uiEntities[0] 'Contact' > editorView > itemConfiguration[0] 'Contact' > " +
                    "tabs[0] 'tab.common' > columns[0] > blocks[1] 'Street'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "the item 'Contact' it is configured for has no such attribute")
    }

    @Test
    fun `an attribute block of a nested item configuration shows only attributes of that nested item`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact, address),
                uiEntities = listOf(
                    uiEntity(
                        rootItem = contact,
                        itemConfigurations = listOf(
                            nestedItemConfiguration(addressId, attributeNames = listOf("Street", "Firstname"))
                        ),
                    )
                ),
            )
        )

        assertEquals(
            "SchemaData > uiEntities[0] 'Contact' > editorView > itemConfiguration[0] 'Address' > " +
                    "noTab > columns[0] > blocks[1] 'Firstname'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "the item 'Address' it is configured for has no such attribute")
    }

    @Test
    fun `an attribute block of a default nested item editor shows only attributes of that item`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact, address),
                uiItems = listOf(
                    uiItem(itemId = addressId, defaultNestedItemEditorAttributeNames = listOf("Street", "Firstname"))
                ),
            )
        )

        assertEquals(
            "SchemaData > uiItems[0] 'Address' > defaultNestedItemEditor > columns[0] > blocks[1] 'Firstname'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "the item 'Address' it is configured for has no such attribute")
    }

    @Test
    fun `an editor is configured only for an item of the schema`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                uiEntities = listOf(
                    uiEntity(
                        rootItem = contact,
                        itemConfigurations = listOf(
                            nestedItemConfiguration(addressId, attributeNames = listOf("Street"))
                        ),
                    )
                ),
            )
        )

        assertEquals(
            "SchemaData > uiEntities[0] 'Contact' > editorView > itemConfiguration[0] 'Address'",
            exception.path.toString(),
        )
        assertProblemContains(exception, "no such item is declared in the schema")
    }

    @Test
    fun `a database configuration is declared only for an item of the schema`() {
        val exception = validationFailureOf(
            schemaData(items = listOf(contact), dbItems = listOf(dbItem(itemId = TestItemId("Unknown"))))
        )

        assertEquals("SchemaData > dbItems[0] 'Unknown'", exception.path.toString())
        assertProblemContains(exception, "no such item is declared in the schema")
    }

    @Test
    fun `a database configuration is declared only for an item with a primary key`() {
        val exception = validationFailureOf(
            schemaData(items = listOf(contact, address), dbItems = listOf(dbItem(itemId = addressId)))
        )

        assertEquals("SchemaData > dbItems[0] 'Address'", exception.path.toString())
        assertProblemContains(exception, "it declares no primary key")
    }

    @Test
    fun `an item is configured for the database only once`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                dbItems = listOf(dbItem(itemId = contactId), dbItem(itemId = contactId)),
            )
        )

        assertEquals("SchemaData > dbItems", exception.path.toString())
        assertProblemContains(exception, "configured for the database by 2 declarations")
    }

    @Test
    fun `a database configuration names a column only for an attribute of its own item`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                dbItems = listOf(
                    dbItem(
                        itemId = contactId,
                        columns = listOf(DbColumn(attributeName = "Street", columnName = "STREET")),
                    ),
                ),
            )
        )

        assertEquals("SchemaData > dbItems[0] 'Contact' > columns[0] 'Street'", exception.path.toString())
        assertProblemContains(exception, "the item 'Contact' has no such attribute")
    }

    @Test
    fun `a database configuration names a column for every attribute only once`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                dbItems = listOf(
                    dbItem(
                        itemId = contactId,
                        columns = listOf(
                            DbColumn(attributeName = "Firstname", columnName = "FIRST_NAME"),
                            DbColumn(attributeName = "Firstname", columnName = "GIVEN_NAME"),
                        ),
                    ),
                ),
            )
        )

        assertEquals("SchemaData > dbItems[0] 'Contact'", exception.path.toString())
        assertProblemContains(exception, "names a column for the attribute 'Firstname' 2 times")
    }

    @Test
    fun `every item is stored in a table of its own`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact, company),
                dbItems = listOf(dbItem(itemId = company.itemId, tableName = "CONTACT")),
            )
        )

        assertEquals("SchemaData > dbItems", exception.path.toString())
        assertProblemContains(exception, "The table 'CONTACT' is used by 2 items")
    }

    @Test
    fun `every attribute is stored in a column of its own`() {
        val exception = validationFailureOf(
            schemaData(
                items = listOf(contact),
                dbItems = listOf(
                    dbItem(
                        itemId = contactId,
                        columns = listOf(DbColumn(attributeName = "Firstname", columnName = "CONTACT_ID")),
                    ),
                ),
            )
        )

        assertEquals("SchemaData > dbItems", exception.path.toString())
        assertProblemContains(exception, "The column 'CONTACT_ID' of the table 'CONTACT' stores 2 attributes")
    }

    @Test
    fun `a database configuration is declared only for an enum type of the schema`() {
        val exception = validationFailureOf(
            schemaData(enums = listOf(gender), dbEnums = listOf(dbEnum(enumId = TestEnumId("Unknown"))))
        )

        assertEquals("SchemaData > dbEnums[0] 'Unknown'", exception.path.toString())
        assertProblemContains(exception, "no such enum type is declared in the schema")
    }

    @Test
    fun `an enum type is configured for the database only once`() {
        val exception = validationFailureOf(
            schemaData(enums = listOf(gender), dbEnums = listOf(dbEnum(enumId = genderId), dbEnum(enumId = genderId)))
        )

        assertEquals("SchemaData > dbEnums", exception.path.toString())
        assertProblemContains(exception, "configured for the database by 2 declarations")
    }

    @Test
    fun `a database configuration names a database value only for a value of its own enum type`() {
        val exception = validationFailureOf(
            schemaData(
                enums = listOf(gender),
                dbEnums = listOf(
                    dbEnum(enumId = genderId, values = listOf(DbEnumValue(enumValue = "Dr", databaseValue = "DR."))),
                ),
            )
        )

        assertEquals("SchemaData > dbEnums[0] 'Gender' > values[0] 'Dr'", exception.path.toString())
        assertProblemContains(exception, "the enum type 'Gender' has no such value")
    }

    @Test
    fun `a database configuration names a database value for every enum value only once`() {
        val exception = validationFailureOf(
            schemaData(
                enums = listOf(gender),
                dbEnums = listOf(
                    dbEnum(
                        enumId = genderId,
                        values = listOf(
                            DbEnumValue(enumValue = "Mr", databaseValue = "MR."),
                            DbEnumValue(enumValue = "Mr", databaseValue = "MISTER"),
                        ),
                    ),
                ),
            )
        )

        assertEquals("SchemaData > dbEnums[0] 'Gender'", exception.path.toString())
        assertProblemContains(exception, "names a database value for the enum value 'Mr' 2 times")
    }

    @Test
    fun `every enum type is stored in a SQL enum type of its own`() {
        val salutationId = TestEnumId("Salutation")
        val exception = validationFailureOf(
            schemaData(
                enums = listOf(gender, EnumType(enumId = salutationId, enumValues = listOf("Mr"))),
                dbEnums = listOf(
                    dbEnum(enumId = genderId, enumTypeName = "salutation"),
                    dbEnum(enumId = salutationId, enumTypeName = "salutation"),
                ),
            )
        )

        assertEquals("SchemaData > dbEnums", exception.path.toString())
        assertProblemContains(exception, "The SQL enum type 'salutation' is used by 2 enum types")
    }

    @Test
    fun `every enum value is stored as a database value of its own`() {
        val exception = validationFailureOf(
            schemaData(
                enums = listOf(gender),
                dbEnums = listOf(
                    dbEnum(enumId = genderId, values = listOf(DbEnumValue(enumValue = "Ms", databaseValue = "MR"))),
                ),
            )
        )

        assertEquals("SchemaData > dbEnums", exception.path.toString())
        assertProblemContains(exception, "The database value 'MR' of the enum type 'Gender' stores 2 enum values")
    }

    // **************
    // Test fixtures
    // **************

    private data class TestItemId(override val itemName: String) : ItemId

    private data class TestEnumId(override val enumName: String) : EnumId

    private val contactId = TestItemId("Contact")
    private val addressId = TestItemId("Address")
    private val genderId = TestEnumId("Gender")

    private val gender = EnumType(enumId = genderId, enumValues = listOf("Mr", "Ms", "Mrs"))

    private val contact = Item(
        itemId = contactId,
        attributes = listOf(
            attribute(name = "ContactId", type = BuiltInType.UUID, isPrimaryKey = true),
            attribute(name = "Firstname", exampleDataCategory = ExampleDataCategory.FIRSTNAME),
            attribute(name = "ContactSalutation", type = genderId),
            attribute(name = "HomeAddress", type = addressId),
        ),
    )

    private val address = Item(
        itemId = addressId,
        attributes = listOf(attribute(name = "Street")),
    )

    private val company = Item(
        itemId = TestItemId("Company"),
        attributes = listOf(attribute(name = "CompanyNumber", type = BuiltInType.NUMBER, isPrimaryKey = true)),
    )

    private fun attribute(
        name: String,
        type: ItemAttributeType = BuiltInType.STRING,
        isNullable: Boolean = false,
        isMultiple: Boolean = false,
        exampleDataCategory: ExampleDataCategory? = null,
        isPrimaryKey: Boolean = false,
        isReference: Boolean = false,
    ): ItemAttribute = ItemAttribute(
        attributeName = name,
        isNullable = isNullable,
        isMultiple = isMultiple,
        type = type,
        exampleDataCategory = exampleDataCategory,
        isPrimaryKey = isPrimaryKey,
        isReference = isReference,
    )

    private fun schemaData(
        items: List<Item> = emptyList(),
        enums: List<EnumType> = emptyList(),
        uiItems: List<UiItem> = emptyList(),
        uiEntities: List<UiEntity> = emptyList(),
        dbItems: List<DbItem> = emptyList(),
        dbEnums: List<DbEnum> = emptyList(),
    ): SchemaData = SchemaData(
        items = items,
        enums = enums,
        uiItems = uiItems,
        uiEntities = uiEntities,
        dbItems = dbItems,
        dbEnums = dbEnums,
    )

    private fun dbItem(
        itemId: ItemId,
        tableName: String? = null,
        columns: List<DbColumn> = emptyList(),
    ): DbItem = DbItem(itemId = itemId, tableName = tableName, columns = columns)

    private fun dbEnum(
        enumId: EnumId,
        enumTypeName: String? = null,
        values: List<DbEnumValue> = emptyList(),
    ): DbEnum = DbEnum(enumId = enumId, enumTypeName = enumTypeName, values = values)

    private fun uiItem(
        itemId: ItemId,
        displayAttributeNames: List<String> = emptyList(),
        defaultNestedItemEditorAttributeNames: List<String> = emptyList(),
    ): UiItem = UiItem(
        itemId = itemId,
        displayAttributeNames = displayAttributeNames,
        defaultNestedItemEditor = if (defaultNestedItemEditorAttributeNames.isEmpty()) {
            emptyList()
        } else {
            listOf(column(defaultNestedItemEditorAttributeNames))
        },
    )

    private fun uiEntity(
        rootItem: Item,
        searchResultAttributeNames: List<String> = emptyList(),
        itemConfigurations: List<UiEntityEditorItemConfiguration> = emptyList(),
    ): UiEntity = UiEntity(
        uiEntityName = rootItem.itemName,
        rootItem = rootItem,
        editorView = UiEntityEditorView(itemConfiguration = itemConfigurations),
        searchResultView = UiEntitySearchResultView(attributeNames = searchResultAttributeNames),
    )

    private fun rootItemConfiguration(
        attributeNames: List<String> = emptyList(),
        tabAttributeNames: List<String> = emptyList(),
    ): UiEntityEditorRootItemConfiguration = UiEntityEditorRootItemConfiguration(
        noTab = listOf(column(attributeNames)),
        tabs = if (tabAttributeNames.isEmpty()) {
            emptyList()
        } else {
            listOf(UiEntityEditorTab(tabTranslationKey = "tab.common", columns = listOf(column(tabAttributeNames))))
        },
    )

    private fun nestedItemConfiguration(
        itemId: ItemId,
        attributeNames: List<String> = emptyList(),
    ): UiEntityEditorEntityNestedItemConfiguration = UiEntityEditorEntityNestedItemConfiguration(
        itemId = itemId,
        noTab = listOf(column(attributeNames)),
    )

    private fun column(attributeNames: List<String>): UiEntityEditorColumn = UiEntityEditorColumn(
        blocks = attributeNames.map { UiItemAttributeBlock(attributeName = it) as UiBlock },
    )

    private fun validationFailureOf(schemaData: SchemaData): SchemaDataValidationException =
        assertThrows<SchemaDataValidationException> { SchemaDataValidator().validate(schemaData) }

    private fun assertProblemContains(exception: SchemaDataValidationException, expectedPart: String) {
        assertTrue(
            exception.problem.contains(expectedPart),
            "Expected the problem to contain '$expectedPart', but it was '${exception.problem}'",
        )
    }
}
