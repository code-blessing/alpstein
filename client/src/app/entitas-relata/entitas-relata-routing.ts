import {Routes} from '@angular/router';
import {EntitasRelataBoardComponent} from '@app/entitas-relata/entitas-relata-board/entitas-relata-board.component';
import {
    EntitasRelataRoutableEditComponent
} from "@app/entitas-relata/entitas-relata-routable-edit/entitas-relata-routable-edit.component";
import {SideNavLink} from "@app/side-nav/side-nav-list/side-nav-link.model";
import {ViewDescriptor} from "@app/view-tabs/view-descriptor";
import {ViewEntityRegistration} from "@app/view-tabs/view-registry";
import {openViewGuard} from "@app/view-tabs/open-view.guard";

export const ENTITAS_RELATA_BOARD_VIEW: ViewDescriptor = {entityName: 'entitas-relata', viewKind: 'board'};
export const ENTITAS_RELATA_EDITOR_VIEW: ViewDescriptor = {entityName: 'entitas-relata', viewKind: 'edit'};
export const ENTITAS_RELATA_CREATE_VIEW: ViewDescriptor = {entityName: 'entitas-relata', viewKind: 'create'};

/**
 * t(entitasRelata.board.title)
 * t(entitasRelata.edit.title)
 * t(entitasRelata.create.title)
 */
export const ENTITAS_RELATA_VIEW_REGISTRATION: ViewEntityRegistration = {
    entityName: 'entitas-relata',
    boardComponent: EntitasRelataBoardComponent,
    editorComponent: EntitasRelataRoutableEditComponent,
    titleTranslationKeys: {
        board: 'entitasRelata.board.title',
        edit: 'entitasRelata.edit.title',
        create: 'entitasRelata.create.title',
    },
};

export const ENTITAS_RELATA_ROUTES: Routes = [
    {path: 'entitas-relata-board', canActivate: [openViewGuard(ENTITAS_RELATA_BOARD_VIEW)], children: []},
    {path: 'entitas-relata-create', canActivate: [openViewGuard(ENTITAS_RELATA_CREATE_VIEW)], children: []},
    {path: 'entitas-relata-routable-edit/:clavisPrimaria', canActivate: [openViewGuard(ENTITAS_RELATA_EDITOR_VIEW, 'clavisPrimaria')], children: []},
    {path: 'entitas-relata-edit/:clavisPrimaria', canActivate: [openViewGuard(ENTITAS_RELATA_EDITOR_VIEW, 'clavisPrimaria')], children: []},
];

/**
 * t(entitasRelata.sideNav.board)
 */
export const ENTITAS_RELATA_SIDE_NAVIGATION_LINKS: ReadonlyArray<SideNavLink> = [
    {routeLink: '/entitas-relata-board', nameTranslationKey: "entitasRelata.sideNav.board", icon: "people"},
]
