package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.*
import org.codeblessing.alpstein.model.builders.DbEnumDsl
import org.codeblessing.alpstein.model.builders.DbItemDsl
import org.codeblessing.alpstein.model.builders.UiEntityDsl
import org.codeblessing.alpstein.model.builders.UiItemDsl
import org.codeblessing.alpstein.model.builders.EnumDsl
import org.codeblessing.alpstein.model.builders.ItemDsl
import org.codeblessing.alpstein.model.builders.SchemaDsl
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.DbEnum
import org.codeblessing.alpstein.model.schema.DbItem
import org.codeblessing.alpstein.model.schema.EnumType
import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.SchemaData
import org.codeblessing.alpstein.model.schema.UiEntity
import org.codeblessing.alpstein.model.schema.UiItem

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = SchemaData::class, alias = "schema")
interface SchemaBuilder: SchemaDsl {

    // **************
    // Item
    // **************

    @BuilderMethod
    @NewClazzModel(clazz = Item::class, alias = "item")
    @SetClazzModelOfAlias(alias = "schema", clazzProperty = "items", referencedAlias = "item")
    fun createNewItemInternal(
        @SetAsClazzModelId(alias = "item")
        @SetAsValue(alias = "item", clazzProperty = "itemId")
        itemId: ItemId,
        @InjectBuilder builder: ItemBuilder.() -> Unit,
    )

    override fun item(itemId: ItemId, builder: ItemDsl.() -> Unit) {
        // cast from org.codeblessing.alpstein.codegen.builders.XyzBuilder to our XyzBuilder
        createNewItemInternal(itemId, builder)
    }

    // **************
    // Enum
    // **************

    @BuilderMethod
    @NewClazzModel(clazz = EnumType::class, alias = "enum")
    @SetClazzModelOfAlias(alias = "schema", clazzProperty = "enums", referencedAlias = "enum")
    fun createNewEnumTypeInternal(
        @SetAsClazzModelId(alias = "enum")
        @SetAsValue(alias = "enum", clazzProperty = "enumId")
        enumId: EnumId,
        @InjectBuilder builder: EnumBuilder.() -> Unit
    )

    override fun enumType(enumId: EnumId, builder: EnumDsl.() -> Unit) {
        // cast from org.codeblessing.alpstein.codegen.builders.XyzBuilder to our XyzBuilder
        createNewEnumTypeInternal(enumId, builder)
    }


    // **************
    // UI Item
    // **************

    @BuilderMethod
    @NewClazzModel(clazz = UiItem::class, alias = "uiItem")
    @SetClazzModelOfAlias(alias = "schema", clazzProperty = "uiItems", referencedAlias = "uiItem")
    fun uiItemInternal(
        @SetAsValue(alias = "uiItem", clazzProperty = "itemId")
        itemId: ItemId,
        @InjectBuilder builder: UiItemBuilder.() -> Unit
    )

    override fun uiItem(itemId: ItemId, builder: UiItemDsl.() -> Unit) {
        uiItemInternal(itemId, builder)
    }


    // **************
    // DB Item
    // **************

    @BuilderMethod
    @NewClazzModel(clazz = DbItem::class, alias = "dbItem")
    @SetClazzModelOfAlias(alias = "schema", clazzProperty = "dbItems", referencedAlias = "dbItem")
    fun dbItemInternal(
        @SetAsValue(alias = "dbItem", clazzProperty = "itemId")
        itemId: ItemId,
        @InjectBuilder builder: DbItemBuilder.() -> Unit
    )

    override fun dbItem(itemId: ItemId, builder: DbItemDsl.() -> Unit) {
        dbItemInternal(itemId, builder)
    }


    // **************
    // DB Enum
    // **************

    @BuilderMethod
    @NewClazzModel(clazz = DbEnum::class, alias = "dbEnum")
    @SetClazzModelOfAlias(alias = "schema", clazzProperty = "dbEnums", referencedAlias = "dbEnum")
    fun dbEnumInternal(
        @SetAsValue(alias = "dbEnum", clazzProperty = "enumId")
        enumId: EnumId,
        @InjectBuilder builder: DbEnumBuilder.() -> Unit
    )

    override fun dbEnum(enumId: EnumId, builder: DbEnumDsl.() -> Unit) {
        dbEnumInternal(enumId, builder)
    }


    // **************
    // UI Entity
    // **************

    @BuilderMethod
    @NewClazzModel(clazz = UiEntity::class, alias = "uiEntity")
    @SetClazzModelOfAlias(alias = "schema", clazzProperty = "uiEntities", referencedAlias = "uiEntity")
    fun uiEntityInternal(
        @SetAsClazzModelId(alias = "uiEntity")
        @SetAsValue(alias = "uiEntity", clazzProperty = "uiEntityName")
        uiEntityName: String,
        @SetClazzModelOfId(alias = "uiEntity", clazzProperty = "rootItem")
        rootItemId: ItemId,
        @InjectBuilder builder: UiEntityBuilder.() -> Unit
    )

    override fun uiEntity(uiEntityName: String, rootItemId: ItemId, builder: UiEntityDsl.() -> Unit) {
        uiEntityInternal(uiEntityName, rootItemId, builder)
    }

}
