/*
 * This file is generated using https://github.com/code-blessing/tavnit
 */
package org.codeblessing.alpstein.codegen.renderer.angular

import org.codeblessing.alpstein.codegen.renderer.model.ui.UiEntityModel

/**
 * Generate the content for the template `EntityRoutingRenderer`.
 *
 * This template renderer was generated from the template:
 * - file: `opus-magnum-routing.ts`
 * - path: `opus-magnum/opus-magnum-routing.ts`
 */
object EntityRoutingRenderer : UiEntityRenderer {

    override fun renderTemplate(model: UiEntityModel): String {
        return """
          |
          |import {Routes} from '@angular/router';
          |import {${model.entityName.pascalCase}BoardComponent} from '@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-board/${model.entityName.kebabCase}-board.component';
          |import {
          |    ${model.entityName.pascalCase}RoutableEditComponent
          |} from "@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-routable-edit/${model.entityName.kebabCase}-routable-edit.component";
          |import {${model.entityName.camelCase}FirstEntryEditGuard} from "@app/${model.entityName.kebabCase}/${model.entityName.kebabCase}-first-entry-edit.guard";
          |import {SideNavLink} from "@app/side-nav/side-nav-list/side-nav-link.model";
          |import {ViewDescriptor} from "@app/view-tabs/view-descriptor";
          |import {ViewEntityRegistration} from "@app/view-tabs/view-registry";
          |import {openViewGuard} from "@app/view-tabs/open-view.guard";
          |
          |export const ${model.entityName.screamingSnakeCase}_BOARD_VIEW: ViewDescriptor = {entityName: '${model.entityName.kebabCase}', viewKind: 'board'};
          |export const ${model.entityName.screamingSnakeCase}_EDITOR_VIEW: ViewDescriptor = {entityName: '${model.entityName.kebabCase}', viewKind: 'edit'};
          |export const ${model.entityName.screamingSnakeCase}_CREATE_VIEW: ViewDescriptor = {entityName: '${model.entityName.kebabCase}', viewKind: 'create'};
          |
          |/**
          | * t(${model.entityName.camelCase}.board.title)
          | * t(${model.entityName.camelCase}.edit.title)
          | * t(${model.entityName.camelCase}.create.title)
          | */
          |export const ${model.entityName.screamingSnakeCase}_VIEW_REGISTRATION: ViewEntityRegistration = {
          |    entityName: '${model.entityName.kebabCase}',
          |    boardComponent: ${model.entityName.pascalCase}BoardComponent,
          |    editorComponent: ${model.entityName.pascalCase}RoutableEditComponent,
          |    titleTranslationKeys: {
          |        board: '${model.entityName.camelCase}.board.title',
          |        edit: '${model.entityName.camelCase}.edit.title',
          |        create: '${model.entityName.camelCase}.create.title',
          |    },
          |};
          |
          |// The route of a view is a link target only: the guard redirects it to the tab shell with that
          |// view opened in a tab.
          |export const ${model.entityName.screamingSnakeCase}_ROUTES: Routes = [
          |    {path: '${model.entityName.kebabCase}-board', canActivate: [openViewGuard(${model.entityName.screamingSnakeCase}_BOARD_VIEW)], children: []},
          |    {path: '${model.entityName.kebabCase}-create', canActivate: [openViewGuard(${model.entityName.screamingSnakeCase}_CREATE_VIEW)], children: []},
          |    {path: '${model.entityName.kebabCase}-edit-first-entry', canActivate: [${model.entityName.camelCase}FirstEntryEditGuard], children: []},
          |    {path: '${model.entityName.kebabCase}-routable-edit/:${model.idAttribute.attributeName.camelCase}', canActivate: [openViewGuard(${model.entityName.screamingSnakeCase}_EDITOR_VIEW, '${model.idAttribute.attributeName.camelCase}')], children: []},
          |    {path: '${model.entityName.kebabCase}-edit/:${model.idAttribute.attributeName.camelCase}', canActivate: [openViewGuard(${model.entityName.screamingSnakeCase}_EDITOR_VIEW, '${model.idAttribute.attributeName.camelCase}')], children: []},
          |];
          |
          |/**
          | * t(${model.entityName.camelCase}.sideNav.board)
          | * t(${model.entityName.camelCase}.sideNav.createNewEntry)
          | * t(${model.entityName.camelCase}.sideNav.editFirstEntry)
          | */
          |export const ${model.entityName.screamingSnakeCase}_SIDE_NAVIGATION_LINKS: ReadonlyArray<SideNavLink> = [
          |    {routeLink: '/${model.entityName.kebabCase}-board', nameTranslationKey: "${model.entityName.camelCase}.sideNav.board", icon: "people"},
          |]
          |
        """.trimMargin(marginPrefix = "|")
    }

    override fun filePath(model: UiEntityModel): String {
      return "${model.entityName.kebabCase}/${model.entityName.kebabCase}-routing.ts"
    }
}