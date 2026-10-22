package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.SchemaData

/**
 * A database configuration exists only for an item of the schema that declares a primary key.
 *
 * An item without one is nested into the row of the item holding it instead of being stored
 * in a table of its own, therefore there is nothing to configure for it.
 */
class DbItemItemExistenceValidator : SchemaDataAspectValidator {
    override fun validate(schemaData: SchemaData, path: ValidationPath) {
        schemaData.dbItems.forEachIndexed { dbItemIndex, dbItem ->
            val dbItemPath = path.child("dbItems", dbItemIndex, dbItem.itemId.itemName)
            val item = schemaData.items.firstOrNull { it.itemId == dbItem.itemId }
                ?: validationError(
                    dbItemPath,
                    "There is a database configuration for the item '${dbItem.itemId.itemName}', but no such " +
                            "item is declared in the schema. Available are ${schemaData.items.map { it.itemName }}.",
                )

            if (!item.hasPrimaryKey) {
                validationError(
                    dbItemPath,
                    "There is a database configuration for the item '${item.itemName}', but it declares no " +
                            "primary key and is therefore stored as part of the item nesting it instead of in " +
                            "a table of its own.",
                )
            }
        }
    }
}
