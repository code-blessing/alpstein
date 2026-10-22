package org.codeblessing.alpstein.codegen.validation

import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.ItemAttributeType
import org.codeblessing.alpstein.model.schema.ItemId

/** How the type of an item attribute is named in a validation message. */
internal val ItemAttributeType.description: String
    get() = when (this) {
        is BuiltInType -> "the built-in type $name"
        is EnumId -> "the enum type '$enumName'"
        is ItemId -> "the item '$itemName'"
    }
