package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.SchemaData
import org.codeblessing.alpstein.model.schema.UiEntity
import org.codeblessing.alpstein.model.schema.UiEntityEditorColumn
import org.codeblessing.alpstein.model.schema.UiEntityEditorEntityNestedItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorItemConfiguration
import org.codeblessing.alpstein.model.schema.UiEntityEditorRootItemConfiguration
import org.codeblessing.alpstein.model.schema.UiItem
import org.codeblessing.alpstein.model.schema.UiItemAttributeBlock

/**
 * An attribute block of an editor shows only an attribute of the item it is configured
 * for, which is the root item of the ui entity, the nested item of the configuration the
 * block belongs to, or the item declaring the default nested item editor.
 */
class AttributeBlockAttributeExistenceValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        validateDefaultNestedItemEditors(schemaData, path)
        validateEditorViews(schemaData, path)
    }

    private fun validateDefaultNestedItemEditors(schemaData: SchemaData, path: ValidationPath) {
        schemaData.uiItems.forEachIndexed { uiItemIndex, uiItem ->
            val uiItemPath = path.child("uiItems", uiItemIndex, uiItem.itemId.itemName)

            validateColumns(
                columns = uiItem.defaultNestedItemEditor,
                configuredItem = uiItem.configuredItem(schemaData, uiItemPath),
                path = uiItemPath.child("defaultNestedItemEditor"),
            )
        }
    }

    private fun validateEditorViews(schemaData: SchemaData, path: ValidationPath) {
        schemaData.uiEntities.forEachIndexed { entityIndex, uiEntity ->
            val editorViewPath = path
                .child("uiEntities", entityIndex, uiEntity.uiEntityName)
                .child("editorView")

            uiEntity.editorView.itemConfiguration.forEachIndexed { configurationIndex, configuration ->
                val configurationPath = editorViewPath.child(
                    property = "itemConfiguration",
                    index = configurationIndex,
                    name = configuration.configuredItemName(uiEntity),
                )
                val configuredItem = configuration.configuredItem(uiEntity, schemaData, configurationPath)

                validateColumns(configuration.noTab, configuredItem, configurationPath.child("noTab"))

                if (configuration is UiEntityEditorRootItemConfiguration) {
                    configuration.tabs.forEachIndexed { tabIndex, tab ->
                        validateColumns(
                            columns = tab.columns,
                            configuredItem = configuredItem,
                            path = configurationPath.child("tabs", tabIndex, tab.tabTranslationKey),
                        )
                    }
                }
            }
        }
    }

    private fun UiItem.configuredItem(schemaData: SchemaData, path: ValidationPath): Item =
        schemaData.items.firstOrNull { it.itemId == itemId }
            ?: validationError(
                path,
                "There is a ui configuration for the item '${itemId.itemName}', but no such item is " +
                        "declared in the schema. Available are ${schemaData.items.map { it.itemName }}.",
            )

    private fun UiEntityEditorItemConfiguration.configuredItemName(uiEntity: UiEntity): String = when (this) {
        is UiEntityEditorRootItemConfiguration -> uiEntity.rootItem.itemName
        is UiEntityEditorEntityNestedItemConfiguration -> itemId.itemName
    }

    private fun UiEntityEditorItemConfiguration.configuredItem(
        uiEntity: UiEntity,
        schemaData: SchemaData,
        path: ValidationPath,
    ): Item = when (this) {
        is UiEntityEditorRootItemConfiguration -> uiEntity.rootItem
        is UiEntityEditorEntityNestedItemConfiguration -> schemaData.items.firstOrNull { it.itemId == itemId }
            ?: validationError(
                path,
                "The editor is configured for the nested item '${itemId.itemName}', but no such item is " +
                        "declared in the schema. Available are ${schemaData.items.map { it.itemName }}.",
            )
    }

    private fun validateColumns(columns: List<UiEntityEditorColumn>, configuredItem: Item, path: ValidationPath) {
        columns.forEachIndexed { columnIndex, column ->
            val columnPath = path.child("columns", columnIndex)
            column.blocks.forEachIndexed { blockIndex, block ->
                if (block !is UiItemAttributeBlock) return@forEachIndexed
                if (configuredItem.attributes.none { it.attributeName == block.attributeName }) {
                    validationError(
                        columnPath.child("blocks", blockIndex, block.attributeName),
                        "The attribute block shows the attribute '${block.attributeName}', but the item " +
                                "'${configuredItem.itemName}' it is configured for has no such attribute. " +
                                "Available are ${configuredItem.attributes.map { it.attributeName }}.",
                    )
                }
            }
        }
    }
}
