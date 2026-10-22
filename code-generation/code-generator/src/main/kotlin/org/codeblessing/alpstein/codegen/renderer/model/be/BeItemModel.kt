package org.codeblessing.alpstein.codegen.renderer.model.be

import org.codeblessing.alpstein.codegen.renderer.NotSupportedInTemplateException
import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.codegen.renderer.model.db.DbTableModel
import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.ItemId

data class BeItemModel(
    val itemDescription: BeItemDescriptionModel,
    val attributes: List<BeAttributeModel>,
    /**
     * The attribute that identifies this item (its primary key), or `null` if this item
     * has none. It is always a plain built-in attribute, never a reference to another item.
     */
    val idAttribute: BuiltInTypeBeAttributeModel?,
    /** The table this item is stored in, or `null` if it has no primary key and is therefore nested. */
    val dbTable: DbTableModel?,
) : Comparable<BeItemModel> {
    val itemId: ItemId = itemDescription.itemId
    val itemName: NameCase = itemDescription.itemName

    /** Whether this item is identified by a primary key and can therefore be addressed, searched and referenced. */
    val hasPrimaryKey: Boolean = idAttribute != null

    /**
     * The primary key, for the templates that are only rendered for an item that has one
     * (controller, service, repository, search, by-ids, example data initializer).
     */
    val primaryKeyAttribute: BuiltInTypeBeAttributeModel
        get() = requireNotNull(idAttribute) {
            "The item '${itemName.pascalCase}' declares no primary key."
        }

    /**
     * The table this item is stored in, for the templates that are only rendered for an item
     * that has a primary key.
     */
    val table: DbTableModel
        get() = requireNotNull(dbTable) {
            "The item '${itemName.pascalCase}' is stored in no table, because it declares no primary key."
        }

    /** Whether the primary key is a [BuiltInType.UUID], e.g. to render the import of the UUID type. */
    val hasUuidPrimaryKey: Boolean = idAttribute?.builtInType == BuiltInType.UUID

    /**
     * The Kotlin expression that hands out the next unused primary key, rendered into the
     * `create()` of the in-memory repository, the only place that assigns one — the PostgreSQL
     * repository leaves the key to the default of its column.
     *
     * A NUMBER key counts up and therefore reads the `store` map the in-memory repository
     * holds its entries in.
     */
    val nextPrimaryKeyValueExpression: String
        get() = when (primaryKeyAttribute.builtInType) {
            BuiltInType.UUID -> "UUID.randomUUID()"
            BuiltInType.NUMBER -> "(store.keys.maxOrNull() ?: 0) + 1"
            BuiltInType.STRING, BuiltInType.DOUBLE, BuiltInType.BOOLEAN ->
                throw NotSupportedInTemplateException(unsupportedPrimaryKeyTypeMessage())
        }

    /**
     * The Kotlin expression for a primary key no item is ever identified by, rendered
     * wherever a reference has to be created although there is nothing to reference yet.
     */
    val unresolvablePrimaryKeyValueExpression: String
        get() = when (primaryKeyAttribute.builtInType) {
            BuiltInType.UUID -> "UUID(0L, 0L)"
            BuiltInType.NUMBER -> "0"
            BuiltInType.STRING, BuiltInType.DOUBLE, BuiltInType.BOOLEAN ->
                throw NotSupportedInTemplateException(unsupportedPrimaryKeyTypeMessage())
        }

    private fun unsupportedPrimaryKeyTypeMessage(): String =
        "The item '${itemName.pascalCase}' is identified by the attribute " +
                "'${primaryKeyAttribute.attributeName.camelCase}' of the built-in type " +
                "${primaryKeyAttribute.builtInType}, which no item can be identified by."

    val usedEnums: List<BeEnumModel> = attributes
        .filterIsInstance<EnumBeAttributeModel>()
        .map { it.enum }
        .distinct()

    val attributesWithItemType: List<ItemBeIAttributeModel> = attributes
        .filterIsInstance<ItemBeIAttributeModel>()

    val attributesWithEnumType: List<EnumBeAttributeModel> = attributes
        .filterIsInstance<EnumBeAttributeModel>()

    val builtInAttributes: List<BuiltInTypeBeAttributeModel> = attributes
        .filterIsInstance<BuiltInTypeBeAttributeModel>()

    /**
     * All attributes referencing another item. They are a subset of [builtInAttributes],
     * as a reference is transported as the primary key of the referenced item.
     */
    val attributesWithItemReference: List<ItemReferenceBeAttributeModel> = attributes
        .filterIsInstance<ItemReferenceBeAttributeModel>()

    /** All items referenced by an attribute of this item, e.g. to inject one example data fetcher per item. */
    val referencedItems: List<BeReferencedItemModel> = attributesWithItemReference
        .map { it.referencedItem }
        .distinct()

    val builtInTypeAndEnumAttributes: List<BeAttributeModel> = attributes
        .filter { it.isBuiltIn || it.isEnum }

    val directlyNestedItems: List<BeItemDescriptionModel> = attributes
        .filterIsInstance<ItemBeIAttributeModel>()
        .map { it.referencedItem }
        .distinct()

    /**
     * All items this item depends on, i.e. the items it references by their primary key and the
     * items it nests as an attribute. Both have to exist before this item can be created.
     *
     * An item referencing itself is left out: that dependency can never be satisfied by an order.
     */
    val dependencyItemIds: Set<ItemId> =
        (referencedItems.map { it.itemId } + directlyNestedItems.map { it.itemId }).toSet() - itemId

    fun dependsOn(other: BeItemModel): Boolean = other.itemId in dependencyItemIds

    /**
     * Orders an item after every item it directly depends on, and orders two items that do not
     * depend on each other by their name, so that the same schema always renders the same order.
     *
     * This only sees the direct dependencies of the two compared items, which makes it too weak
     * to sort a whole list with: it is not transitive, as an item depending on a second one that
     * depends on a third says nothing about the first and the third. Use [sortedByDependencies]
     * to order a list.
     */
    override fun compareTo(other: BeItemModel): Int = when {
        this === other -> 0
        dependsOn(other) -> 1
        other.dependsOn(this) -> -1
        else -> itemName.pascalCase.compareTo(other.itemName.pascalCase)
    }

    /**
     * Item references are left out: their example data is not generated but fetched from the
     * already existing instances of the referenced item.
     */
    val exampleDataGeneratorConfigs: List<BeExampleDataGeneratorConfig> = attributes
        .filterIsInstance<BuiltInTypeBeAttributeModel>()
        .filter { !it.isItemReference }
        .map { it.exampleDataGeneratorConfig }
        .distinctBy { it.fullQualifiedName }

    /**
     * UUIDs are edited with the very same text input as strings, therefore they count
     * as text attributes for everything that is about rendering an input field.
     */
    val containsTextAttributes: Boolean = attributesOfTypes(TEXT_LIKE_BUILT_IN_TYPES, isList = false).any()
    val containsBooleanAttributes: Boolean = attributesOfType(BuiltInType.BOOLEAN, isList = false).any()
    val containsNumberAttributes: Boolean = attributesOfTypes(NUMBER_LIKE_BUILT_IN_TYPES, isList = false).any()

    val containsTextListAttributes: Boolean = attributesOfTypes(TEXT_LIKE_BUILT_IN_TYPES, isList = true).any()
    val containsBooleanListAttributes: Boolean = attributesOfType(BuiltInType.BOOLEAN, isList = true).any()
    val containsNumberListAttributes: Boolean = attributesOfTypes(NUMBER_LIKE_BUILT_IN_TYPES, isList = true).any()

    /**
     * Whether any attribute is a [BuiltInType.UUID], e.g. to render the import of the UUID
     * type. A reference counts here if the item it references is identified by a UUID.
     */
    val containsUuidAttributes: Boolean = attributesOfTypes(setOf(BuiltInType.UUID)).any()

    private fun attributesOfType(filterBuiltInType: BuiltInType, isList: Boolean): List<BeAttributeModel> =
        attributesOfTypes(setOf(filterBuiltInType), isList)

    /** All built-in attributes of one of the [filterBuiltInTypes], of any cardinality if [isList] is `null`. */
    private fun attributesOfTypes(filterBuiltInTypes: Set<BuiltInType>, isList: Boolean? = null): List<BeAttributeModel> {
        return attributes
            .filterIsInstance<BuiltInTypeBeAttributeModel>()
            .filter { it.builtInType in filterBuiltInTypes && (isList == null || it.isList == isList) }
    }

    companion object {
        /** Built-in types that are rendered as a text input on the client. */
        private val TEXT_LIKE_BUILT_IN_TYPES = setOf(BuiltInType.STRING, BuiltInType.UUID)

        /** Built-in types that are rendered as a number input on the client. */
        private val NUMBER_LIKE_BUILT_IN_TYPES = setOf(BuiltInType.NUMBER, BuiltInType.DOUBLE)

        /**
         * The [items] ordered so that every item follows all items it depends on
         * (see [dependencyItemIds]).
         *
         * Every item is measured by the longest dependency chain below it, because [compareTo]
         * is not transitive enough to sort by on its own. Items of the same chain length never
         * depend on each other, so the natural order merely breaks their tie by name.
         */
        fun sortedByDependencies(items: List<BeItemModel>): List<BeItemModel> {
            val itemsById = items.associateBy { it.itemId }
            val chainLengths = mutableMapOf<ItemId, Int>()
            items.forEach { dependencyChainLength(it, itemsById, chainLengths, mutableSetOf()) }
            return items.sortedWith(compareBy<BeItemModel> { chainLengths.getValue(it.itemId) }.then(naturalOrder()))
        }

        /**
         * An item reached again while its own chain is still being measured closes a dependency
         * cycle, which no order can satisfy. The edge closing it is ignored so that all other
         * items still end up behind their dependencies.
         */
        private fun dependencyChainLength(
            item: BeItemModel,
            itemsById: Map<ItemId, BeItemModel>,
            chainLengths: MutableMap<ItemId, Int>,
            itemsBeingMeasured: MutableSet<ItemId>,
        ): Int {
            chainLengths[item.itemId]?.let { return it }
            if (!itemsBeingMeasured.add(item.itemId)) return 0

            val chainLength = item.dependencyItemIds
                .mapNotNull { itemsById[it] }
                .maxOfOrNull { dependencyChainLength(it, itemsById, chainLengths, itemsBeingMeasured) + 1 }
                ?: 0

            itemsBeingMeasured.remove(item.itemId)
            chainLengths[item.itemId] = chainLength
            return chainLength
        }
    }
}
