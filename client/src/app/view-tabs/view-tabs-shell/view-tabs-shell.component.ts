import {Component, OnDestroy, OnInit, QueryList, Type, ViewChildren} from '@angular/core';
import {NgComponentOutlet} from '@angular/common';
import {ActivatedRoute, ParamMap, Params, RouterLink} from '@angular/router';
import {MatTabLink, MatTabsModule} from '@angular/material/tabs';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {TranslocoPipe} from '@jsverse/transloco';
import {Subscription} from 'rxjs';
import {ViewDescriptor, viewToken} from '@app/view-tabs/view-descriptor';
import {OpenViewTab, VIEW_TABS_ROUTE, ViewTabsService} from '@app/view-tabs/view-tabs.service';
import {ViewEntityRegistration} from '@app/view-tabs/view-registry';
import {ViewItemLabelService} from '@app/view-tabs/view-item-label.service';
import {VIEW_REGISTRATIONS} from '@app/app-view-registrations';

interface ViewTab extends OpenViewTab {
    /** Identifies the tab *and* the view in it, so that `@for` tracks both. */
    trackId: string;
    titleTranslationKey: string;
    titleSuffix: string;
    component: Type<unknown>;
    inputs: Record<string, unknown>;
    activateParams: Params;
}

@Component({
    selector: 'app-view-tabs-shell',
    templateUrl: './view-tabs-shell.component.html',
    styleUrls: ['./view-tabs-shell.component.scss'],
    imports: [
        NgComponentOutlet,
        RouterLink,
        MatTabsModule,
        MatIconModule,
        MatButtonModule,
        TranslocoPipe,
    ]
})
export class ViewTabsShellComponent implements OnInit, OnDestroy {
    readonly viewTabsRoute = VIEW_TABS_ROUTE;

    tabs: ReadonlyArray<ViewTab> = [];
    activeKey: number | null = null;

    private openTabs: ReadonlyArray<OpenViewTab> = [];
    private readonly subscriptions = new Subscription();

    @ViewChildren(MatTabLink) private tabLinks?: QueryList<MatTabLink>;

    constructor(
        private readonly viewTabs: ViewTabsService,
        private readonly viewItemLabels: ViewItemLabelService,
        private readonly route: ActivatedRoute,
    ) {
    }

    ngOnInit(): void {
        this.subscriptions.add(this.route.queryParamMap
            .subscribe(queryParams => this.updateTabs(queryParams)));
        this.subscriptions.add(this.viewItemLabels.labelsChanged$
            .subscribe(() => this.renewTabs()));
    }

    ngOnDestroy(): void {
        this.subscriptions.unsubscribe();
    }

    closeTab(tab: ViewTab): void {
        this.viewTabs.closeTab(tab.key);
    }

    /**
     * Closing with the keyboard hands the focus to the tab that takes the place of the closed
     * one, instead of dropping it to the document. That tab keeps its element across the
     * navigation, which is why it can be focused before the closed tab is gone.
     */
    closeTabAndFocusNeighbour(tab: ViewTab): void {
        const closedIndex = this.tabs.indexOf(tab);
        const neighbour = this.tabLinks?.get(closedIndex + 1) ?? this.tabLinks?.get(closedIndex - 1);
        neighbour?.focus();
        this.closeTab(tab);
    }

    /**
     * Every tab is rendered and only the active one is visible, so that switching tabs keeps
     * the state of the views that are left behind.
     */
    private updateTabs(queryParams: ParamMap): void {
        this.openTabs = this.viewTabs.openTabs(queryParams)
            .filter(openTab => registrationOf(openTab.view) !== undefined);
        this.activeKey = this.viewTabs.activeTabKey(queryParams, this.openTabs);
        this.renewTabs();
    }

    // Every tab is renewed as a whole, also when nothing but one item label has changed: the
    // tabs keep their `trackId` and their input values, therefore no view is recreated.
    private renewTabs(): void {
        this.tabs = this.openTabs.map(openTab => this.viewTab(openTab, this.openTabs));
    }

    private viewTab(openTab: OpenViewTab, openTabs: ReadonlyArray<OpenViewTab>): ViewTab {
        const registration = requireRegistrationOf(openTab.view);
        return {
            ...openTab,
            trackId: `${openTab.key}:${viewToken(openTab.view)}`,
            titleTranslationKey: registration.titleTranslationKeys[openTab.view.viewKind],
            titleSuffix: this.titleSuffix(openTab.view),
            component: componentOf(registration, openTab.view),
            inputs: inputsOf(openTab),
            activateParams: this.viewTabs.tabSetParams(openTabs, openTab.key),
        };
    }

    /**
     * What an edited item is named in its tab: its display attributes, which is what identifies
     * it for a human reader. The primary key stands in for them as long as the editor of the
     * item has not reported them yet, and for an item that has no display attributes at all.
     */
    private titleSuffix(view: ViewDescriptor): string {
        if (view.viewKind !== 'edit') {
            return '';
        }
        return ` ${this.viewItemLabels.label(view) || view.itemId}`;
    }
}

function registrationOf(view: ViewDescriptor): ViewEntityRegistration | undefined {
    return VIEW_REGISTRATIONS.find(registration => registration.entityName === view.entityName);
}

function requireRegistrationOf(view: ViewDescriptor): ViewEntityRegistration {
    const registration = registrationOf(view);
    if (registration === undefined) {
        throw new Error(`No view registration for the entity '${view.entityName}'.`);
    }
    return registration;
}

function componentOf(registration: ViewEntityRegistration, view: ViewDescriptor): Type<unknown> {
    return view.viewKind === 'board' ? registration.boardComponent : registration.editorComponent;
}

// The inputs hold nothing but the values the addressed component declares as inputs:
// `ComponentRef.setInput` rejects an unknown name.
function inputsOf(openTab: OpenViewTab): Record<string, unknown> {
    if (openTab.view.viewKind === 'board') {
        return {};
    }
    return {
        itemId: openTab.view.itemId ?? null,
    };
}
