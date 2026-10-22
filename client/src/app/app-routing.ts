import {Routes} from '@angular/router';
import {GENERATED_ENTITY_ROUTES} from "@app/generated-routes";
import {ENTITY_ROUTES} from "@app/entity-routing";
import {ViewTabsShellComponent} from "@app/view-tabs/view-tabs-shell/view-tabs-shell.component";
import {StartPageComponent} from "@app/start-page/start-page.component";

export const ROUTES: Routes = [
    {path: 'views', component: ViewTabsShellComponent},
    ...ENTITY_ROUTES,
    ...GENERATED_ENTITY_ROUTES,
    {path: '', component: StartPageComponent, pathMatch: 'full'}
];
