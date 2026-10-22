/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityFirstEntryGuardRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-first-entry-edit.guard.ts`
 * - path: `opus-magnum/opus-magnum-first-entry-edit.guard.ts`
 */
object EntityFirstEntryGuardRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |
          |
          |import {inject} from '@angular/core';
          |import {CanActivateFn, Router, UrlTree} from '@angular/router';
          |import {Observable} from 'rxjs';
          |import {map} from 'rxjs/operators';
          |import {${model.entityRootItem.itemName.pascalCase}Service} from '@app/service/${model.entityRootItem.itemName.kebabCase}.service';
          |
          |/**
          | * Resolves the first available {@link ${model.entityRootItem.itemName.pascalCase}WTO} and redirects to its edit route,
          | * i.e. {@code /${model.entityName.kebabCase}-routable-edit/<${model.idAttribute.attributeName.camelCase}>}. Used by the side navigation so a
          | * link can open "the first entry" without knowing its id up front. Falls back to the board
          | * when there is no entry to edit.
          | */
          |export const ${model.entityName.camelCase}FirstEntryEditGuard: CanActivateFn = (): Observable<UrlTree> => {
          |    const router = inject(Router);
          |    return inject(${model.entityRootItem.itemName.pascalCase}Service).get${model.entityRootItem.itemName.pascalCase}List().pipe(
          |        map(${model.entityRootItem.itemName.camelCase}List => {
          |            const first = ${model.entityRootItem.itemName.camelCase}List.at(0);
          |            return first
          |                ? router.createUrlTree(['/${model.entityName.kebabCase}-routable-edit', first.${model.idAttribute.attributeName.camelCase}])
          |                : router.createUrlTree(['/${model.entityName.kebabCase}-board']);
          |        })
          |    );
          |};
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-first-entry-edit.guard.ts"
    }
}