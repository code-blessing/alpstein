/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiItemModel

/**
 * Generate the content for the template `ItemReferenceFieldComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `membrum-relatum-reference-field.component.ts`
 * - path: `reference/membrum-relatum-reference-field/membrum-relatum-reference-field.component.ts`
 */
object ItemReferenceFieldComponentTypescriptRenderer : UiItemRenderer {

    override fun renderTemplate(model: UiItemModel): String {
        return """
          |import {Component, Input, OnInit} from '@angular/core';
          |import {FormControl} from "@angular/forms";
          |import {
          |    ${model.itemName.pascalCase}TypeaheadComponent
          |} from "@app/reference/${model.itemName.kebabCase}-typeahead/${model.itemName.kebabCase}-typeahead.component";
          |import {
          |    ${model.itemName.camelCase}DisplayRow,
          |    ${model.itemName.camelCase}DisplayRowLabel
          |} from "@app/reference/${model.itemName.kebabCase}-display";
          |import {${model.itemName.pascalCase}Service} from "@app/service/${model.itemName.kebabCase}.service";
          |import {
          |    FieldErrorMessagesComponent
          |} from "@app/shared/form-controls/field-error-messages/field-error-messages.component";
          |import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
          |${ if(model.hasUuidPrimaryKey) { """import {${model.primaryKeyAttribute.typescriptAttributeType}} from "@app/shared/uuid";
              |""" } else { """""" } }import {${model.itemName.pascalCase}WTO} from "@app/wto/${model.itemName.kebabCase}.wto";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |
          |/**
          | * Edits a single reference to a ${model.itemName.pascalCase}, held in the form as one FormControl of a
          | * primary key or null.
          | *
          | * The single-reference counterpart of the ${model.itemName.pascalCase}ReferenceTableComponent: the
          | * typeahead itself is the field, it shows the picked entry by its display attributes and a new
          | * pick overwrites it. There is nothing to delete: whether the reference exists at all is decided
          | * one level up by the nullability of the field, and while the field is not null exactly one
          | * reference is required, so an empty field is a validation error.
          | *
          | * The primary key stored in the form says nothing to the user, so it is resolved to the whole
          | * ${model.itemName.pascalCase}WTO through the already existing backend call (`GET /api/${model.itemName.kebabCase}/{id}`)
          | * and shown by its display attributes. Resolved objects are cached, so a key is fetched once.
          | */
          |@Component({
          |    selector: 'app-${model.itemName.kebabCase}-reference-field',
          |    templateUrl: './${model.itemName.kebabCase}-reference-field.component.html',
          |    styleUrls: ['./${model.itemName.kebabCase}-reference-field.component.scss'],
          |    imports: [
          |        ${model.itemName.pascalCase}TypeaheadComponent,
          |        FieldErrorMessagesComponent,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.itemName.pascalCase}ReferenceFieldComponent implements OnInit {
          |    @Input({required: true}) ${model.itemName.camelCase}ReferenceFormControl!: FormControl<${model.primaryKeyAttribute.typescriptAttributeType} | null>;
          |    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];
          |
          |    private readonly resolvedBy${model.primaryKeyAttribute.attributeName.pascalCase} = new Map<${model.primaryKeyAttribute.typescriptAttributeType}, ${model.itemName.pascalCase}WTO>();
          |
          |    constructor(private readonly ${model.itemName.camelCase}Service: ${model.itemName.pascalCase}Service) {}
          |
          |    ngOnInit(): void {
          |        this.refresh();
          |        // The form control is often patched only after the first render, so react to every change.
          |        this.${model.itemName.camelCase}ReferenceFormControl.valueChanges.subscribe(() => this.refresh());
          |    }
          |
          |    /**
          |     * The display attributes of the referenced entry, shown in the search field itself. Falls
          |     * back to the unresolved value for the attributes that are not known (yet).
          |     */
          |    protected selectionLabel(): string {
          |        const ${model.primaryKeyAttribute.attributeName.camelCase} = this.referenced${model.primaryKeyAttribute.attributeName.pascalCase}();
          |        if (${model.primaryKeyAttribute.attributeName.camelCase} === null) {
          |            return '';
          |        }
          |        return ${model.itemName.camelCase}DisplayRowLabel(
          |            ${model.itemName.camelCase}DisplayRow(${model.primaryKeyAttribute.attributeName.camelCase}, this.resolvedBy${model.primaryKeyAttribute.attributeName.pascalCase}.get(${model.primaryKeyAttribute.attributeName.camelCase}))
          |        );
          |    }
          |
          |    protected on${model.itemName.pascalCase}Selected(${model.itemName.camelCase}: ${model.itemName.pascalCase}WTO): void {
          |        this.resolvedBy${model.primaryKeyAttribute.attributeName.pascalCase}.set(${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase}, ${model.itemName.camelCase});
          |        this.${model.itemName.camelCase}ReferenceFormControl.setValue(${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase});
          |    }
          |
          |    protected referenced${model.primaryKeyAttribute.attributeName.pascalCase}(): ${model.primaryKeyAttribute.typescriptAttributeType} | null {
          |        return this.${model.itemName.camelCase}ReferenceFormControl.getRawValue();
          |    }
          |
          |    private refresh(): void {
          |        this.markAsTouchedIfEmpty();
          |        this.resolveMissing${model.itemName.pascalCase}();
          |    }
          |
          |    /**
          |     * Exactly one reference is required while the field is not null, so an empty enabled field
          |     * is an error the user has to see right away. The typeahead is deliberately not bound to the
          |     * form control, so nothing else ever marks it as touched and the message would stay hidden.
          |     */
          |    private markAsTouchedIfEmpty(): void {
          |        if (this.${model.itemName.camelCase}ReferenceFormControl.enabled && this.referenced${model.primaryKeyAttribute.attributeName.pascalCase}() === null) {
          |            this.${model.itemName.camelCase}ReferenceFormControl.markAsTouched();
          |        }
          |    }
          |
          |    /** The separate backend call that resolves the primary key if it is not yet in the cache. */
          |    private resolveMissing${model.itemName.pascalCase}(): void {
          |        const ${model.primaryKeyAttribute.attributeName.camelCase} = this.referenced${model.primaryKeyAttribute.attributeName.pascalCase}();
          |        if (${model.primaryKeyAttribute.attributeName.camelCase} === null || this.resolvedBy${model.primaryKeyAttribute.attributeName.pascalCase}.has(${model.primaryKeyAttribute.attributeName.camelCase})) {
          |            return;
          |        }
          |        this.${model.itemName.camelCase}Service.get${model.itemName.pascalCase}ById(${model.primaryKeyAttribute.attributeName.camelCase})
          |            .subscribe(${model.itemName.camelCase} => {
          |                if (${model.itemName.camelCase} !== null) {
          |                    this.resolvedBy${model.primaryKeyAttribute.attributeName.pascalCase}.set(${model.itemName.camelCase}.${model.primaryKeyAttribute.attributeName.camelCase}, ${model.itemName.camelCase});
          |                }
          |            });
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiItemModel): String {
      return "reference/${model.itemName.kebabCase}-reference-field/${model.itemName.kebabCase}-reference-field.component.ts"
    }
}