package org.codeblessing.alpstein.codegen.renderer.model.be

import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.codegen.renderer.model.db.DbColumnModel
import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.EnumId
import org.codeblessing.alpstein.model.schema.ExampleDataCategory

sealed class BeAttributeModel(
    val item: BeItemDescriptionModel,
    val attributeName: NameCase,
    val isNullable: Boolean,
    val isList: Boolean,
    val dbColumn: DbColumnModel?,
) {
    abstract val isItem: Boolean
    abstract val isBuiltIn: Boolean
    abstract val isEnum: Boolean

    /**
     * Whether this attribute references another item by its primary key.
     * Such an attribute is of the built-in type of that primary key in every layer,
     * therefore it is also a built-in attribute ([isBuiltIn] is `true` as well).
     */
    open val isItemReference: Boolean
        get() = false

    val mermaidItemRelationship: String
        get() = "--${if(isNullable) "o" else "|"}${if (isList) "|" else "{"}"

    val mermaidItemTypeDefinition: String
        get() = kotlinAttributeTypeAsString()

    fun mermaidItemAdditionalInformation(idAttribute: BuiltInTypeBeAttributeModel?): String {
        val tokens = mutableListOf<String>()
        if(idAttribute?.attributeName == this.attributeName) {
            tokens.add("PK")
        }
        if(isItemReference) {
            tokens.add("FK")
        }
        if(isNullable) {
            tokens.add("nullable")
        }
        if(isList) {
            tokens.add("array")
        }
        return tokens.joinToString(",")
    }

    /**
     * The column this attribute is stored in, for the templates that are only rendered for an
     * item that has a table, i.e. one that declares a primary key.
     */
    val column: DbColumnModel
        get() = requireNotNull(dbColumn) {
            "The attribute '${attributeName.camelCase}' of the item " +
                    "'${item.itemName.pascalCase}' is stored in no column."
        }

    /**
     * The example-data creator call to obtain a value for this attribute, respecting its cardinality:
     * `createList()` for list attributes, `create()` otherwise.
     */
    open val exampleDataCreatorCall: String
        get() = if (isList) "createList(dataContext)" else "create(dataContext)"

    val typescriptAttributeType: String
        get() = calculateAttributeTypeWithCardinality()

    /**
     * The Kotlin type of this attribute as used in the business objects, respecting its cardinality,
     * e.g. `String`, `Int?`, `List<ArticulusInteriorBO>` or `List<AppellatioComis>?`.
     */
    val kotlinAttributeType: String
        get() = withKotlinCardinality(kotlinAttributeTypeAsString())

    /**
     * The Kotlin type of this attribute as used in the WTOs (transport layer), respecting its
     * cardinality, e.g. `String`, `Int?`, `List<ArticulusInteriorWTO>` or `List<AppellatioComisEnum>?`.
     */
    val wtoAttributeType: String
        get() = withKotlinCardinality(wtoAttributeTypeAsString())

    /**
     * The suffix that maps this attribute's WTO value to its BO value, respecting cardinality and
     * nullability: e.g. `` (built-ins, identity), `.toBo()`, `?.toBo()`, `.map { it.toBo() }` or
     * `?.map { it.toBo() }`.
     */
    val toBoMappingSuffix: String
        get() = mappingSuffix("toBo")

    /** The counterpart of [toBoMappingSuffix] mapping a BO value to its WTO value (`toWto`). */
    val toWtoMappingSuffix: String
        get() = mappingSuffix("toWto")

    protected abstract fun attributeTypeAsString(): String

    protected abstract fun kotlinAttributeTypeAsString(): String

    protected abstract fun wtoAttributeTypeAsString(): String


    private fun mappingSuffix(conversion: String): String = when {
        isBuiltIn -> "" // built-in types are transported as-is, no conversion needed
        !isList && !isNullable -> ".$conversion()"
        !isList && isNullable -> "?.$conversion()"
        isList && !isNullable -> ".map { it.$conversion() }"
        else -> "?.map { it.$conversion() }"
    }

    private fun calculateAttributeTypeWithCardinality(): String {
        val type = attributeTypeAsString()
        return when {
            !isList && isNullable -> "$type | null"
            !isList && !isNullable -> type
            isList && !isNullable -> "Array<$type>"
            else -> "Array<$type> | null"
        }
    }

    private fun withKotlinCardinality(type: String): String = when {
        !isList && isNullable -> "$type?"
        !isList && !isNullable -> type
        isList && !isNullable -> "List<$type>"
        else -> "List<$type>?"
    }
}


open class BuiltInTypeBeAttributeModel(
    item: BeItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    dbColumn: DbColumnModel?,
    val builtInType: BuiltInType,
    val exampleDataGeneratorConfig: BeExampleDataGeneratorConfig
) : BeAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    dbColumn = dbColumn,
) {
    override val isItem: Boolean
        get() = false
    override val isBuiltIn: Boolean
        get() = true
    override val isEnum: Boolean
        get() = false

    override fun attributeTypeAsString(): String {
        return builtInTypeAsString()
    }

    override fun kotlinAttributeTypeAsString(): String =
        when (builtInType) {
            BuiltInType.STRING -> "String"
            BuiltInType.NUMBER -> "Int"
            BuiltInType.DOUBLE -> "Double"
            BuiltInType.BOOLEAN -> "Boolean"
            BuiltInType.UUID -> "UUID"
        }

    // built-in types are identical in the BO and WTO layers
    override fun wtoAttributeTypeAsString(): String = kotlinAttributeTypeAsString()

    override val exampleDataCreatorCall: String = exampleDataGeneratorConfig.exampleDataGeneratorCallExpression


    private fun builtInTypeAsString(): String =
        when (builtInType) {
            BuiltInType.STRING -> "string"
            BuiltInType.NUMBER, BuiltInType.DOUBLE -> "number"
            BuiltInType.BOOLEAN -> "boolean"
            BuiltInType.UUID -> "UUID"
        }
}

/**
 * An attribute that references another item by its primary key.
 *
 * The reference stores exactly that primary key, therefore this attribute behaves like a
 * built-in attribute of the primary key's type in the business objects and in the transport
 * layer. What distinguishes it is [referencedItem]: it tells the templates which item has to
 * be searched and resolved to display the reference by its identifying attributes instead of
 * displaying the raw primary key.
 */
class ItemReferenceBeAttributeModel(
    item: BeItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    dbColumn: DbColumnModel?,
    val referencedItem: BeReferencedItemModel,
) : BuiltInTypeBeAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    dbColumn = dbColumn,
    builtInType = referencedItem.idBuiltInType,
    // A random value of the primary key's type for now; resolving it to a really existing
    // item is up to the templates that know how to fetch the referenced items.
    exampleDataGeneratorConfig = randomKeyExampleDataGeneratorConfig(
        builtInType = referencedItem.idBuiltInType,
        isNullable = isNullable,
        isList = isList,
    ),
) {
    override val isItemReference: Boolean
        get() = true

    private companion object {
        private fun randomKeyExampleDataGeneratorConfig(
            builtInType: BuiltInType,
            isNullable: Boolean,
            isList: Boolean,
        ) = BeExampleDataGeneratorConfig(
            generatorNamePrefix = NameCase(ExampleDataCategory.randomDataOf(builtInType).generatorPrefixName),
            isNullable = isNullable,
            numberOfEntries = if (isList) 3 else 1,
        )
    }
}

class ItemBeIAttributeModel(
    item: BeItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    dbColumn: DbColumnModel?,
    val referencedItem: BeItemDescriptionModel,
) : BeAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    dbColumn = dbColumn,
) {
    override val isItem: Boolean
        get() = true
    override val isBuiltIn: Boolean
        get() = false
    override val isEnum: Boolean
        get() = false

    override fun attributeTypeAsString(): String {
        return "${referencedItemTypeAsString()}WTO"
    }

    override fun kotlinAttributeTypeAsString(): String = "${referencedItemTypeAsString()}BO"

    override fun wtoAttributeTypeAsString(): String = "${referencedItemTypeAsString()}WTO"

    private fun referencedItemTypeAsString(): String = this.referencedItem.itemName.pascalCase
}

class EnumBeAttributeModel(
    item: BeItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    dbColumn: DbColumnModel?,
    val enum: BeEnumModel,
) : BeAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    dbColumn = dbColumn,
) {
    val enumId: EnumId = enum.enumId

    override val isItem: Boolean
        get() = false
    override val isBuiltIn: Boolean
        get() = false
    override val isEnum: Boolean
        get() = true

    override fun attributeTypeAsString(): String {
        return enumTypeAsString()
    }

    override fun kotlinAttributeTypeAsString(): String = enumTypeAsString()

    override fun wtoAttributeTypeAsString(): String = "${enumTypeAsString()}Enum"

    private fun enumTypeAsString(): String = this.enum.enumName.pascalCase

}
