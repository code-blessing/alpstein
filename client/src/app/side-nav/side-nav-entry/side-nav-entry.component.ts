import {Component, Input} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from "@angular/material/list";
import {Params, RouterLink} from "@angular/router";
import {ViewOpenMode} from "@app/view-tabs/view-descriptor";

// Bound as they are instead of being built per change detection cycle: a fresh object would
// look like a changed link target to the router link.
const NEW_TAB_PARAMS: Params = {};
const CURRENT_TAB_PARAMS: Params = {open: 'currentTab'};

@Component({
    selector: 'side-nav-entry',
    templateUrl: './side-nav-entry.component.html',
    styleUrls: ['./side-nav-entry.component.scss'],
    imports: [
        MatIconModule,
        MatListModule,
        RouterLink,
    ],
    providers: []
})
export class SideNavEntryComponent {

    @Input() routeLink!: string;
    @Input() openMode: ViewOpenMode | undefined;
    @Input() linkText!: string;
    @Input() iconName: string | undefined;

    get queryParams(): Params {
        return this.openMode === 'currentTab' ? CURRENT_TAB_PARAMS : NEW_TAB_PARAMS;
    }

}
