/* @tt{{{


    @move-comment-backward
    @template-renderer [
        templateRendererClassName="EntityRoutingRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.angular"
        templateRendererInterfaceName="UiEntityRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.angular"
    ] [
        modelClassName="UiEntityModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.ui"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="OPUS_MAGNUM" replaceByExpression="model.entityName.screamingSnakeCase" ]
        [ searchValue="OpusMagnum" replaceByExpression="model.entityName.pascalCase" ]
        [ searchValue="opusMagnum" replaceByExpression="model.entityName.camelCase" ]
        [ searchValue="opus-magnum" replaceByExpression="model.entityName.kebabCase" ]

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.idAttribute.attributeName.camelCase" ]


    @modify-provided-filepath-by-replacements



}}}@ */

import {Routes} from '@angular/router';
import {OpusMagnumBoardComponent} from '@app/opus-magnum/opus-magnum-board/opus-magnum-board.component';
import {
    OpusMagnumRoutableEditComponent
} from "@app/opus-magnum/opus-magnum-routable-edit/opus-magnum-routable-edit.component";
import {opusMagnumFirstEntryEditGuard} from "@app/opus-magnum/opus-magnum-first-entry-edit.guard";
import {SideNavLink} from "@app/side-nav/side-nav-list/side-nav-link.model";
import {ViewDescriptor} from "@app/view-tabs/view-descriptor";
import {ViewEntityRegistration} from "@app/view-tabs/view-registry";
import {openViewGuard} from "@app/view-tabs/open-view.guard";

export const OPUS_MAGNUM_BOARD_VIEW: ViewDescriptor = {entityName: 'opus-magnum', viewKind: 'board'};
export const OPUS_MAGNUM_EDITOR_VIEW: ViewDescriptor = {entityName: 'opus-magnum', viewKind: 'edit'};
export const OPUS_MAGNUM_CREATE_VIEW: ViewDescriptor = {entityName: 'opus-magnum', viewKind: 'create'};

/**
 * t(opusMagnum.board.title)
 * t(opusMagnum.edit.title)
 * t(opusMagnum.create.title)
 */
export const OPUS_MAGNUM_VIEW_REGISTRATION: ViewEntityRegistration = {
    entityName: 'opus-magnum',
    boardComponent: OpusMagnumBoardComponent,
    editorComponent: OpusMagnumRoutableEditComponent,
    titleTranslationKeys: {
        board: 'opusMagnum.board.title',
        edit: 'opusMagnum.edit.title',
        create: 'opusMagnum.create.title',
    },
};

// The route of a view is a link target only: the guard redirects it to the tab shell with that
// view opened in a tab.
export const OPUS_MAGNUM_ROUTES: Routes = [
    {path: 'opus-magnum-board', canActivate: [openViewGuard(OPUS_MAGNUM_BOARD_VIEW)], children: []},
    {path: 'opus-magnum-create', canActivate: [openViewGuard(OPUS_MAGNUM_CREATE_VIEW)], children: []},
    {path: 'opus-magnum-edit-first-entry', canActivate: [opusMagnumFirstEntryEditGuard], children: []},
    {path: 'opus-magnum-routable-edit/:indexUnicus', canActivate: [openViewGuard(OPUS_MAGNUM_EDITOR_VIEW, 'indexUnicus')], children: []},
    {path: 'opus-magnum-edit/:indexUnicus', canActivate: [openViewGuard(OPUS_MAGNUM_EDITOR_VIEW, 'indexUnicus')], children: []},
];

/**
 * t(opusMagnum.sideNav.board)
 * t(opusMagnum.sideNav.createNewEntry)
 * t(opusMagnum.sideNav.editFirstEntry)
 */
export const OPUS_MAGNUM_SIDE_NAVIGATION_LINKS: ReadonlyArray<SideNavLink> = [
    {routeLink: '/opus-magnum-board', nameTranslationKey: "opusMagnum.sideNav.board", icon: "people"},
    /* @tt{{{   @ignore-text  }}}@ */
    {routeLink: '/opus-magnum-create', nameTranslationKey: "opusMagnum.sideNav.createNewEntry", icon: "add"},
    {routeLink: '/opus-magnum-edit-first-entry', nameTranslationKey: "opusMagnum.sideNav.editFirstEntry", icon: "code"},
    /* @tt{{{   @end-ignore-text  }}}@ */
]
