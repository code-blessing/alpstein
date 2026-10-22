package org.codeblessing.alpstein.codegen.renderer.model.ui

import org.codeblessing.alpstein.codegen.renderer.model.NIL_UUID
import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.model.schema.BuiltInType
import org.codeblessing.alpstein.model.schema.EnumId

/** The TypeScript type one value of [builtInType] is transported and edited as. */
fun typescriptBuiltInType(builtInType: BuiltInType): String = when (builtInType) {
    BuiltInType.STRING -> "string"
    BuiltInType.NUMBER, BuiltInType.DOUBLE -> "number"
    BuiltInType.BOOLEAN -> "boolean"
    BuiltInType.UUID -> "UUID"
}

sealed class UiAttributeModel(
    val item: UiItemDescriptionModel,
    val attributeName: NameCase,
    val isNullable: Boolean,
    val isList: Boolean,
    val hasCustomValidation: Boolean,
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

    /**
     * Whether this attribute is the primary key of its item. Only a plain built-in attribute
     * can be one, therefore every other kind of attribute answers `false`.
     */
    open val isPrimaryKey: Boolean
        get() = false

    val typescriptAttributeType: String
        get() = calculateAttributeTypeWithCardinality()

    /**
     * The three form-type accessors take the name of the UiEntity whose editor is being
     * rendered, because a nested item's form part is generated once per UiEntity and its
     * `FormPartGroup` type is therefore prefixed with that name. The attribute itself is
     * UiEntity-agnostic: an item can appear in the editor of several UiEntities.
     */
    fun angularInitialValueFormType(uiEntityName: NameCase): String =
        calculateAngularInitialValueFormType(uiEntityName)

    fun angularFormControlType(uiEntityName: NameCase): String =
        calculateAngularFormControlType(uiEntityName, withCollection = false)

    fun angularFormControlTypeWithCollection(uiEntityName: NameCase): String =
        calculateAngularFormControlType(uiEntityName, withCollection = true)

    val angularFormInitialValue: String
        get() = determineAngularFormInitialValue()

    protected abstract fun attributeTypeAsString(): String


    private fun calculateAttributeTypeWithCardinality(): String {
        val type = attributeTypeAsString()
        return when {
            !isList && isNullable -> "$type | null"
            !isList && !isNullable -> type
            isList && !isNullable -> "Array<$type>"
            else -> "Array<$type> | null"
        }
    }

    /**
     * Something like:
     * - `string`
     * - `string | null`
     * - `AppellatioEnum | null`
     * - `Array<FormGroup<ArticulusInteriorFormPartGroup>>`
     * Form values are always `null`based, not `undefined`.
     */
    private fun calculateAngularInitialValueFormType(uiEntityName: NameCase): String {
        val singleType = calculateAngularInitialValueFormSingleType(uiEntityName)

        // Built-in type and enum lists store their elements directly in a `FormArray<FormControl<...>>`.
        // The initial value provided by the form-part service is the value of a single element
        // (e.g. `string` or an enum value), not the whole array (which is created empty). Only item
        // lists need the `Array<...>` wrapping, because their FormArray is seeded from the initial value service.
        return if (isList && isItem) {
            "Array<$singleType>"
        } else {
            singleType
        }
    }

    protected abstract fun calculateAngularInitialValueFormSingleType(uiEntityName: NameCase): String


    /**
     * Something like:
     * - `FormControl<string>`
     * - `FormControl<string | null>`
     * - `FormControl<AppellatioEnum>`
     * - `FormGroup<ArticulusInteriorFormPartGroup>`
     * - `FormArray<FormGroup<ArticulusInteriorFormPartGroup>>`
     * Form values are always `null`based, not `undefined`.
     */
    private fun calculateAngularFormControlType(uiEntityName: NameCase, withCollection: Boolean): String {
        val singleFormType = calculateAngularFormControlSingleType(uiEntityName)

        return if (isList && withCollection) {
            "FormArray<$singleFormType>"
        } else {
            singleFormType
        }
    }

    protected abstract fun calculateAngularFormControlSingleType(uiEntityName: NameCase): String

    protected abstract fun determineAngularFormInitialValue(): String
}


open class BuiltInTypeUiAttributeModel(
    item: UiItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    customValidation: Boolean,
    val builtInType: BuiltInType,
    override val isPrimaryKey: Boolean = false,
) : UiAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    hasCustomValidation = customValidation,
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

    private fun builtInTypeAsString(): String = typescriptBuiltInType(builtInType)


    override fun calculateAngularInitialValueFormSingleType(uiEntityName: NameCase): String {
        return typescriptBuiltInType(builtInType)
    }

    /**
     * Something like:
     * - `FormControl<string>`
     * - `FormControl<string | null>`
     * - `FormControl<AppellatioEnum>`
     * - `FormGroup<ArticulusInteriorFormPartGroup>`
     * - `FormArray<FormGroup<ArticulusInteriorFormPartGroup>>`
     * Form values are always `null`based, not `undefined`.
     */
    override fun calculateAngularFormControlSingleType(uiEntityName: NameCase): String {
        return "FormControl<${typescriptBuiltInType(builtInType)}>"
    }

    override fun determineAngularFormInitialValue(): String {
        return when (builtInType) {
            BuiltInType.STRING -> "''"
            BuiltInType.NUMBER, BuiltInType.DOUBLE -> "0"
            BuiltInType.BOOLEAN -> "false"
            BuiltInType.UUID -> "'$NIL_UUID'"
        }
    }

}

/**
 * An attribute that references another item by its primary key.
 *
 * The reference stores exactly that primary key, therefore this attribute behaves like a
 * built-in attribute of the primary key's type in the form and in the transport layer. What
 * distinguishes it is [referencedItem]: it tells the templates which item has to be
 * searched and resolved to display the reference by its display attributes instead of
 * displaying the raw primary key.
 */
class ItemReferenceUiAttributeModel(
    item: UiItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    customValidation: Boolean,
    val referencedItem: UiReferencedItemModel,
) : BuiltInTypeUiAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    customValidation = customValidation,
    builtInType = referencedItem.idBuiltInType,
) {
    override val isItemReference: Boolean
        get() = true

    /**
     * A single reference holds `null` as long as nothing is picked, so that the required
     * validator can complain instead of an unassigned primary key being sent to the backend.
     * Inside a list every entry is a picked reference, therefore the entries themselves are
     * never null.
     */
    override fun calculateAngularFormControlSingleType(uiEntityName: NameCase): String {
        val referencedKeyType = attributeTypeAsString()
        return if (isList) "FormControl<$referencedKeyType>" else "FormControl<$referencedKeyType | null>"
    }
}

class ItemUiIAttributeModel(
    item: UiItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    customValidation: Boolean,
    val referencedItem: UiItemDescriptionModel,
) : UiAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    hasCustomValidation = customValidation,
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

    private fun referencedItemTypeAsString(): String = this.referencedItem.itemName.pascalCase


    override fun calculateAngularInitialValueFormSingleType(uiEntityName: NameCase): String {
        return "FormGroup<${uiEntityAndReferencedItemTypeAsString(uiEntityName)}FormPartGroup>"
    }

    /**
     * Something like:
     * - `FormControl<string>`
     * - `FormControl<string | null>`
     * - `FormControl<AppellatioEnum>`
     * - `FormGroup<ArticulusInteriorFormPartGroup>`
     * - `FormArray<FormGroup<ArticulusInteriorFormPartGroup>>`
     * Form values are always `null`based, not `undefined`.
     */
    override fun calculateAngularFormControlSingleType(uiEntityName: NameCase): String {
        return "FormGroup<${uiEntityAndReferencedItemTypeAsString(uiEntityName)}FormPartGroup>"
    }

    private fun uiEntityAndReferencedItemTypeAsString(uiEntityName: NameCase): String =
        "${uiEntityName.pascalCase}${referencedItem.itemName.pascalCase}"

    override fun determineAngularFormInitialValue(): String {
        return if (isList) {
            "[]"
        } else {
            throw RuntimeException("ItemUiItemAttributeTypeModel has no form initial value.") // should not occur
        }
    }

}

class EnumUiAttributeModel(
    item: UiItemDescriptionModel,
    attributeName: NameCase,
    isNullable: Boolean,
    isList: Boolean,
    customValidation: Boolean,
    val enum: UiEnumModel,
) : UiAttributeModel(
    item = item,
    attributeName = attributeName,
    isNullable = isNullable,
    isList = isList,
    hasCustomValidation = customValidation,
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

    private fun enumTypeAsString(): String = this.enum.enumClassName

    override fun calculateAngularInitialValueFormSingleType(uiEntityName: NameCase): String {
        return enumTypeAsString()
    }

    /**
     * Something like:
     * - `FormControl<string>`
     * - `FormControl<string | null>`
     * - `FormControl<AppellatioEnum>`
     * - `FormGroup<ArticulusInteriorFormPartGroup>`
     * Form values are always `null`based, not `undefined`.
     */
    override fun calculateAngularFormControlSingleType(uiEntityName: NameCase): String {
        return "FormControl<${enumTypeAsString()}>"
    }

    override fun determineAngularFormInitialValue(): String {
        return enum.angularFormInitialValue
    }

}
