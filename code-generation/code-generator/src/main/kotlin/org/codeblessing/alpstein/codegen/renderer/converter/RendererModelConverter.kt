package org.codeblessing.alpstein.codegen.renderer.converter

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.codegen.renderer.model.SchemaModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeEnumModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeExampleDataGeneratorConfig
import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemDescriptionModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeItemModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BeReferencedItemModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbColumnModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbEnumModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbEnumValueModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbModel
import org.codeblessing.alpstein.codegen.renderer.model.db.DbNameDefaults
import org.codeblessing.alpstein.codegen.renderer.model.db.DbSqlType
import org.codeblessing.alpstein.codegen.renderer.model.db.DbTableModel
import org.codeblessing.alpstein.codegen.renderer.model.be.BuiltInTypeBeAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.be.EnumBeAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.be.ItemBeIAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.be.ItemReferenceBeAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.BuiltInTypeUiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.EnumUiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.ItemReferenceUiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.ItemUiIAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityViewsModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEnumModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemDescriptionModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiReferencedItemModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewColumnModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewItemModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.UiEntityFormViewTabModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormBlockModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormItemAttributeBlockModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormNamedSectionSplitBlockModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.entityform.blocks.UiEntityFormTextBlockModel
import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.DbEnum
import org.codeblessing.alpstein.model.schema.DbItem
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.EnumType
import org.codeblessing.alpstein.model.schema.ExampleDataCategory
import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.ItemAttribute
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.SchemaData
import org.codeblessing.alpstein.model.schema.UiBlock
import org.codeblessing.alpstein.model.schema.UiEntity
import org.codeblessing.alpstein.model.schema.UiEntityEditorColumn
import org.codeblessing.alpstein.model.schema.UiEntityEditorItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorRootItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorEntityNestedItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorTab
import org.codeblessing.alpstein.model.schema.UiItem
import org.codeblessing.alpstein.model.schema.UiItemAttributeBlock
import org.codeblessing.alpstein.model.schema.UiSectionBlock
import org.codeblessing.alpstein.model.schema.UiTextBlock

object RendererModelConverter {

    fun convertSchemaDataToSchemaModel(schemaData: SchemaData): SchemaModel {
        val allUiEnumModels = schemaData.enums.map { UiEnumModel(it) }

        // An item is independent of any UiEntity: it can appear in the editor of several of
        // them, therefore it is mapped exactly once.
        val uiItemPerItem = uiItemPerItem(schemaData.uiItems, schemaData.items)
        val allUiItemModels = schemaData.items.map { item ->
            val displayAttributeNames = uiItemPerItem[item.itemId]?.displayAttributeNames.orEmpty()
            val ownUiEntityNames = schemaData.uiEntities
                .filter { it.rootItem.itemId == item.itemId }
                .map { NameCase(it.uiEntityName) }
            mapUiItemModel(item, displayAttributeNames, ownUiEntityNames, schemaData.enums, schemaData.items)
        }
        // The tables and enum types are mapped first: an item that is stored in a table carries
        // it, so that a template reaching only the BeItemModel still knows the SQL names of its
        // attributes, and an enum carries how its values are spelled in the database.
        val dbModel = mapDbModel(schemaData)
        val allBeEnumModels = schemaData.enums.map { BeEnumModel(it, dbModel.enumOf(it.enumId)) }
        val allBeItemModels = schemaData.items.map { item ->
            val dbTable = dbModel.tables.singleOrNull { it.itemId == item.itemId }
            mapBeItemModel(item, dbTable, allBeEnumModels, schemaData.items)
        }

        return SchemaModel(
            uiModel = UiModel(
                uiItems = allUiItemModels,
                uiEnums = allUiEnumModels,
                uiEntitiesViews = schemaData.uiEntities.map { uiEntity ->
                    val allNestedItemIds = HierarchicalItemSearch.findAllItemNames(uiEntity.rootItem, schemaData.items)
                    mapUiEntityViewsModel(uiEntity, allNestedItemIds, allUiItemModels, allUiEnumModels, uiItemPerItem)
                }
            ),
            beModel = BeModel(
                items = allBeItemModels,
                enums = allBeEnumModels,
            ),
            dbModel = dbModel,
        )
    }

    // **************
    // UI items
    // **************

    private fun mapUiItemModel(
        item: Item,
        displayAttributeNames: List<String>,
        ownUiEntityNames: List<NameCase>,
        enums: List<EnumType>,
        items: List<Item>,
    ): UiItemModel {
        val itemDescription = toUiItemDescriptionModel(item.itemId)
        val attributes = item.attributes.map { mapUiItemAttribute(itemDescription, it, enums, items) }
        return UiItemModel(
            itemDescription = itemDescription,
            attributes = attributes,
            idAttribute = item.idAttributeName?.let { idAttributeName ->
                val idAttribute = attributes.single { it.attributeName.isEqual(idAttributeName) }
                require(idAttribute is BuiltInTypeUiAttributeModel && !idAttribute.isItemReference) {
                    primaryKeyIsNoBuiltInTypeMessage(item)
                }
                idAttribute
            },
            displayAttributes = mapDisplayAttributes(item, displayAttributeNames, attributes),
            ownUiEntityNames = ownUiEntityNames,
        )
    }

    /** An item that declares no `uiItem` at all is missing from the returned map. */
    private fun uiItemPerItem(uiItems: List<UiItem>, items: List<Item>): Map<ItemId, UiItem> {
        val itemIds = items.map { it.itemId }.toSet()

        return uiItems.groupBy { it.itemId }
            .mapValues { (itemId, uiItemsOfItem) ->
                require(itemId in itemIds) {
                    "There is a 'uiItem' declaration for the item '${itemId.itemName}', but no such item " +
                            "is declared in the schema. Available are ${itemIds.map { it.itemName }}."
                }
                require(uiItemsOfItem.size == 1) {
                    "The item '${itemId.itemName}' is configured by ${uiItemsOfItem.size} 'uiItem' " +
                            "declarations. An item is configured for the UI exactly once."
                }
                uiItemsOfItem.single()
            }
    }

    /**
     * The declared display attributes in the declared order, or, as long as an item declares
     * none, every single-valued text attribute of the item.
     */
    private fun mapDisplayAttributes(
        item: Item,
        displayAttributeNames: List<String>,
        attributes: List<UiAttributeModel>,
    ): List<UiAttributeModel> {
        if (displayAttributeNames.isEmpty()) {
            return attributes.filter { it.isDisplayable }
        }

        return displayAttributeNames.map { attributeName ->
            val attribute = attributes.singleOrNull { it.attributeName.isEqual(attributeName) }
                ?: throw NoSuchElementException(
                    "The 'uiItem' of the item '${item.itemName}' declares '$attributeName' as a display " +
                            "attribute, but the item has no such attribute. " +
                            "Available are ${attributes.map { it.attributeName.camelCase }}."
                )

            require(attribute.isDisplayable) {
                "The 'uiItem' of the item '${item.itemName}' declares '$attributeName' as a display attribute, " +
                        "but only a single-valued attribute can be one, because a display attribute is " +
                        "rendered as a plain string wherever a reference to the item is shown."
            }
            attribute
        }
    }

    /**
     * Whether an attribute can be shown as one of the display attributes of its item.
     * A reference is left out although it is a UUID: it identifies another item instead
     * of describing this one.
     */
    private val UiAttributeModel.isDisplayable: Boolean
        get() = this is BuiltInTypeUiAttributeModel
                && !isList

    private fun toUiItemDescriptionModel(itemId: ItemId): UiItemDescriptionModel {
        return UiItemDescriptionModel(
            itemId = itemId,
            itemName = NameCase(itemId.itemName),
        )
    }

    private fun mapUiItemAttribute(
        item: UiItemDescriptionModel,
        itemAttribute: ItemAttribute,
        enums: List<EnumType>,
        items: List<Item>,
    ): UiAttributeModel {
        val itemAttributeType = itemAttribute.type
        val attributeName = NameCase(itemAttribute.attributeName)

        return when (itemAttributeType) {
            is BuiltInType -> BuiltInTypeUiAttributeModel(
                item = item,
                attributeName = attributeName,
                isNullable = itemAttribute.isNullable,
                isList = itemAttribute.isMultiple,
                customValidation = itemAttribute.customValidation,
                builtInType = itemAttributeType,
                isPrimaryKey = itemAttribute.isPrimaryKey,
            )
            is EnumId -> {
                val enumType = enums.singleOrNull { it.enumId == itemAttributeType }
                    ?: throw NoSuchElementException("EnumType ${itemAttributeType.enumName} not found in schema enums")
                EnumUiAttributeModel(
                    item = item,
                    attributeName = attributeName,
                    isNullable = itemAttribute.isNullable,
                    isList = itemAttribute.isMultiple,
                    customValidation = itemAttribute.customValidation,
                    enum = UiEnumModel(enumType),
                )
            }
            is ItemId -> if (itemAttribute.isReference) {
                ItemReferenceUiAttributeModel(
                    item = item,
                    attributeName = attributeName,
                    isNullable = itemAttribute.isNullable,
                    isList = itemAttribute.isMultiple,
                    customValidation = itemAttribute.customValidation,
                    referencedItem = referencedItem(itemAttributeType, items).toUiReferencedItemModel(),
                )
            } else {
                ItemUiIAttributeModel(
                    item = item,
                    attributeName = attributeName,
                    isNullable = itemAttribute.isNullable,
                    isList = itemAttribute.isMultiple,
                    customValidation = itemAttribute.customValidation,
                    referencedItem = toUiItemDescriptionModel(itemAttributeType),
                )
            }
        }
    }

    private fun Item.toUiReferencedItemModel(): UiReferencedItemModel {
        return UiReferencedItemModel(
            itemId = itemId,
            itemName = NameCase(itemName),
            idAttributeName = NameCase(requireNotNull(idAttributeName)),
            idBuiltInType = primaryKeyBuiltInType(),
        )
    }

    // **************
    // Backend items
    // **************

    private fun mapBeItemModel(
        item: Item,
        dbTable: DbTableModel?,
        enums: List<BeEnumModel>,
        items: List<Item>,
    ): BeItemModel {
        val itemDescription = toBeItemDescriptionModel(item.itemId)
        val dbColumnPerAttributeName = dbTable?.columns.orEmpty().associateBy { it.attributeName }
        val attributes = item.attributes.map {
            mapBeItemAttribute(itemDescription, it, dbColumnPerAttributeName[NameCase(it.attributeName)], enums, items)
        }
        return BeItemModel(
            itemDescription = itemDescription,
            attributes = attributes,
            idAttribute = item.idAttributeName?.let { idAttributeName ->
                val idAttribute = attributes.single { it.attributeName.isEqual(idAttributeName) }
                require(idAttribute is BuiltInTypeBeAttributeModel && !idAttribute.isItemReference) {
                    primaryKeyIsNoBuiltInTypeMessage(item)
                }
                idAttribute
            },
            dbTable = dbTable,
        )
    }

    private fun toBeItemDescriptionModel(itemId: ItemId): BeItemDescriptionModel {
        return BeItemDescriptionModel(
            itemId = itemId,
            itemName = NameCase(itemId.itemName),
        )
    }

    private fun mapBeItemAttribute(
        item: BeItemDescriptionModel,
        itemAttribute: ItemAttribute,
        dbColumn: DbColumnModel?,
        enums: List<BeEnumModel>,
        items: List<Item>,
    ): BeAttributeModel {
        val itemAttributeType = itemAttribute.type
        val attributeName = NameCase(itemAttribute.attributeName)

        return when (itemAttributeType) {
            is BuiltInType -> BuiltInTypeBeAttributeModel(
                item = item,
                attributeName = attributeName,
                isNullable = itemAttribute.isNullable,
                isList = itemAttribute.isMultiple,
                dbColumn = dbColumn,
                builtInType = itemAttributeType,
                exampleDataGeneratorConfig = toExampleDataGeneratorConfig(item, itemAttribute)
            )
            is EnumId -> {
                val enum = enums.singleOrNull { it.enumId == itemAttributeType }
                    ?: throw NoSuchElementException("EnumType ${itemAttributeType.enumName} not found in schema enums")
                EnumBeAttributeModel(
                    item = item,
                    attributeName = attributeName,
                    isNullable = itemAttribute.isNullable,
                    isList = itemAttribute.isMultiple,
                    dbColumn = dbColumn,
                    enum = enum,
                )
            }
            is ItemId -> if (itemAttribute.isReference) {
                ItemReferenceBeAttributeModel(
                    item = item,
                    attributeName = attributeName,
                    isNullable = itemAttribute.isNullable,
                    isList = itemAttribute.isMultiple,
                    dbColumn = dbColumn,
                    referencedItem = referencedItem(itemAttributeType, items).toBeReferencedItemModel(),
                )
            } else {
                ItemBeIAttributeModel(
                    item = item,
                    attributeName = attributeName,
                    isNullable = itemAttribute.isNullable,
                    isList = itemAttribute.isMultiple,
                    dbColumn = dbColumn,
                    referencedItem = toBeItemDescriptionModel(itemAttributeType),
                )
            }
        }
    }

    private fun Item.toBeReferencedItemModel(): BeReferencedItemModel {
        return BeReferencedItemModel(
            itemId = itemId,
            itemName = NameCase(itemName),
            idAttributeName = NameCase(requireNotNull(idAttributeName)),
            idBuiltInType = primaryKeyBuiltInType(),
        )
    }

    /** The built-in type this item is identified by, i.e. the type a reference to it is stored as. */
    private fun Item.primaryKeyBuiltInType(): BuiltInType {
        val primaryKeyType = requireNotNull(idAttribute).type
        require(primaryKeyType is BuiltInType) { primaryKeyIsNoBuiltInTypeMessage(this) }
        return primaryKeyType
    }

    private fun primaryKeyIsNoBuiltInTypeMessage(item: Item): String =
        "The item '${item.itemName}' is identified by the attribute '${item.idAttributeName}', " +
                "which is of ${requireNotNull(item.idAttribute).type} instead of a plain built-in type."

    /**
     * The item an attribute of type [ItemId] with `isReference` refers to. The referenced item
     * must be declared in the same schema, otherwise the reference could never be resolved,
     * and it must declare a primary key, otherwise there is no identifier to store.
     */
    private fun referencedItem(itemId: ItemId, items: List<Item>): Item {
        val item = items.singleOrNull { it.itemId == itemId }
            ?: throw NoSuchElementException("Item ${itemId.itemName} not found in schema items")

        require(item.hasPrimaryKey) {
            "The item '${item.itemName}' is referenced by another item, but it declares no " +
                    "primary key. Only an item with a primary key can be referenced, because a " +
                    "reference stores exactly that primary key."
        }
        return item
    }

    // **************
    // Database tables
    // **************

    private fun mapDbModel(schemaData: SchemaData): DbModel {
        // Only an item with a primary key is stored on its own; one without is nested into
        // the row of the item holding it and has therefore no table.
        val dbItemPerItem = schemaData.dbItems.associateBy { it.itemId }
        val dbEnumPerEnumType = schemaData.dbEnums.associateBy { it.enumId }
        val enums = schemaData.enums.map { mapDbEnumModel(it, dbEnumPerEnumType[it.enumId]) }

        return DbModel(
            tables = schemaData.items
                .filter { it.hasPrimaryKey }
                .map { mapDbTableModel(it, dbItemPerItem[it.itemId], enums, schemaData.items) },
            enums = enums,
        )
    }

    private fun mapDbEnumModel(enumType: EnumType, dbEnum: DbEnum?): DbEnumModel = DbEnumModel(
        enumId = enumType.enumId,
        enumName = NameCase(enumType.enumName),
        enumTypeName = DbNameDefaults.enumTypeName(dbEnum),
        values = enumType.enumValues.map {
            DbEnumValueModel(enumValue = NameCase(it), databaseValue = DbNameDefaults.databaseValue(it, dbEnum))
        },
    )

    private fun mapDbTableModel(
        item: Item,
        dbItem: DbItem?,
        enums: List<DbEnumModel>,
        items: List<Item>,
    ): DbTableModel = DbTableModel(
        itemId = item.itemId,
        itemName = NameCase(item.itemName),
        tableName = DbNameDefaults.tableName(item, dbItem),
        columns = item.attributes.map { mapDbColumnModel(it, dbItem, enums, items) },
    )

    private fun mapDbColumnModel(
        itemAttribute: ItemAttribute,
        dbItem: DbItem?,
        enums: List<DbEnumModel>,
        items: List<Item>,
    ): DbColumnModel = DbColumnModel(
        attributeName = NameCase(itemAttribute.attributeName),
        columnName = DbNameDefaults.columnName(itemAttribute, dbItem),
        sqlType = dbSqlType(itemAttribute, items),
        enumTypeName = dbEnumTypeName(itemAttribute, enums),
        isNullable = itemAttribute.isNullable,
        isMultiple = itemAttribute.isMultiple,
        isPrimaryKey = itemAttribute.isPrimaryKey,
    )

    /** The SQL enum type a column storing enum values is declared with. */
    private fun dbEnumTypeName(itemAttribute: ItemAttribute, enums: List<DbEnumModel>): String? {
        val enumId = itemAttribute.type as? EnumId ?: return null
        return enums.single { it.enumId == enumId }.enumTypeName
    }

    /**
     * The type one value of the attribute is stored as; a list of them becomes an array of that
     * type, which [DbColumnModel] derives.
     *
     * Only a nested item instance has no flat relational representation and is therefore stored
     * as `jsonb`. A reference is stored as the primary key of the item it refers to and is
     * therefore of that key's type.
     */
    private fun dbSqlType(itemAttribute: ItemAttribute, items: List<Item>): DbSqlType =
        when (val itemAttributeType = itemAttribute.type) {
            is BuiltInType -> dbSqlType(itemAttributeType)
            is EnumId -> DbSqlType.TEXT
            is ItemId -> if (itemAttribute.isReference) {
                dbSqlType(referencedItem(itemAttributeType, items).primaryKeyBuiltInType())
            } else {
                DbSqlType.JSONB
            }
        }

    private fun dbSqlType(builtInType: BuiltInType): DbSqlType = when (builtInType) {
        BuiltInType.STRING -> DbSqlType.TEXT
        BuiltInType.NUMBER -> DbSqlType.INTEGER
        BuiltInType.DOUBLE -> DbSqlType.DOUBLE_PRECISION
        BuiltInType.BOOLEAN -> DbSqlType.BOOLEAN
        BuiltInType.UUID -> DbSqlType.UUID
    }

    // **************
    // Example data
    // **************

    private fun toExampleDataGeneratorConfig(item: BeItemDescriptionModel, itemAttribute: ItemAttribute): BeExampleDataGeneratorConfig {
        val exampleDataCategory = itemAttribute.exampleDataCategory ?: defaultExampleDataCategory(itemAttribute)
        if(exampleDataCategory.supportedBuiltInType != itemAttribute.type) {
            throw IllegalArgumentException(
                "The attribute '${item.itemId.itemName}.${itemAttribute.attributeName}' has the built-in type ${itemAttribute.type} " +
                        "but the exampleDataCategory is only compatible to ${exampleDataCategory.supportedBuiltInType}"
            )
        }

        return BeExampleDataGeneratorConfig(
            generatorNamePrefix = NameCase(exampleDataCategory.generatorPrefixName),
            isNullable = itemAttribute.isNullable,
            numberOfEntries = if(itemAttribute.isMultiple) 3 else 1,
        )
    }

    private fun defaultExampleDataCategory(itemAttribute: ItemAttribute): ExampleDataCategory {
        val type = itemAttribute.type
        if(type is BuiltInType) {
            return ExampleDataCategory.randomDataOf(type)
        } else {
            throw IllegalStateException("ExampleDataCategory must be built-in type here")
        }
    }

    // **************
    // UiEntity editor views
    // **************

    private fun mapUiEntityViewsModel(
        uiEntity: UiEntity,
        entityItemModelIds: Set<ItemId>,
        allUiItemModels: List<UiItemModel>,
        allUiEnumModels: List<UiEnumModel>,
        uiItemPerItem: Map<ItemId, UiItem>,
    ): UiEntityViewsModel {
        val entityRootItem = allUiItemModels.single { it.itemId == uiEntity.rootItem.itemId }
        val uiEntityModel = UiEntityModel(
            entityName = NameCase(uiEntity.uiEntityName),
            entityRootItem = entityRootItem,
            entityItemModels = allUiItemModels.filter { it.itemId in entityItemModelIds },
            entityEnumTypes = allUiEnumModels, // TODO filter for only the enums used in this UiEntity
            searchResultAttributes = mapSearchResultAttributes(uiEntity, entityRootItem),
        )

        val itemConfigurations = itemConfigurationsWithDefaults(uiEntity, entityItemModelIds, uiItemPerItem)

        val uiEntityItems = itemConfigurations.map { itemConfiguration ->
            val itemModel = when (itemConfiguration) {
                is UiEntityEditorRootItemConfiguration -> uiEntityModel.entityRootItem
                is UiEntityEditorEntityNestedItemConfiguration -> requireNotNull(uiEntityModel.entityItemModels.firstOrNull { it.itemName.isEqual(itemConfiguration.itemId.itemName) }) {
                    "No item found with item id '${itemConfiguration.itemId.itemName}' within items ${uiEntityModel.entityItemModels.map { it.itemName }}"
                }
            }

            val noTab = itemConfiguration.noTab.map { mapUiEntityColumn(uiEntityModel = uiEntityModel, uiItemModel = itemModel, column = it) }

            val tabs = when (itemConfiguration) {
                is UiEntityEditorRootItemConfiguration -> itemConfiguration.tabs.map { mapUiEntityTab(uiEntityModel = uiEntityModel, uiItemModel = itemModel, tab = it) }
                is UiEntityEditorEntityNestedItemConfiguration -> emptyList()
            }

            UiEntityFormViewItemModel(
                entity = uiEntityModel,
                item = itemModel,
                noTab = noTab,
                tabs = tabs,
            )
        }

        return UiEntityViewsModel(
            uiEntity = uiEntityModel,
            formView = UiEntityFormViewModel(
                entity = uiEntityModel,
                entityItems = uiEntityItems,
            )
        )
    }

    /**
     * The configurations the editor declares itself, followed by one configuration for every
     * nested item of the entity that the editor does not configure at all, but whose `uiItem`
     * declares a default nested item editor.
     *
     * The root item is left to [UiEntityEditorRootItemConfiguration], it is never configured
     * by such a default.
     */
    private fun itemConfigurationsWithDefaults(
        uiEntity: UiEntity,
        entityItemModelIds: Set<ItemId>,
        uiItemPerItem: Map<ItemId, UiItem>,
    ): List<UiEntityEditorItemConfiguration> {
        val declaredConfigurations = uiEntity.editorView.itemConfiguration
        val configuredNestedItemIds = declaredConfigurations
            .filterIsInstance<UiEntityEditorEntityNestedItemConfiguration>()
            .map { it.itemId }
            .toSet()

        val defaultConfigurations = (entityItemModelIds - uiEntity.rootItem.itemId - configuredNestedItemIds)
            .mapNotNull { itemId ->
                uiItemPerItem[itemId]?.defaultNestedItemEditor
                    ?.takeIf { it.isNotEmpty() }
                    ?.let { UiEntityEditorEntityNestedItemConfiguration(itemId = itemId, noTab = it) }
            }

        return declaredConfigurations + defaultConfigurations
    }

    private fun mapSearchResultAttributes(uiEntity: UiEntity, entityRootItem: UiItemModel): List<UiAttributeModel> {
        return uiEntity.searchResultView.attributeNames.map { attributeName ->
            entityRootItem.attributes.singleOrNull { it.attributeName.isEqual(attributeName) }
                ?: throw NoSuchElementException(
                    "The search result of the UiEntity '${uiEntity.uiEntityName}' declares the attribute " +
                            "'$attributeName', but the item '${entityRootItem.itemName.pascalCase}' has no such " +
                            "attribute. Available are ${entityRootItem.attributes.map { it.attributeName.camelCase }}."
                )
        }
    }

    private fun mapUiEntityTab(uiEntityModel: UiEntityModel, uiItemModel: UiItemModel, tab: UiEntityEditorTab): UiEntityFormViewTabModel {
        return UiEntityFormViewTabModel(
            entity = uiEntityModel,
            tabTranslationKey = tab.tabTranslationKey,
            columns = tab.columns.map { mapUiEntityColumn(uiEntityModel = uiEntityModel, uiItemModel = uiItemModel, column = it) })
    }

    private fun mapUiEntityColumn(uiEntityModel: UiEntityModel, uiItemModel: UiItemModel, column: UiEntityEditorColumn): UiEntityFormViewColumnModel {
        return UiEntityFormViewColumnModel(
            entity = uiEntityModel,
            blocks = column.blocks.map { mapUiEntityBlock(uiEntityModel = uiEntityModel, uiItemModel = uiItemModel, block = it) },
        )
    }

    private fun mapUiEntityBlock(uiEntityModel: UiEntityModel, uiItemModel: UiItemModel, block: UiBlock): UiEntityFormBlockModel {
        return when (block) {
            is UiItemAttributeBlock -> UiEntityFormItemAttributeBlockModel(
                entity = uiEntityModel,
                item = uiItemModel,
                attribute = uiItemModel.attributes.single { it.attributeName.isEqual(block.attributeName) },
            )
            is UiSectionBlock -> UiEntityFormNamedSectionSplitBlockModel(block.sectionTranslationKey)
            is UiTextBlock -> UiEntityFormTextBlockModel(block.textTranslationKey)
        }
    }
}
