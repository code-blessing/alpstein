import {Component, Input, OnInit} from '@angular/core';
import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";
import {MembrumRelatumService} from "@app/service/membrum-relatum.service";
import {EntitasRelataFormComponent} from "@app/entitas-relata/entitas-relata-form/entitas-relata-form/entitas-relata-form.component";
import {SaveNotificationService} from "@app/shared/save-notification/save-notification.service";
import {ViewItemLabelService} from "@app/view-tabs/view-item-label.service";
import {membrumRelatumDisplayLabel} from "@app/reference/membrum-relatum-display";
import {UUID} from "@app/shared/uuid";

import {TranslocoPipe} from "@jsverse/transloco";
import {finalize} from "rxjs";

/**
 * The editor of one EntitasRelata on its own, without the search and the search result of the
 * board around it. It edits the item its `itemId` addresses, or creates a new one when there
 * is no `itemId`.
 */
@Component({
    selector: 'app-entitas-relata-routable-edit',
    templateUrl: './entitas-relata-routable-edit.component.html',
    styleUrls: ['./entitas-relata-routable-edit.component.scss'],
    imports: [
        EntitasRelataFormComponent,
        TranslocoPipe,
    ]
})
export class EntitasRelataRoutableEditComponent implements OnInit {
    /** The primary key of the edited MembrumRelatum as it stands in the URL, null to create one. */
    @Input() itemId: string | null = null;

    selectedEntitasRelata: MembrumRelatumWTO | null = null;
    loading = false;
    saving = false;

    constructor(
        private membrumRelatumService: MembrumRelatumService,
        private saveNotification: SaveNotificationService,
        private viewItemLabels: ViewItemLabelService,
    ) {
    }

    ngOnInit(): void {
        const idParam = this.itemId;
        if (idParam === null) {
            return;
        }
        const clavisPrimaria = idParam as UUID;
        this.loading = true;
        this.membrumRelatumService.getMembrumRelatumById(clavisPrimaria)
            .pipe(finalize(() => this.loading = false))
            .subscribe(entitasRelata => {
                this.selectedEntitasRelata = entitasRelata;
                this.reportTabLabel(entitasRelata);
            });
    }

    get creating(): boolean {
        return this.itemId === null;
    }

    onSave(entitasRelata: MembrumRelatumWTO): void {
        // The already stored MembrumRelatum, not the form, decides that this is an update and
        // under which primary key: after a create the form still holds the placeholder key it
        // was initialised with, while the server has assigned the real one.
        const storedEntitasRelata = this.selectedEntitasRelata;
        const save$ = storedEntitasRelata === null
            ? this.membrumRelatumService.createMembrumRelatum(entitasRelata)
            : this.membrumRelatumService.updateMembrumRelatum({...entitasRelata, clavisPrimaria: storedEntitasRelata.clavisPrimaria});
        this.saving = true;
        save$.pipe(finalize(() => this.saving = false))
            .subscribe(savedEntitasRelata => {
                this.selectedEntitasRelata = savedEntitasRelata;
                this.reportTabLabel(savedEntitasRelata);
                this.saveNotification.notifySaved();
            });
    }

    // The tab this editor lives in names the edited item by its display attributes, which only
    // the loaded item knows. A created item is not reported: its tab is the create view, which
    // it stays after the save.
    private reportTabLabel(entitasRelata: MembrumRelatumWTO | null): void {
        const itemId = this.itemId;
        if (itemId === null || entitasRelata === null) {
            return;
        }
        this.viewItemLabels.setLabel('entitas-relata', itemId, membrumRelatumDisplayLabel(entitasRelata));
    }
}
