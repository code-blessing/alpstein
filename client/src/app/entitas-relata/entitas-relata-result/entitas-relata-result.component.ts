import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MembrumRelatumService} from '@app/service/membrum-relatum.service';
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
import {MatTooltipModule} from "@angular/material/tooltip";
import {RouterLink} from "@angular/router";
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {EntitasRelataSearchCriteria} from "@app/entitas-relata/entitas-relata-search/entitas-relata-search.component";
import {MembrumRelatumSearchCriteriaWTO} from "@app/wto/membrum-relatum-search-criteria.wto";
import {TranslocoPipe} from "@jsverse/transloco";

@Component({
    selector: 'app-entitas-relata-result',
    templateUrl: './entitas-relata-result.component.html',
    styleUrls: ['./entitas-relata-result.component.scss'],
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
        MatTooltipModule,
        RouterLink,
        TranslocoPipe,
    ]
})
export class EntitasRelataResultComponent implements OnInit, OnChanges {
    @Input() searchCriteria: EntitasRelataSearchCriteria = {};
    @Input() refreshKey: number = 0;
    @Output() selectEntitasRelata = new EventEmitter<MembrumRelatumWTO>();
    @Output() deleteEntitasRelata = new EventEmitter<MembrumRelatumWTO>();
    @Output() createEntitasRelata = new EventEmitter<void>();

    displayedColumns: string[] = [
        'clavisPrimaria',
        'descriptioExDistanti',
        'actions'
    ];
    dataSource: MatTableDataSource<MembrumRelatumWTO> = new MatTableDataSource<MembrumRelatumWTO>();

    constructor(private membrumRelatumService: MembrumRelatumService) {
    }

    ngOnInit(): void {
        this.loadEntitasRelatas();
    }

    ngOnChanges(changes: SimpleChanges): void {
        const refreshed = changes['refreshKey'] && !changes['refreshKey'].firstChange;
        const searchCriteriaChanged = changes['searchCriteria'] && !changes['searchCriteria'].firstChange;
        if (refreshed || searchCriteriaChanged) {
            this.loadEntitasRelatas();
        }
    }


    private loadEntitasRelatas(): void {
        const searchCriteria: MembrumRelatumSearchCriteriaWTO = {
            query: this.searchCriteria?.searchQuery ?? '',
        };
        this.membrumRelatumService.searchMembrumRelatumList(searchCriteria)
            .subscribe(searchResult => {
                this.dataSource.data = searchResult.membrumRelatumList;
            });
    }

    onCreate(): void {
        this.createEntitasRelata.emit();
    }

    onEdit(entitasRelata: MembrumRelatumWTO): void {
        this.selectEntitasRelata.emit(entitasRelata);
    }

    onDelete(entitasRelata: MembrumRelatumWTO): void {
        this.deleteEntitasRelata.emit(entitasRelata);
    }
}
