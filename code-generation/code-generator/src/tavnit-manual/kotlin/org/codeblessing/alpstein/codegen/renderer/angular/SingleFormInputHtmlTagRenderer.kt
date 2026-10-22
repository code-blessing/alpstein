package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.BuiltInTypeUiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.NameCase
import org.codeblessing.alpstein.codegen.renderer.model.ui.ItemReferenceUiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.EnumUiAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.ItemUiIAttributeModel
import org.codeblessing.alpstein.codegen.renderer.model.ui.UiAttributeModel
import org.codeblessing.alpstein.model.schema.BuiltInType

/**
 * Generate the content for the template `SingleFormInputHtmlTagRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-form-part.component.html`
 * - path: `opus-magnum/opus-magnum-form/opus-magnum-form-part/opus-magnum-form-part.component.html`
 */
object SingleFormInputHtmlTagRenderer {

    /**
     *  ` <app-text-input [textFormControl]="campusTextusObligatoriusControl" [label]="'silvaOptionum.campusTextusObligatorius.label' | transloco" [placeholder]="'silvaOptionum.campusTextusObligatorius.placeholder' | transloco" [validatorTranslations]="campusTextusObligatoriusValidatorNames" />`
     *  ` <app-articulus-interior-form-part [articulusInteriorForm]="articulusInteriorListFormGroupUnderEdit!"  />`
     *  ` <app-articulus-interior-form-part [articulusInteriorForm]="articulusInteriorSingularisControl"  />`
     *  ` <app-appellatio-selector [enumFormControl]="appellatioControl" [validatorTranslations]="appellatioValidatorNames" />`
     */
    fun renderTemplate(
        attributeModel: UiAttributeModel,
        uiEntityName: NameCase,
        isList: Boolean = false,
    ): String {
        val inputTag = when (attributeModel) {
            // must be checked before the built-in input: a reference is a UUID, but it is never
            // edited as one
            is ItemReferenceUiAttributeModel -> createItemReferenceInput(attributeModel)
            is BuiltInTypeUiAttributeModel -> createBuiltInInput(attributeModel)
            is EnumUiAttributeModel -> createEnumInput(attributeModel)
            is ItemUiIAttributeModel -> createItemInput(attributeModel, uiEntityName)
        }

        return """
                $inputTag        
            """.trimMargin(marginPrefix = "|")
    }

    /**
     *  ` <app-text-input [textFormControl]="campusTextusObligatoriusControl" [label]="'silvaOptionum.campusTextusObligatorius.label' | transloco" [placeholder]="'silvaOptionum.campusTextusObligatorius.placeholder' | transloco" [validatorTranslations]="campusTextusObligatoriusValidatorNames" />`
     *  ` <app-text-input [textFormControl]="indexUnicusControl" [label]="'silvaOptionum.indexUnicus.label' | transloco" [placeholder]="'silvaOptionum.indexUnicus.placeholder' | transloco" [validatorTranslations]="indexUnicusValidatorNames" [readonly]="true" />`
     *  ` <app-single-text-form-field-table [formArray]="iteratioSimpliciumTextuumControl" [columnHeader]="'silvaOptionum.iteratioSimpliciumTextuum.label' | transloco" [placeholder]="'silvaOptionum.iteratioSimpliciumTextuum.placeholder' | transloco" />`
     */
    private fun createBuiltInInput(attributeModel: BuiltInTypeUiAttributeModel): String {
        val infix = determineFormComponentTypeInfix(attributeModel.builtInType)
        val attributeNameCamelCase = attributeModel.attributeName.camelCase
        val label = """[label]="'${attributeLabelTranslationKey(attributeModel)}' | transloco""""
        val columnHeader = """[columnHeader]="'${attributeLabelTranslationKey(attributeModel)}' | transloco""""
        val placeholder = """[placeholder]="'${attributePlaceholderTranslationKey(attributeModel)}' | transloco""""
        // A checkbox has no placeholder, it is labelled next to the box instead.
        val isCheckbox = attributeModel.builtInType == BuiltInType.BOOLEAN
        return if(attributeModel.isList) {
            val entryInput = if(isCheckbox) label else placeholder
            """<app-single-${infix}-form-field-table [formArray]="${attributeNameCamelCase}Control" $columnHeader $entryInput [validatorTranslations]="${attributeNameCamelCase}ValidatorNames" />"""
        } else {
            val labelAndPlaceholder = if(isCheckbox) label else "$label $placeholder"
            // The database assigns the primary key, so it is shown but never edited.
            val readonly = if(attributeModel.isPrimaryKey) """ [readonly]="true"""" else ""
            """<app-${infix}-input [${infix}FormControl]="${attributeNameCamelCase}Control" $labelAndPlaceholder [validatorTranslations]="${attributeNameCamelCase}ValidatorNames"$readonly />"""
        }
    }

    /**
     * The label of a field is translated under the item the attribute belongs to, so that the
     * same attribute of two items can be labelled differently, e.g. `contact.firstname.label`.
     */
    private fun attributeLabelTranslationKey(attributeModel: UiAttributeModel): String =
        "${attributeModel.item.itemName.camelCase}.${attributeModel.attributeName.camelCase}.label"

    private fun attributePlaceholderTranslationKey(attributeModel: UiAttributeModel): String =
        "${attributeModel.item.itemName.camelCase}.${attributeModel.attributeName.camelCase}.placeholder"

    /**
     *  ` <app-membrum-relatum-reference-field [membrumRelatumReferenceFormControl]="relatioAdEntitatemOptionalisControl" [validatorTranslations]="relatioAdEntitatemOptionalisValidatorNames" />`
     *  ` <app-membrum-relatum-reference-table [membrumRelatumReferenceFormArray]="relatioAdEntitatemOptionalisIteratusControl" />`
     *
     * A reference is stored as the primary key of the referenced item, which tells the user
     * nothing. It is therefore edited with the reference components of the referenced item,
     * which search it with a typeahead and show it by its display attributes.
     */
    private fun createItemReferenceInput(attributeModel: ItemReferenceUiAttributeModel): String {
        val attributeNameCamelCase = attributeModel.attributeName.camelCase
        val referencedItem = attributeModel.referencedItem
        val componentSelectorPrefix = "app-${referencedItem.itemName.kebabCase}-reference"
        val referencedItemCamelCase = referencedItem.itemName.camelCase
        return if (attributeModel.isList) {
            """<$componentSelectorPrefix-table [${referencedItemCamelCase}ReferenceFormArray]="${attributeNameCamelCase}Control" />"""
        } else {
            """<$componentSelectorPrefix-field [${referencedItemCamelCase}ReferenceFormControl]="${attributeNameCamelCase}Control" [validatorTranslations]="${attributeNameCamelCase}ValidatorNames" />"""
        }
    }

    /**
     * Replaces the component to <app-text-input />
     */
    private fun determineFormComponentTypeInfix(builtInType: BuiltInType): String =
        when (builtInType) {
            // a UUID is edited as plain text, therefore it reuses the text input component
            BuiltInType.STRING, BuiltInType.UUID -> "text"
            // a floating point number is edited with the very same input as a whole one
            BuiltInType.NUMBER, BuiltInType.DOUBLE -> "number"
            BuiltInType.BOOLEAN -> "boolean"
        }


    /**
     *  ` <app-articulus-interior-form-part [articulusInteriorForm]="articulusInteriorListEditState.formGroupUnderEdit!"  />`
     *  ` <app-articulus-interior-form-part [articulusInteriorForm]="articulusInteriorSingularisControl"  />`
     */
    private fun createItemInput(attributeModel: ItemUiIAttributeModel, uiEntityName: NameCase): String {
        val attributeNameCamelCase = attributeModel.attributeName.camelCase
        val entityNameKebabCase = uiEntityName.kebabCase
        val itemNameKebabCase = attributeModel.referencedItem.itemName.kebabCase
        val itemNameCamelCase = attributeModel.referencedItem.itemName.camelCase
        val controlName = if(attributeModel.isList) "${attributeNameCamelCase}EditState.formGroupUnderEdit!" else "${attributeNameCamelCase}Control"
        return """<app-${entityNameKebabCase}-${itemNameKebabCase}-form-part [${itemNameCamelCase}Form]="$controlName"  />"""
    }

    /**
     *  ` <app-appellatio-comis-selector [enumFormControl]="appellatioControl" [validatorTranslations]="appellatioValidatorNames" />`
     *  ` <app-single-appellatio-comis-form-field-table [formArray]="appellatioOptionalisIteratusControl" [columnHeader]="'silvaOptionum.appellatioOptionalisIteratus.label' | transloco" [validatorTranslations]="appellatioOptionalisIteratusValidatorNames" />`
     */
    private fun createEnumInput(attributeModel: EnumUiAttributeModel): String {
        val attributeNameCamelCase = attributeModel.attributeName.camelCase
        val enumNameKebabCase = attributeModel.enum.enumName.kebabCase
        return if(attributeModel.isList) {
            """<app-single-${enumNameKebabCase}-form-field-table [formArray]="${attributeNameCamelCase}Control" [columnHeader]="'${attributeLabelTranslationKey(attributeModel)}' | transloco" [validatorTranslations]="${attributeNameCamelCase}ValidatorNames" />"""
        } else {
            """<app-${enumNameKebabCase}-selector [enumFormControl]="${attributeNameCamelCase}Control" [validatorTranslations]="${attributeNameCamelCase}ValidatorNames" />"""
        }
    }

}
