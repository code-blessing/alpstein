import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {ViewDescriptor, viewToken} from '@app/view-tabs/view-descriptor';

/**
 * The label of every item that has been opened in a tab: its display attributes joined into the
 * single line a tab has room for.
 *
 * The editor of an item loads that item anyway, therefore it reports its label here instead of
 * the tab bar resolving every open item a second time. A label is kept after its tab is closed,
 * so that reopening the item shows it right away.
 */
@Injectable({providedIn: 'root'})
export class ViewItemLabelService {
    private readonly labelsByViewToken = new Map<string, string>();
    private readonly labelsChanged = new Subject<void>();

    /** Emits whenever a label arrives or changes, so that the tab bar can renew its titles. */
    readonly labelsChanged$: Observable<void> = this.labelsChanged.asObservable();

    setLabel(entityName: string, itemId: string, label: string): void {
        this.labelsByViewToken.set(viewToken({entityName, viewKind: 'edit', itemId}), label);
        this.labelsChanged.next();
    }

    label(view: ViewDescriptor): string | undefined {
        return this.labelsByViewToken.get(viewToken(view));
    }
}
