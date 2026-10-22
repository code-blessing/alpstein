/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityResultComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-result.component.ts`
 * - path: `opus-magnum/opus-magnum-result/opus-magnum-result.component.ts`
 */
object EntityResultComponentTypescriptRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
          |import {MatTableDataSource, MatTableModule} from '@angular/material/table';
          |import {${model.entityName.pascalCase}SearchCriteria} from '@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-search/${model.entityName.kebabCase}-search.component';
          |import {${model.entityRootItem.itemName.pascalCase}Service} from '@app/service/${model.entityRootItem.itemName.kebabCase}.service';
          |import {ReactiveFormsModule} from "@angular/forms";
          |import {MatButtonModule} from "@angular/material/button";
          |import {MatToolbarModule} from "@angular/material/toolbar";
          |import {MatCardModule} from "@angular/material/card";
          |import {MatFormFieldModule} from "@angular/material/form-field";
          |import {MatInputModule} from "@angular/material/input";
          |import {MatIconModule} from "@angular/material/icon";
          |import {MatExpansionModule} from "@angular/material/expansion";
          |import {MatSidenavModule} from "@angular/material/sidenav";
          |import {MatListModule} from "@angular/material/list";
          |import {MatDialogModule} from "@angular/material/dialog";
          |import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
          |import {MatTooltipModule} from "@angular/material/tooltip";
          |import {RouterLink} from "@angular/router";
          |import {${model.entityRootItem.itemName.pascalCase}WTO} from "@app/wto/${model.entityRootItem.itemName.kebabCase}.wto";
          |import {${model.entityRootItem.itemName.pascalCase}SearchCriteriaWTO} from "@app/wto/${model.entityRootItem.itemName.kebabCase}-search-criteria.wto";
          |import {TranslocoPipe} from "@jsverse/transloco";
          |import {finalize} from "rxjs";
          |${ if(model.searchResultContainsUuidReferences) { """import {UUID} from "@app/shared/uuid";
              |""" } else { """""" } }${ model.searchResultReferencedItems.joinToString("") { referencedItem ->  """import {${referencedItem.itemName.pascalCase}Service} from "@app/service/${referencedItem.itemName.kebabCase}.service";
              |import {${referencedItem.itemName.pascalCase}WTO} from "@app/wto/${referencedItem.itemName.kebabCase}.wto";
              |import {
              |    ${referencedItem.itemName.camelCase}DisplayRow,
              |    ${referencedItem.itemName.camelCase}DisplayRowLabel
              |} from "@app/reference/${referencedItem.itemName.kebabCase}-display";
              |""" } }
          |@Component({
          |    selector: 'app-${model.entityName.kebabCase}-result',
          |    templateUrl: './${model.entityName.kebabCase}-result.component.html',
          |    styleUrls: ['./${model.entityName.kebabCase}-result.component.scss'],
          |    imports: [
          |        ReactiveFormsModule,
          |        MatButtonModule,
          |        MatToolbarModule,
          |        MatTableModule,
          |        MatCardModule,
          |        MatFormFieldModule,
          |        MatInputModule,
          |        MatIconModule,
          |        MatExpansionModule,
          |        MatSidenavModule,
          |        MatListModule,
          |        MatDialogModule,
          |        MatProgressSpinnerModule,
          |        MatTooltipModule,
          |        RouterLink,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.entityName.pascalCase}ResultComponent implements OnInit, OnChanges {
          |    @Input() searchCriteria: ${model.entityName.pascalCase}SearchCriteria = {};
          |    @Input() refreshKey: number = 0;
          |    @Output() select${model.entityName.pascalCase} = new EventEmitter<${model.entityRootItem.itemName.pascalCase}WTO>();
          |    @Output() delete${model.entityName.pascalCase} = new EventEmitter<${model.entityRootItem.itemName.pascalCase}WTO>();
          |    @Output() create${model.entityName.pascalCase} = new EventEmitter<void>();
          |
          |    displayedColumns: string[] = [
          |${ model.searchResultAttributes.joinToString("") { attribute ->  """        '${attribute.attributeName.camelCase}',
              |""" } }        'actions'
          |    ];
          |    dataSource: MatTableDataSource<${model.entityRootItem.itemName.pascalCase}WTO> = new MatTableDataSource<${model.entityRootItem.itemName.pascalCase}WTO>();
          |    loading = false;
          |
          |${ model.searchResultReferencedItems.joinToString("") { referencedItem ->  """    private readonly resolved${referencedItem.itemName.pascalCase}By${referencedItem.idAttributeName.pascalCase} = new Map<${referencedItem.idTypescriptType}, ${referencedItem.itemName.pascalCase}WTO>();
              |
              |""" } }    constructor(
          |        private ${model.entityRootItem.itemName.camelCase}Service: ${model.entityRootItem.itemName.pascalCase}Service,
          |${ model.searchResultReferencedItems.joinToString("") { referencedItem ->  """        private readonly ${referencedItem.itemName.camelCase}ReferenceService: ${referencedItem.itemName.pascalCase}Service,
              |""" } }    ) {
          |    }
          |
          |    ngOnInit(): void {
          |        this.load${model.entityName.pascalCase}s();
          |    }
          |
          |    ngOnChanges(changes: SimpleChanges): void {
          |        const refreshed = changes['refreshKey'] && !changes['refreshKey'].firstChange;
          |        const searchCriteriaChanged = changes['searchCriteria'] && !changes['searchCriteria'].firstChange;
          |        if (refreshed || searchCriteriaChanged) {
          |            this.load${model.entityName.pascalCase}s();
          |        }
          |    }
          |
          |${ model.searchResultReferenceAttributes.joinToString("") { attribute ->  """    protected ${attribute.attributeName.camelCase}Label(${model.entityRootItem.itemName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO): string {
              |        // The attribute holds either a single key or a whole list of them; flattening a
              |        // one-element array covers both without a case distinction.
              |        return this.${attribute.referencedItem.itemName.camelCase}ReferenceLabel([${model.entityRootItem.itemName.camelCase}.${attribute.attributeName.camelCase}].flat());
              |    }
              |
              |""" } }    private load${model.entityName.pascalCase}s(): void {
          |        const searchCriteria: ${model.entityRootItem.itemName.pascalCase}SearchCriteriaWTO = {
          |            query: this.searchCriteria?.searchQuery ?? '',
          |        };
          |        this.loading = true;
          |        this.${model.entityRootItem.itemName.camelCase}Service.search${model.entityRootItem.itemName.pascalCase}List(searchCriteria)
          |            .pipe(finalize(() => this.loading = false))
          |            .subscribe(searchResult => {
          |                this.dataSource.data = searchResult.${model.entityRootItem.itemName.camelCase}List;
          |${ model.searchResultReferencedItems.joinToString("") { referencedItem ->  """                this.resolveMissing${referencedItem.itemName.pascalCase}(searchResult.${model.entityRootItem.itemName.camelCase}List);
              |""" } }            });
          |    }
          |
          |${ model.searchResultReferencedItems.joinToString("") { referencedItem ->  """    /**
              |     * The display attributes of the ${referencedItem.itemName.pascalCase} entries one cell references, joined into a
              |     * single line. A key that is not resolved (yet) falls back to the unresolved display value.
              |     */
              |    private ${referencedItem.itemName.camelCase}ReferenceLabel(${referencedItem.idAttributeName.camelCase}List: ReadonlyArray<${referencedItem.idTypescriptType} | null>): string {
              |        return ${referencedItem.idAttributeName.camelCase}List
              |            .filter(${referencedItem.idAttributeName.camelCase} => ${referencedItem.idAttributeName.camelCase} !== null)
              |            .map(${referencedItem.idAttributeName.camelCase} => ${referencedItem.itemName.camelCase}DisplayRowLabel(
              |                ${referencedItem.itemName.camelCase}DisplayRow(${referencedItem.idAttributeName.camelCase}, this.resolved${referencedItem.itemName.pascalCase}By${referencedItem.idAttributeName.pascalCase}.get(${referencedItem.idAttributeName.camelCase}))
              |            ))
              |            .join(', ');
              |    }
              |
              |    /**
              |     * The separate backend call that resolves the referenced ${referencedItem.itemName.pascalCase} keys of the loaded
              |     * rows which are not yet in the cache.
              |     */
              |    private resolveMissing${referencedItem.itemName.pascalCase}(${model.entityRootItem.itemName.camelCase}List: ReadonlyArray<${model.entityRootItem.itemName.pascalCase}WTO>): void {
              |        const referenced${referencedItem.idAttributeName.pascalCase}List = [
              |${ referencedItem.referenceAttributes.joinToString("") { attribute ->  """            ...${model.entityRootItem.itemName.camelCase}List.map(${model.entityRootItem.itemName.camelCase} => ${model.entityRootItem.itemName.camelCase}.${attribute.attributeName.camelCase}).flat(),
                  |""" } }        ];
              |        const unresolved${referencedItem.idAttributeName.pascalCase}List = [...new Set(referenced${referencedItem.idAttributeName.pascalCase}List)]
              |            .filter(${referencedItem.idAttributeName.camelCase} => ${referencedItem.idAttributeName.camelCase} !== null)
              |            .filter(${referencedItem.idAttributeName.camelCase} => !this.resolved${referencedItem.itemName.pascalCase}By${referencedItem.idAttributeName.pascalCase}.has(${referencedItem.idAttributeName.camelCase}));
              |        if (unresolved${referencedItem.idAttributeName.pascalCase}List.length === 0) {
              |            return;
              |        }
              |        this.${referencedItem.itemName.camelCase}ReferenceService.get${referencedItem.itemName.pascalCase}ListByIds({${referencedItem.idAttributeName.camelCase}List: unresolved${referencedItem.idAttributeName.pascalCase}List})
              |            .subscribe(byIdsResult => byIdsResult.${referencedItem.itemName.camelCase}List.forEach(
              |                ${referencedItem.itemName.camelCase} => this.resolved${referencedItem.itemName.pascalCase}By${referencedItem.idAttributeName.pascalCase}.set(${referencedItem.itemName.camelCase}.${referencedItem.idAttributeName.camelCase}, ${referencedItem.itemName.camelCase})
              |            ));
              |    }
              |
              |""" } }    onCreate(): void {
          |        this.create${model.entityName.pascalCase}.emit();
          |    }
          |
          |    onEdit(${model.entityName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO): void {
          |        this.select${model.entityName.pascalCase}.emit(${model.entityName.camelCase});
          |    }
          |
          |    onDelete(${model.entityName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO): void {
          |        this.delete${model.entityName.pascalCase}.emit(${model.entityName.camelCase});
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-result/${model.entityName.kebabCase}-result.component.ts"
    }
}