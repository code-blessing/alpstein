package org.codeblessing.alpstein.model.builders

import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.ExampleDataCategory
import org.codeblessing.alpstein.model.schema.ItemId
import org.codeblessing.alpstein.model.schema.PrimaryKeyType

@MainDslMarker
interface EnumDsl {
    fun enumValue(name: String)
}

@MainDslMarker
interface ItemDsl {
    /**
     * Declares an attribute of this item, for example
     * `attribute(name = "firstname").string().options(nullable = true)`.
     *
     * The attribute is declared in three steps: its name here, then its type
     * (see [ItemAttributeTypeDsl]) and finally the options that make sense for
     * that type (see [ItemAttributeOptionsDsl]).
     */
    fun attribute(name: String): ItemAttributeTypeDsl
}

/**
 * Declares the type of an attribute of an item, the second step of
 * [ItemDsl.attribute].
 *
 * Every type declaration returns the [ItemAttributeOptionsDsl] to declare the
 * options of the attribute, except [primaryKey] which allows no options at all.
 */
@MainDslMarker
interface ItemAttributeTypeDsl {
    /**
     * Declares this attribute as the one that identifies its item (like a primary key
     * in the database), for example `attribute(name = "contactId").primaryKey()` or
     * `attribute(name = "companyNumber").primaryKey(type = PrimaryKeyType.NUMBER)`.
     *
     * A primary key is of one of the [PrimaryKeyType] built-in types and never nullable
     * nor multiple, therefore it has no options. Only an item with a primary key can be
     * searched and referenced, and only for such an item the whole REST/service/persistence
     * stack is generated.
     */
    fun primaryKey(type: PrimaryKeyType = PrimaryKeyType.UUID)

    fun string(exampleDataCategory: ExampleDataCategory? = null): ItemAttributeOptionsDsl

    fun number(exampleDataCategory: ExampleDataCategory? = null): ItemAttributeOptionsDsl

    /**
     * Declares this attribute to hold a floating point number, for everything a whole
     * number cannot carry, e.g. an amount of money or a measurement.
     */
    fun double(exampleDataCategory: ExampleDataCategory? = null): ItemAttributeOptionsDsl

    fun boolean(exampleDataCategory: ExampleDataCategory? = null): ItemAttributeOptionsDsl

    fun uuid(exampleDataCategory: ExampleDataCategory? = null): ItemAttributeOptionsDsl

    /**
     * Declares this attribute to hold one of the values of the enumeration [enumId],
     * for example `attribute(name = "contactSalutation").enumType(enumId = EnumTypes.SALUTATION)`.
     */
    fun enumType(enumId: EnumId): ItemAttributeOptionsDsl

    /**
     * Declares this attribute to nest the instance of the item [itemId] itself,
     * for example `attribute(name = "homeAddress").nestedItem(itemId = Items.ADDRESS)`.
     *
     * In contrast to [reference], the nested item is part of this item and is therefore
     * stored, loaded and edited together with it.
     */
    fun nestedItem(itemId: ItemId): ItemAttributeOptionsDsl

    /**
     * Declares this attribute to reference the item [itemId], for example
     * `attribute(name = "myReferenceToAddress").reference(itemId = Items.ADDRESS)`.
     *
     * In contrast to [nestedItem] (which nests the item instance itself), such an
     * attribute stores only the identifying attribute of the referenced item and is
     * therefore of the same type as that attribute. The referenced item has to declare
     * a [primaryKey].
     */
    fun reference(itemId: ItemId): ItemAttributeOptionsDsl
}

/**
 * Declares the options of an attribute of an item, the last step of
 * [ItemDsl.attribute].
 *
 * Not declaring any options is the same as declaring all of them as `false`.
 */
@MainDslMarker
interface ItemAttributeOptionsDsl {
    fun options(
        nullable: Boolean = false,
        multiple: Boolean = false,
        customValidation: Boolean = false,
    )
}
