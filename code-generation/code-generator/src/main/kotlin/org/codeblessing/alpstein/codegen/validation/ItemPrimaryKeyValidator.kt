package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.PrimaryKeyType
import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * An item is identified by at most one attribute, and that attribute is a single
 * mandatory attribute of a [PrimaryKeyType] built-in type (like a primary key in the
 * database).
 */
class ItemPrimaryKeyValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.items.forEachIndexed { itemIndex, item ->
            val itemPath = path.child("items", itemIndex, item.itemName)
            val primaryKeyAttributes = item.attributes.withIndex().filter { (_, attribute) -> attribute.isPrimaryKey }

            if (primaryKeyAttributes.size > 1) {
                val declarations = primaryKeyAttributes
                    .joinToString { (index, attribute) -> "attributes[$index] '${attribute.attributeName}'" }
                validationError(
                    itemPath,
                    "The item declares ${primaryKeyAttributes.size} attributes as its primary key, namely " +
                            "$declarations. An item is identified by one single attribute.",
                )
            }

            val (primaryKeyIndex, primaryKey) = primaryKeyAttributes.singleOrNull() ?: return@forEachIndexed
            val primaryKeyPath = itemPath.child("attributes", primaryKeyIndex, primaryKey.attributeName)

            if (PrimaryKeyType.entries.none { it.builtInType == primaryKey.type }) {
                val supportedTypes = PrimaryKeyType.entries.joinToString { it.builtInType.name }
                validationError(
                    primaryKeyPath,
                    "The attribute identifies its item, but it is of ${primaryKey.type.description}. " +
                            "An item is only identified by an attribute of one of the built-in " +
                            "types $supportedTypes.",
                )
            }
            if (primaryKey.isNullable || primaryKey.isMultiple) {
                validationError(
                    primaryKeyPath,
                    "The attribute identifies its item, but it is declared as " +
                            "nullable=${primaryKey.isNullable} and multiple=${primaryKey.isMultiple}. " +
                            "An item is only identified by a single mandatory attribute.",
                )
            }
        }
    }
}
