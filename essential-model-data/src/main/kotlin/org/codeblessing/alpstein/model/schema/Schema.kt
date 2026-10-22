package org.codeblessing.alpstein.model.schema

data class SchemaData(
    val items: List<Item>,
    val enums: List<EnumType>,
    val uiItems: List<UiItem>,
    val uiEntities: List<UiEntity>,
    val dbItems: List<DbItem>,
    val dbEnums: List<DbEnum>,
)
