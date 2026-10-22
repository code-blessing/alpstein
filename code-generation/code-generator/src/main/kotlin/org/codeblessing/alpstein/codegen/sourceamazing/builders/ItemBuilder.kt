package org.codeblessing.alpstein.codegen.sourceamazing.builders

import org.codeblessing.sourceamazing.builder.api.annotations.*
import org.codeblessing.alpstein.model.builders.ItemDsl
import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.ItemAttribute

@Builder
@ExpectedClazzModelFromSuperiorBuilder(clazz = Item::class, alias = "item")
interface ItemBuilder: ItemDsl {

    /**
     * Creates the attribute with its name only. The type is declared on the returned
     * [ItemAttributeTypeBuilder], the options on the [ItemAttributeOptionsBuilder]
     * returned by that one.
     *
     * All flags are preset to `false` here, so that an attribute without options
     * is complete already. The subsequent builders replace them where needed.
     */
    @BuilderMethod
    @NewClazzModel(clazz = ItemAttribute::class, alias = "itemAttribute")
    @SetClazzModelOfAlias(alias = "item", clazzProperty = "attributes", referencedAlias = "itemAttribute")
    @SetFixedBooleanValue(alias = "itemAttribute", clazzProperty = "isPrimaryKey", value = false)
    @SetFixedBooleanValue(alias = "itemAttribute", clazzProperty = "isReference", value = false)
    @SetFixedBooleanValue(alias = "itemAttribute", clazzProperty = "isNullable", value = false)
    @SetFixedBooleanValue(alias = "itemAttribute", clazzProperty = "isMultiple", value = false)
    @SetFixedBooleanValue(alias = "itemAttribute", clazzProperty = "customValidation", value = false)
    override fun attribute(
        @SetAsValue(alias = "itemAttribute", clazzProperty = "attributeName")
        name: String,
    ): ItemAttributeTypeBuilder
}
