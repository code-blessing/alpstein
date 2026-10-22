import {inject} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivateFn, Router, UrlTree} from '@angular/router';
import {ViewDescriptor, ViewOpenMode} from '@app/view-tabs/view-descriptor';
import {VIEW_TABS_ROUTE, ViewTabsService} from '@app/view-tabs/view-tabs.service';

const OPEN_MODE_PARAM = 'open';

/**
 * Turns the route of a view into the tab set that has that view open, i.e. every entity route
 * is a link target that redirects to the tab shell. A link declares with `?open=currentTab`
 * that it replaces the active tab; without that parameter it opens a new tab.
 *
 * The redirect deliberately pushes a history entry: going back is how the user returns to the
 * previous tab set.
 */
export function openViewGuard(view: ViewDescriptor, idRouteParamName?: string): CanActivateFn {
    return (route: ActivatedRouteSnapshot): UrlTree => {
        const router = inject(Router);
        const viewTabs = inject(ViewTabsService);
        const openMode: ViewOpenMode = route.queryParamMap.get(OPEN_MODE_PARAM) === 'currentTab'
            ? 'currentTab'
            : 'newTab';
        return router.createUrlTree([VIEW_TABS_ROUTE], {
            queryParams: viewTabs.openViewParams(openedView(view, route, idRouteParamName), openMode),
        });
    };
}

function openedView(
    view: ViewDescriptor,
    route: ActivatedRouteSnapshot,
    idRouteParamName: string | undefined,
): ViewDescriptor {
    if (idRouteParamName === undefined) {
        return view;
    }
    return {...view, itemId: route.paramMap.get(idRouteParamName) ?? undefined};
}
