package org.codeblessing.alpstein.model.schema

/**
 * All types an [ItemAttribute]'s type can
 * be, have implemented this interface.
 */
sealed interface ItemAttributeType

/**
 * An [EnumId] is globally unique.
 */
interface EnumId : ItemAttributeType {
    val enumName: String
}

/**
 * An [EnumType] defines an enumeration
 * and all its values.
 */
data class EnumType(
    val enumId: EnumId,
    val enumValues: List<String>,
) {
    val enumName: String = enumId.enumName
}

enum class BuiltInType : ItemAttributeType {
    STRING,
    NUMBER,
    DOUBLE,
    BOOLEAN,
    UUID,
}

/**
 * The built-in types an item can be identified by (see [ItemAttribute.isPrimaryKey]).
 *
 * A [BuiltInType.BOOLEAN] is missing on purpose: it can only tell two items apart. So is a
 * [BuiltInType.DOUBLE]: two floating point values that differ only below their precision
 * compare as equal, so it identifies nothing reliably.
 *
 * A [BuiltInType.STRING] is missing because the persistence hands out every key — a database
 * column default for the PostgreSQL repository, a counter for the in-memory one — and there is
 * nothing to hand out for a textual key. It would have to be supplied by the caller instead.
 */
enum class PrimaryKeyType(val builtInType: BuiltInType) {
    UUID(BuiltInType.UUID),
    NUMBER(BuiltInType.NUMBER),
}

enum class ExampleDataCategory(val generatorPrefixName: String, val supportedBuiltInType: BuiltInType) {
    RANDOM_TEXT("RandomString", BuiltInType.STRING),
    RANDOM_NUMBER("RandomNumber",BuiltInType.NUMBER),
    RANDOM_DOUBLE("RandomDouble",BuiltInType.DOUBLE),
    RANDOM_BOOLEAN("RandomBoolean",BuiltInType.BOOLEAN),
    RANDOM_UUID("RandomUuid",BuiltInType.UUID),
    FIRSTNAME("FirstnameString",BuiltInType.STRING),
    LASTNAME("LastnameString",BuiltInType.STRING),
    AGE("AgeNumber",BuiltInType.NUMBER),
    AMOUNT("AmountDouble",BuiltInType.DOUBLE),
    STREET("StreetAndNumberString",BuiltInType.STRING),
    POSTCODE("PostcodeString",BuiltInType.STRING),
    CITY("CityString",BuiltInType.STRING),
    COUNTRY_ISO("CountryIsoCodeString",BuiltInType.STRING),
    COUNTRY_NAME("CountryNameString",BuiltInType.STRING),
    ;

    companion object {
        /** The category used for an attribute of [builtInType] that declares none itself. */
        fun randomDataOf(builtInType: BuiltInType): ExampleDataCategory = when (builtInType) {
            BuiltInType.STRING -> RANDOM_TEXT
            BuiltInType.NUMBER -> RANDOM_NUMBER
            BuiltInType.DOUBLE -> RANDOM_DOUBLE
            BuiltInType.BOOLEAN -> RANDOM_BOOLEAN
            BuiltInType.UUID -> RANDOM_UUID
        }
    }
}

/**
 * An [ItemId] is only used to wire the
 * different items together.
 */
interface ItemId : ItemAttributeType {
    val itemName: String
}

/**
 * An [Item] is a named list of attributes.
 *
 * An item with an attribute marked as [ItemAttribute.isPrimaryKey] can be identified by
 * that attribute (like a primary key in the database). Therefore, one can search for such
 * an item and it can be referenced from another [Item] (see [ItemAttribute.isReference]).
 *
 * An item without such an attribute has no identifier and can therefore not be
 * referenced by an identifier, only by a direct instance reference or a position
 * index (e.g. in a list).
 */
data class Item(
    val itemId: ItemId,
    val attributes: List<ItemAttribute>,
) {
    val itemName: String = itemId.itemName

    /**
     * The attribute of this item that identifies it (like a primary key in the
     * database), or `null` if this item has no identifier at all.
     * It is always of one of the [PrimaryKeyType] built-in types.
     */
    val idAttribute: ItemAttribute? = attributes.singleOrNull { it.isPrimaryKey }

    /** The name of the [idAttribute], or `null` if this item has no identifier at all. */
    val idAttributeName: String? = idAttribute?.attributeName

    /** Whether this item is identified by a primary key and can therefore be referenced and searched. */
    val hasPrimaryKey: Boolean = idAttribute != null
}

/**
 * A named field of an [Item] with a type,
 * e.g. to model a field like `myStrings: List<String>`.
 */
data class ItemAttribute(
    val attributeName: String,
    val isNullable: Boolean,
    val isMultiple: Boolean,
    val type: ItemAttributeType,
    val customValidation: Boolean = false,
    val exampleDataCategory: ExampleDataCategory?,
    /**
     * Whether this attribute identifies its [Item] (like a primary key in the database).
     *
     * Such an attribute is always of one of the [PrimaryKeyType] built-in types, neither
     * nullable nor multiple, and there is at most one of them per [Item]
     * (see [Item.idAttribute]).
     */
    val isPrimaryKey: Boolean = false,
    /**
     * Whether this attribute references another [Item] by its identifying attribute
     * instead of nesting the item instance itself.
     *
     * Both kinds of attribute carry an [ItemId] as their [type], this flag is what
     * distinguishes them: a reference stores only the primary key of the referenced
     * item, so the referenced item has to declare an [Item.idAttributeName] and the
     * reference is of the same built-in type as that primary key.
     */
    val isReference: Boolean = false,
)
