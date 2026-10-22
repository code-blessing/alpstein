/* @tt{{{

    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityResultComponentTypescriptRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="OpusMagnum" replaceByExpression="model.entityName.pascalCase" ]
        [ searchValue="opusMagnum" replaceByExpression="model.entityName.camelCase" ]
        [ searchValue="opus-magnum" replaceByExpression="model.entityName.kebabCase" ]
        [ searchValue="SilvaOptionum" replaceByExpression="model.entityRootItem.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.entityRootItem.itemName.camelCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.entityRootItem.itemName.kebabCase" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */
import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {OpusMagnumSearchCriteria} from '@app/opus-magnum/opus-magnum-search/opus-magnum-search.component';
import {SilvaOptionumService} from '@app/service/silva-optionum.service';
import {ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatDialogModule} from "@angular/material/dialog";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatTooltipModule} from "@angular/material/tooltip";
import {RouterLink} from "@angular/router";
import {SilvaOptionumWTO} from "@app/wto/silva-optionum.wto";
import {SilvaOptionumSearchCriteriaWTO} from "@app/wto/silva-optionum-search-criteria.wto";
import {TranslocoPipe} from "@jsverse/transloco";
import {finalize} from "rxjs";
/* @tt{{{   @if [ conditionExpression="model.searchResultContainsUuidReferences"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */
/* @tt{{{
    @foreach [ iteratorExpression="model.searchResultReferencedItems" loopVariable="referencedItem" ]

    @replace-value-by-expression
        [ searchValue="MembrumRelatum" replaceByExpression="referencedItem.itemName.pascalCase" ]
        [ searchValue="membrumRelatum" replaceByExpression="referencedItem.itemName.camelCase" ]
        [ searchValue="membrum-relatum" replaceByExpression="referencedItem.itemName.kebabCase" ]
}}}@  */
import {MembrumRelatumService} from "@app/service/membrum-relatum.service";
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {
    membrumRelatumDisplayRow,
    membrumRelatumDisplayRowLabel
} from "@app/reference/membrum-relatum-display";
/* @tt{{{   @end-foreach  }}}@ */

@Component({
    selector: 'app-opus-magnum-result',
    templateUrl: './opus-magnum-result.component.html',
    styleUrls: ['./opus-magnum-result.component.scss'],
    imports: [
        ReactiveFormsModule,
        MatButtonModule,
        MatToolbarModule,
        MatTableModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatExpansionModule,
        MatSidenavModule,
        MatListModule,
        MatDialogModule,
        MatProgressSpinnerModule,
        MatTooltipModule,
        RouterLink,
        TranslocoPipe,
    ]
})
export class OpusMagnumResultComponent implements OnInit, OnChanges {
    @Input() searchCriteria: OpusMagnumSearchCriteria = {};
    @Input() refreshKey: number = 0;
    @Output() selectOpusMagnum = new EventEmitter<SilvaOptionumWTO>();
    @Output() deleteOpusMagnum = new EventEmitter<SilvaOptionumWTO>();
    @Output() createOpusMagnum = new EventEmitter<void>();

    displayedColumns: string[] = [
        /* @tt{{{   @ignore-text  }}}@ */
        'indexUnicus',
        /* @tt{{{   @end-ignore-text  }}}@ */
        /* @tt{{{
            @foreach [ iteratorExpression="model.searchResultAttributes" loopVariable="attribute" ]

            @replace-value-by-expression
                [ searchValue="campusTextusObligatorius" replaceByExpression="attribute.attributeName.camelCase" ]

        }}}@  */
        'campusTextusObligatorius',
    /* @tt{{{  @end-foreach  }}}@ */
        'actions'
    ];
    dataSource: MatTableDataSource<SilvaOptionumWTO> = new MatTableDataSource<SilvaOptionumWTO>();
    loading = false;

    /* @tt{{{
        @foreach [ iteratorExpression="model.searchResultReferencedItems" loopVariable="referencedItem" ]

        @replace-value-by-expression
            [ searchValue="MembrumRelatum" replaceByExpression="referencedItem.itemName.pascalCase" ]
            [ searchValue="membrumRelatum" replaceByExpression="referencedItem.itemName.camelCase" ]
            [ searchValue="ClavisPrimaria" replaceByExpression="referencedItem.idAttributeName.pascalCase" ]
            [ searchValue="clavisPrimaria" replaceByExpression="referencedItem.idAttributeName.camelCase" ]
            [ searchValue="UUID" replaceByExpression="referencedItem.idTypescriptType" ]
    }}}@  */
    private readonly resolvedMembrumRelatumByClavisPrimaria = new Map<UUID, MembrumRelatumWTO>();

    /* @tt{{{   @end-foreach  }}}@ */
    constructor(
        private silvaOptionumService: SilvaOptionumService,
        /* @tt{{{
            @foreach [ iteratorExpression="model.searchResultReferencedItems" loopVariable="referencedItem" ]

            @replace-value-by-expression
                [ searchValue="MembrumRelatum" replaceByExpression="referencedItem.itemName.pascalCase" ]
                [ searchValue="membrumRelatum" replaceByExpression="referencedItem.itemName.camelCase" ]
        }}}@  */
        private readonly membrumRelatumReferenceService: MembrumRelatumService,
        /* @tt{{{   @end-foreach  }}}@ */
    ) {
    }

    ngOnInit(): void {
        this.loadOpusMagnums();
    }

    ngOnChanges(changes: SimpleChanges): void {
        const refreshed = changes['refreshKey'] && !changes['refreshKey'].firstChange;
        const searchCriteriaChanged = changes['searchCriteria'] && !changes['searchCriteria'].firstChange;
        if (refreshed || searchCriteriaChanged) {
            this.loadOpusMagnums();
        }
    }

    /* @tt{{{
        @foreach [ iteratorExpression="model.searchResultReferenceAttributes" loopVariable="attribute" ]

        @replace-value-by-expression
            [ searchValue="relatioAdEntitatemOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
            [ searchValue="MembrumRelatum" replaceByExpression="attribute.referencedItem.itemName.pascalCase" ]
            [ searchValue="membrumRelatum" replaceByExpression="attribute.referencedItem.itemName.camelCase" ]
    }}}@  */
    protected relatioAdEntitatemOptionalisLabel(silvaOptionum: SilvaOptionumWTO): string {
        // The attribute holds either a single key or a whole list of them; flattening a
        // one-element array covers both without a case distinction.
        return this.membrumRelatumReferenceLabel([silvaOptionum.relatioAdEntitatemOptionalis].flat());
    }

    /* @tt{{{   @end-foreach  }}}@ */
    private loadOpusMagnums(): void {
        const searchCriteria: SilvaOptionumSearchCriteriaWTO = {
            query: this.searchCriteria?.searchQuery ?? '',
        };
        this.loading = true;
        this.silvaOptionumService.searchSilvaOptionumList(searchCriteria)
            .pipe(finalize(() => this.loading = false))
            .subscribe(searchResult => {
                this.dataSource.data = searchResult.silvaOptionumList;
                /* @tt{{{
                    @foreach [ iteratorExpression="model.searchResultReferencedItems" loopVariable="referencedItem" ]

                    @replace-value-by-expression
                        [ searchValue="MembrumRelatum" replaceByExpression="referencedItem.itemName.pascalCase" ]
                }}}@  */
                this.resolveMissingMembrumRelatum(searchResult.silvaOptionumList);
                /* @tt{{{   @end-foreach  }}}@ */
            });
    }

    /* @tt{{{
        @foreach [ iteratorExpression="model.searchResultReferencedItems" loopVariable="referencedItem" ]

        @replace-value-by-expression
            [ searchValue="MembrumRelatum" replaceByExpression="referencedItem.itemName.pascalCase" ]
            [ searchValue="membrumRelatum" replaceByExpression="referencedItem.itemName.camelCase" ]
            [ searchValue="ClavisPrimaria" replaceByExpression="referencedItem.idAttributeName.pascalCase" ]
            [ searchValue="clavisPrimaria" replaceByExpression="referencedItem.idAttributeName.camelCase" ]
            [ searchValue="UUID" replaceByExpression="referencedItem.idTypescriptType" ]
    }}}@  */
    /**
     * The display attributes of the MembrumRelatum entries one cell references, joined into a
     * single line. A key that is not resolved (yet) falls back to the unresolved display value.
     */
    private membrumRelatumReferenceLabel(clavisPrimariaList: ReadonlyArray<UUID | null>): string {
        return clavisPrimariaList
            .filter(clavisPrimaria => clavisPrimaria !== null)
            .map(clavisPrimaria => membrumRelatumDisplayRowLabel(
                membrumRelatumDisplayRow(clavisPrimaria, this.resolvedMembrumRelatumByClavisPrimaria.get(clavisPrimaria))
            ))
            .join(', ');
    }

    /**
     * The separate backend call that resolves the referenced MembrumRelatum keys of the loaded
     * rows which are not yet in the cache.
     */
    private resolveMissingMembrumRelatum(silvaOptionumList: ReadonlyArray<SilvaOptionumWTO>): void {
        const referencedClavisPrimariaList = [
            /* @tt{{{
                @foreach [ iteratorExpression="referencedItem.referenceAttributes" loopVariable="attribute" ]

                @replace-value-by-expression
                    [ searchValue="relatioAdEntitatemOptionalis" replaceByExpression="attribute.attributeName.camelCase" ]
            }}}@  */
            ...silvaOptionumList.map(silvaOptionum => silvaOptionum.relatioAdEntitatemOptionalis).flat(),
            /* @tt{{{   @end-foreach  }}}@ */
        ];
        const unresolvedClavisPrimariaList = [...new Set(referencedClavisPrimariaList)]
            .filter(clavisPrimaria => clavisPrimaria !== null)
            .filter(clavisPrimaria => !this.resolvedMembrumRelatumByClavisPrimaria.has(clavisPrimaria));
        if (unresolvedClavisPrimariaList.length === 0) {
            return;
        }
        this.membrumRelatumReferenceService.getMembrumRelatumListByIds({clavisPrimariaList: unresolvedClavisPrimariaList})
            .subscribe(byIdsResult => byIdsResult.membrumRelatumList.forEach(
                membrumRelatum => this.resolvedMembrumRelatumByClavisPrimaria.set(membrumRelatum.clavisPrimaria, membrumRelatum)
            ));
    }

    /* @tt{{{   @end-foreach  }}}@ */
    onCreate(): void {
        this.createOpusMagnum.emit();
    }

    onEdit(opusMagnum: SilvaOptionumWTO): void {
        this.selectOpusMagnum.emit(opusMagnum);
    }

    onDelete(opusMagnum: SilvaOptionumWTO): void {
        this.deleteOpusMagnum.emit(opusMagnum);
    }
}
