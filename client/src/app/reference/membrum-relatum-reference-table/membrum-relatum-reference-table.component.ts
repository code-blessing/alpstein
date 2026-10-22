/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemReferenceTableComponentTypescriptRenderer"
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
import {FormArray, FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatTooltipModule} from "@angular/material/tooltip";
import {RouterLink} from "@angular/router";
import {
    MembrumRelatumTypeaheadComponent
} from "@app/reference/membrum-relatum-typeahead/membrum-relatum-typeahead.component";
import {membrumRelatumDisplayRow} from "@app/reference/membrum-relatum-display";
import {MembrumRelatumService} from "@app/service/membrum-relatum.service";
import {
    MembrumRelatumReferenceTableRow
} from "@app/reference/membrum-relatum-reference-table/membrum-relatum-reference-table-row.model";
import {FormUtil} from "@app/shared/form-controls/form.util";
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {TranslocoPipe} from "@jsverse/transloco";

/**
 * Edits a list of references to MembrumRelatum, held in the form as a FormArray of primary keys.
 *
 * New rows are only ever created by picking an existing MembrumRelatum in the typeahead, so
 * there is no "add empty row" button and the generic SingleFormFieldTableComponent does not
 * fit here.
 *
 * The primary keys already stored in the form say nothing to the user, so they are resolved to whole
 * MembrumRelatumWTOs through a separate backend call (`POST /api/membrum-relatum/by-ids`) and
 * shown by their display attributes. Resolved objects are cached, so a key is fetched once.
 */
@Component({
    selector: 'app-membrum-relatum-reference-table',
    templateUrl: './membrum-relatum-reference-table.component.html',
    styleUrls: ['./membrum-relatum-reference-table.component.scss'],
    imports: [
        ReactiveFormsModule,
        MatButtonModule,
        MatIconModule,
        MatTableModule,
        MatTooltipModule,
        MembrumRelatumTypeaheadComponent,
        RouterLink,
        TranslocoPipe,
    ]
})
export class MembrumRelatumReferenceTableComponent implements OnInit {
    @Input({required: true}) membrumRelatumReferenceFormArray!: FormArray<FormControl<UUID>>;
    /* @tt{{{
        @foreach [ iteratorExpression="model.displayAttributes" loopVariable="displayAttribute" ]
        @replace-value-by-expression
            [ searchValue="DescriptioExDistanti" replaceByExpression="displayAttribute.attributeName.pascalCase" ]
            [ searchValue="descriptioExDistanti" replaceByExpression="displayAttribute.attributeName.camelCase" ]
    }}}@ */
    /**
     * t(membrumRelatum.descriptioExDistanti.label)
     */
    @Input() columnHeaderDescriptioExDistantiTranslationKey: string = 'membrumRelatum.descriptioExDistanti.label';
    /* @tt{{{   @end-foreach  }}}@ */

    protected readonly displayedColumns: string[] = [
        /* @tt{{{
            @foreach [ iteratorExpression="model.displayAttributes" loopVariable="displayAttribute" ]
            @replace-value-by-expression
                [ searchValue="descriptioExDistanti" replaceByExpression="displayAttribute.attributeName.camelCase" ]
        }}}@ */
        'descriptioExDistanti',
        /* @tt{{{   @end-foreach  }}}@ */
        'actions',
    ];
    protected readonly dataSource = new MatTableDataSource<MembrumRelatumReferenceTableRow>();

    private readonly resolvedByClavisPrimaria = new Map<UUID, MembrumRelatumWTO>();

    constructor(private readonly membrumRelatumService: MembrumRelatumService) {}

    ngOnInit(): void {
        this.refresh();
        // The form array is often patched only after the first render, so react to every change.
        this.membrumRelatumReferenceFormArray.valueChanges.subscribe(() => this.refresh());
    }

    protected referencedClavisPrimariaList(): ReadonlyArray<UUID> {
        return this.membrumRelatumReferenceFormArray.controls.map(control => control.getRawValue());
    }

    protected onMembrumRelatumSelected(membrumRelatum: MembrumRelatumWTO): void {
        if (this.referencedClavisPrimariaList().includes(membrumRelatum.clavisPrimaria)) {
            return;
        }
        this.resolvedByClavisPrimaria.set(membrumRelatum.clavisPrimaria, membrumRelatum);
        this.membrumRelatumReferenceFormArray.push(
            new FormControl<UUID>(membrumRelatum.clavisPrimaria, {nonNullable: true})
        );
    }

    protected onDelete(tableRow: MembrumRelatumReferenceTableRow): void {
        FormUtil.removeControl(this.membrumRelatumReferenceFormArray, tableRow.formControl);
    }

    private refresh(): void {
        this.updateFormData();
        this.resolveMissingMembrumRelatum();
    }

    /** The separate backend call that resolves the primary keys which are not yet in the cache. */
    private resolveMissingMembrumRelatum(): void {
        const unresolvedClavisPrimariaList = this.referencedClavisPrimariaList()
            .filter(clavisPrimaria => !this.resolvedByClavisPrimaria.has(clavisPrimaria));
        if (unresolvedClavisPrimariaList.length === 0) {
            return;
        }
        this.membrumRelatumService.getMembrumRelatumListByIds({clavisPrimariaList: [...unresolvedClavisPrimariaList]})
            .subscribe(byIdsResult => {
                byIdsResult.membrumRelatumList.forEach(
                    membrumRelatum => this.resolvedByClavisPrimaria.set(membrumRelatum.clavisPrimaria, membrumRelatum)
                );
                this.updateFormData();
            });
    }

    private updateFormData(): void {
        this.dataSource.data = this.membrumRelatumReferenceFormArray.controls.map((control) => this.toTableRow(control))
    }

    private toTableRow(formControl: FormControl<UUID>): MembrumRelatumReferenceTableRow {
        const clavisPrimaria = formControl.getRawValue();
        return {
            ...membrumRelatumDisplayRow(clavisPrimaria, this.resolvedByClavisPrimaria.get(clavisPrimaria)),
            formControl: formControl,
        }
    }
}
