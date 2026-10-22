/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityRoutableEditComponentTypescriptRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-routable-edit.component.ts`
 * - path: `opus-magnum/opus-magnum-routable-edit/opus-magnum-routable-edit.component.ts`
 */
object EntityRoutableEditComponentTypescriptRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |import {Component, Input, OnInit} from '@angular/core';
          |import {${model.entityRootItem.itemName.pascalCase}WTO} from "@app/wto/${model.entityRootItem.itemName.kebabCase}.wto";
          |import {${model.entityRootItem.itemName.pascalCase}Service} from "@app/service/${model.entityRootItem.itemName.kebabCase}.service";
          |import {${model.entityName.pascalCase}FormComponent} from "@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form/${model.entityName.kebabCase}-form.component";
          |import {SaveNotificationService} from "@app/shared/save-notification/save-notification.service";
          |import {ViewItemLabelService} from "@app/view-tabs/view-item-label.service";
          |import {${model.entityRootItem.itemName.camelCase}DisplayLabel} from "@app/reference/${model.entityRootItem.itemName.kebabCase}-display";
          |${ if(model.entityRootItem.hasUuidPrimaryKey) { """import {${model.idAttribute.typescriptAttributeType}} from "@app/shared/uuid";
              |""" } else { """""" } }
          |import {TranslocoPipe} from "@jsverse/transloco";
          |import {finalize} from "rxjs";
          |
          |/**
          | * The editor of one ${model.entityName.pascalCase} on its own, without the search and the search result of the
          | * board around it. It edits the item its `itemId` addresses, or creates a new one when there
          | * is no `itemId`.
          | */
          |@Component({
          |    selector: 'app-${model.entityName.kebabCase}-routable-edit',
          |    templateUrl: './${model.entityName.kebabCase}-routable-edit.component.html',
          |    styleUrls: ['./${model.entityName.kebabCase}-routable-edit.component.scss'],
          |    imports: [
          |        ${model.entityName.pascalCase}FormComponent,
          |        TranslocoPipe,
          |    ]
          |})
          |export class ${model.entityName.pascalCase}RoutableEditComponent implements OnInit {
          |    /** The primary key of the edited ${model.entityRootItem.itemName.pascalCase} as it stands in the URL, null to create one. */
          |    @Input() itemId: string | null = null;
          |
          |    selected${model.entityName.pascalCase}: ${model.entityRootItem.itemName.pascalCase}WTO | null = null;
          |    loading = false;
          |    saving = false;
          |
          |    constructor(
          |        private ${model.entityRootItem.itemName.camelCase}Service: ${model.entityRootItem.itemName.pascalCase}Service,
          |        private saveNotification: SaveNotificationService,
          |        private viewItemLabels: ViewItemLabelService,
          |    ) {
          |    }
          |
          |    ngOnInit(): void {
          |        const idParam = this.itemId;
          |        if (idParam === null) {
          |            return;
          |        }
          |        const ${model.idAttribute.attributeName.camelCase} = ${model.entityRootItem.primaryKeyFromRouteParamExpression};
          |        this.loading = true;
          |        this.${model.entityRootItem.itemName.camelCase}Service.get${model.entityRootItem.itemName.pascalCase}ById(${model.idAttribute.attributeName.camelCase})
          |            .pipe(finalize(() => this.loading = false))
          |            .subscribe(${model.entityName.camelCase} => {
          |                this.selected${model.entityName.pascalCase} = ${model.entityName.camelCase};
          |                this.reportTabLabel(${model.entityName.camelCase});
          |            });
          |    }
          |
          |    get creating(): boolean {
          |        return this.itemId === null;
          |    }
          |
          |    onSave(${model.entityName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO): void {
          |        // The already stored ${model.entityRootItem.itemName.pascalCase}, not the form, decides that this is an update and
          |        // under which primary key: after a create the form still holds the placeholder key it
          |        // was initialised with, while the server has assigned the real one.
          |        const stored${model.entityName.pascalCase} = this.selected${model.entityName.pascalCase};
          |        const save${"$"} = stored${model.entityName.pascalCase} === null
          |            ? this.${model.entityRootItem.itemName.camelCase}Service.create${model.entityRootItem.itemName.pascalCase}(${model.entityName.camelCase})
          |            : this.${model.entityRootItem.itemName.camelCase}Service.update${model.entityRootItem.itemName.pascalCase}({...${model.entityName.camelCase}, ${model.idAttribute.attributeName.camelCase}: stored${model.entityName.pascalCase}.${model.idAttribute.attributeName.camelCase}});
          |        this.saving = true;
          |        save${"$"}.pipe(finalize(() => this.saving = false))
          |            .subscribe(saved${model.entityName.pascalCase} => {
          |                this.selected${model.entityName.pascalCase} = saved${model.entityName.pascalCase};
          |                this.reportTabLabel(saved${model.entityName.pascalCase});
          |                this.saveNotification.notifySaved();
          |            });
          |    }
          |
          |    // The tab this editor lives in names the edited item by its display attributes, which only
          |    // the loaded item knows. A created item is not reported: its tab is the create view, which
          |    // it stays after the save.
          |    private reportTabLabel(${model.entityName.camelCase}: ${model.entityRootItem.itemName.pascalCase}WTO | null): void {
          |        const itemId = this.itemId;
          |        if (itemId === null || ${model.entityName.camelCase} === null) {
          |            return;
          |        }
          |        this.viewItemLabels.setLabel('${model.entityName.kebabCase}', itemId, ${model.entityRootItem.itemName.camelCase}DisplayLabel(${model.entityName.camelCase}));
          |    }
          |}
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-routable-edit/${model.entityName.kebabCase}-routable-edit.component.ts"
    }
}