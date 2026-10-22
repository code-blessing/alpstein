/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemReferenceFieldComponentTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="MembrumRelatum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="membrumRelatum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="membrum-relatum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="ClavisPrimaria" replaceByExpression="model.primaryKeyAttribute.attributeName.pascalCase" ]
        [ searchValue="clavisPrimaria" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.typescriptAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {
    MembrumRelatumTypeaheadComponent
} from "@app/reference/membrum-relatum-typeahead/membrum-relatum-typeahead.component";
import {
    membrumRelatumDisplayRow,
    membrumRelatumDisplayRowLabel
} from "@app/reference/membrum-relatum-display";
import {MembrumRelatumService} from "@app/service/membrum-relatum.service";
import {
    FieldErrorMessagesComponent
} from "@app/shared/form-controls/field-error-messages/field-error-messages.component";
import {ValidatorTranslation} from "@app/shared/form-controls/validator-translation";
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {TranslocoPipe} from "@jsverse/transloco";

/**
 * Edits a single reference to a MembrumRelatum, held in the form as one FormControl of a
 * primary key or null.
 *
 * The single-reference counterpart of the MembrumRelatumReferenceTableComponent: the
 * typeahead itself is the field, it shows the picked entry by its display attributes and a new
 * pick overwrites it. There is nothing to delete: whether the reference exists at all is decided
 * one level up by the nullability of the field, and while the field is not null exactly one
 * reference is required, so an empty field is a validation error.
 *
 * The primary key stored in the form says nothing to the user, so it is resolved to the whole
 * MembrumRelatumWTO through the already existing backend call (`GET /api/membrum-relatum/{id}`)
 * and shown by its display attributes. Resolved objects are cached, so a key is fetched once.
 */
@Component({
    selector: 'app-membrum-relatum-reference-field',
    templateUrl: './membrum-relatum-reference-field.component.html',
    styleUrls: ['./membrum-relatum-reference-field.component.scss'],
    imports: [
        MembrumRelatumTypeaheadComponent,
        FieldErrorMessagesComponent,
        TranslocoPipe,
    ]
})
export class MembrumRelatumReferenceFieldComponent implements OnInit {
    @Input({required: true}) membrumRelatumReferenceFormControl!: FormControl<UUID | null>;
    @Input() validatorTranslations: ReadonlyArray<ValidatorTranslation> = [];

    private readonly resolvedByClavisPrimaria = new Map<UUID, MembrumRelatumWTO>();

    constructor(private readonly membrumRelatumService: MembrumRelatumService) {}

    ngOnInit(): void {
        this.refresh();
        // The form control is often patched only after the first render, so react to every change.
        this.membrumRelatumReferenceFormControl.valueChanges.subscribe(() => this.refresh());
    }

    /**
     * The display attributes of the referenced entry, shown in the search field itself. Falls
     * back to the unresolved value for the attributes that are not known (yet).
     */
    protected selectionLabel(): string {
        const clavisPrimaria = this.referencedClavisPrimaria();
        if (clavisPrimaria === null) {
            return '';
        }
        return membrumRelatumDisplayRowLabel(
            membrumRelatumDisplayRow(clavisPrimaria, this.resolvedByClavisPrimaria.get(clavisPrimaria))
        );
    }

    protected onMembrumRelatumSelected(membrumRelatum: MembrumRelatumWTO): void {
        this.resolvedByClavisPrimaria.set(membrumRelatum.clavisPrimaria, membrumRelatum);
        this.membrumRelatumReferenceFormControl.setValue(membrumRelatum.clavisPrimaria);
    }

    protected referencedClavisPrimaria(): UUID | null {
        return this.membrumRelatumReferenceFormControl.getRawValue();
    }

    private refresh(): void {
        this.markAsTouchedIfEmpty();
        this.resolveMissingMembrumRelatum();
    }

    /**
     * Exactly one reference is required while the field is not null, so an empty enabled field
     * is an error the user has to see right away. The typeahead is deliberately not bound to the
     * form control, so nothing else ever marks it as touched and the message would stay hidden.
     */
    private markAsTouchedIfEmpty(): void {
        if (this.membrumRelatumReferenceFormControl.enabled && this.referencedClavisPrimaria() === null) {
            this.membrumRelatumReferenceFormControl.markAsTouched();
        }
    }

    /** The separate backend call that resolves the primary key if it is not yet in the cache. */
    private resolveMissingMembrumRelatum(): void {
        const clavisPrimaria = this.referencedClavisPrimaria();
        if (clavisPrimaria === null || this.resolvedByClavisPrimaria.has(clavisPrimaria)) {
            return;
        }
        this.membrumRelatumService.getMembrumRelatumById(clavisPrimaria)
            .subscribe(membrumRelatum => {
                if (membrumRelatum !== null) {
                    this.resolvedByClavisPrimaria.set(membrumRelatum.clavisPrimaria, membrumRelatum);
                }
            });
    }
}
