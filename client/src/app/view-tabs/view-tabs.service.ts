import {Injectable} from '@angular/core';
import {ParamMap, Params, Router} from '@angular/router';
import {parseViewToken, ViewDescriptor, ViewOpenMode, viewToken} from '@app/view-tabs/view-descriptor';

/** One open tab: the view it shows and the key that identifies it in the URL. */
export interface OpenViewTab {
    key: number;
    view: ViewDescriptor;
}

export const VIEW_TABS_ROUTE = '/views';

const TAB_PARAM = 'tab';
const ACTIVE_TAB_PARAM = 'active';

/**
 * Reads and writes the tab set, which lives entirely in the query parameters of the
 * [VIEW_TABS_ROUTE]: `?tab=<key>:<view token>&active=<key>`, one `tab` parameter per open tab
 * in tab order. Every other place goes through this service instead of assembling those
 * parameters itself.
 */
@Injectable({providedIn: 'root'})
export class ViewTabsService {

    constructor(private readonly router: Router) {
    }

    /**
     * The open tabs in tab order. A tab whose key or token cannot be read, or whose key is
     * already taken, is dropped: the tab set comes from the URL and may have been edited by
     * hand, and two tabs sharing one key would also share one component instance.
     */
    openTabs(queryParams: ParamMap): ReadonlyArray<OpenViewTab> {
        const tabs: OpenViewTab[] = [];
        queryParams.getAll(TAB_PARAM).forEach(tabParam => {
            const tab = parseTabParam(tabParam);
            if (tab !== null && !tabs.some(openTab => openTab.key === tab.key)) {
                tabs.push(tab);
            }
        });
        return tabs;
    }

    /** Falls back to the first tab, so that an unknown `active` key never hides every tab. */
    activeTabKey(queryParams: ParamMap, tabs: ReadonlyArray<OpenViewTab>): number | null {
        const activeKey = Number(queryParams.get(ACTIVE_TAB_PARAM));
        if (tabs.some(tab => tab.key === activeKey)) {
            return activeKey;
        }
        return tabs.length === 0 ? null : tabs[0].key;
    }

    tabSetParams(tabs: ReadonlyArray<OpenViewTab>, activeKey: number | null): Params {
        return {
            [TAB_PARAM]: tabs.map(tab => `${tab.key}:${viewToken(tab.view)}`),
            [ACTIVE_TAB_PARAM]: activeKey,
        };
    }

    closeTabParams(
        tabs: ReadonlyArray<OpenViewTab>,
        activeKey: number | null,
        closedKey: number,
    ): Params {
        const remainingTabs = tabs.filter(tab => tab.key !== closedKey);
        if (activeKey !== null && activeKey !== closedKey) {
            return this.tabSetParams(remainingTabs, activeKey);
        }
        if (remainingTabs.length === 0) {
            return this.tabSetParams(remainingTabs, null);
        }
        const closedIndex = tabs.findIndex(tab => tab.key === closedKey);
        const neighbourIndex = Math.min(closedIndex, remainingTabs.length - 1);
        return this.tabSetParams(remainingTabs, remainingTabs[neighbourIndex].key);
    }

    /**
     * The tab set that a link opening [view] navigates to. Read from the current URL rather
     * than from a component, so that a link does not have to know the tabs that are open when
     * it is clicked.
     */
    openViewParams(view: ViewDescriptor, openMode: ViewOpenMode): Params {
        const queryParams = this.currentQueryParams();
        const tabs = this.openTabs(queryParams);
        const activeKey = this.activeTabKey(queryParams, tabs);
        if (openMode === 'currentTab' && activeKey !== null) {
            const replacedTabs = tabs.map(tab => tab.key === activeKey ? {key: tab.key, view} : tab);
            return this.tabSetParams(replacedTabs, activeKey);
        }
        const key = tabs.reduce((maxKey, tab) => Math.max(maxKey, tab.key), 0) + 1;
        return this.tabSetParams([...tabs, {key, view}], key);
    }

    closeTab(key: number): void {
        const queryParams = this.currentQueryParams();
        const tabs = this.openTabs(queryParams);
        this.router.navigate([VIEW_TABS_ROUTE], {
            queryParams: this.closeTabParams(tabs, this.activeTabKey(queryParams, tabs), key),
        });
    }

    private currentQueryParams(): ParamMap {
        return this.router.routerState.snapshot.root.queryParamMap;
    }
}

function parseTabParam(tabParam: string): OpenViewTab | null {
    const separatorIndex = tabParam.indexOf(':');
    if (separatorIndex < 0) {
        return null;
    }
    const key = Number(tabParam.slice(0, separatorIndex));
    const view = parseViewToken(tabParam.slice(separatorIndex + 1));
    if (!Number.isInteger(key) || view === null) {
        return null;
    }
    return {key, view};
}
