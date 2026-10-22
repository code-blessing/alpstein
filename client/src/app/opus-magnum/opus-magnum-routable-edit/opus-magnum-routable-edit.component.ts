/* @tt{{{

    

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityRoutableEditComponentTypescriptRenderer"
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

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.idAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.idAttribute.typescriptAttributeType" ]

    @modify-provided-filepath-by-replacements

    

}}}@ */
import {Component, Input, OnInit} from '@angular/core';
import {SilvaOptionumWTO} from "@app/wto/silva-optionum.wto";
import {SilvaOptionumService} from "@app/service/silva-optionum.service";
import {OpusMagnumFormComponent} from "@app/opus-magnum/opus-magnum-form/opus-magnum-form/opus-magnum-form.component";
import {SaveNotificationService} from "@app/shared/save-notification/save-notification.service";
import {ViewItemLabelService} from "@app/view-tabs/view-item-label.service";
import {silvaOptionumDisplayLabel} from "@app/reference/silva-optionum-display";
/* @tt{{{   @if [ conditionExpression="model.entityRootItem.hasUuidPrimaryKey"]  }}}@ */
import {UUID} from "@app/shared/uuid";
/* @tt{{{   @end-if  }}}@ */

import {TranslocoPipe} from "@jsverse/transloco";
import {finalize} from "rxjs";

/**
 * The editor of one OpusMagnum on its own, without the search and the search result of the
 * board around it. It edits the item its `itemId` addresses, or creates a new one when there
 * is no `itemId`.
 */
@Component({
    selector: 'app-opus-magnum-routable-edit',
    templateUrl: './opus-magnum-routable-edit.component.html',
    styleUrls: ['./opus-magnum-routable-edit.component.scss'],
    imports: [
        OpusMagnumFormComponent,
        TranslocoPipe,
    ]
})
export class OpusMagnumRoutableEditComponent implements OnInit {
    /** The primary key of the edited SilvaOptionum as it stands in the URL, null to create one. */
    @Input() itemId: string | null = null;

    selectedOpusMagnum: SilvaOptionumWTO | null = null;
    loading = false;
    saving = false;

    constructor(
        private silvaOptionumService: SilvaOptionumService,
        private saveNotification: SaveNotificationService,
        private viewItemLabels: ViewItemLabelService,
    ) {
    }

    ngOnInit(): void {
        const idParam = this.itemId;
        if (idParam === null) {
            return;
        }
        /* @tt{{{
            @replace-value-by-expression
                [ searchValue="idParam as UUID" replaceByExpression="model.entityRootItem.primaryKeyFromRouteParamExpression" ]
        }}}@ */
        const indexUnicus = idParam as UUID;
        /* @tt{{{   @end-replace-value-by-expression  }}}@ */
        this.loading = true;
        this.silvaOptionumService.getSilvaOptionumById(indexUnicus)
            .pipe(finalize(() => this.loading = false))
            .subscribe(opusMagnum => {
                this.selectedOpusMagnum = opusMagnum;
                this.reportTabLabel(opusMagnum);
            });
    }

    get creating(): boolean {
        return this.itemId === null;
    }

    onSave(opusMagnum: SilvaOptionumWTO): void {
        // The already stored SilvaOptionum, not the form, decides that this is an update and
        // under which primary key: after a create the form still holds the placeholder key it
        // was initialised with, while the server has assigned the real one.
        const storedOpusMagnum = this.selectedOpusMagnum;
        const save$ = storedOpusMagnum === null
            ? this.silvaOptionumService.createSilvaOptionum(opusMagnum)
            : this.silvaOptionumService.updateSilvaOptionum({...opusMagnum, indexUnicus: storedOpusMagnum.indexUnicus});
        this.saving = true;
        save$.pipe(finalize(() => this.saving = false))
            .subscribe(savedOpusMagnum => {
                this.selectedOpusMagnum = savedOpusMagnum;
                this.reportTabLabel(savedOpusMagnum);
                this.saveNotification.notifySaved();
            });
    }

    // The tab this editor lives in names the edited item by its display attributes, which only
    // the loaded item knows. A created item is not reported: its tab is the create view, which
    // it stays after the save.
    private reportTabLabel(opusMagnum: SilvaOptionumWTO | null): void {
        const itemId = this.itemId;
        if (itemId === null || opusMagnum === null) {
            return;
        }
        this.viewItemLabels.setLabel('opus-magnum', itemId, silvaOptionumDisplayLabel(opusMagnum));
    }
}
