/**
 * One view that can live in a tab: the board of a UiEntity, the editor of one of its items,
 * or the editor of an item that does not exist yet.
 */
export type ViewKind = 'board' | 'edit' | 'create';

/** What a link does with the view it points at. */
export type ViewOpenMode = 'newTab' | 'currentTab';

export interface ViewDescriptor {
    /** The kebab-case name of the UiEntity, as it appears in its routes. */
    entityName: string;
    viewKind: ViewKind;
    /** The primary key of the edited item, as the raw string it is in the URL. */
    itemId?: string;
}

export function viewToken(view: ViewDescriptor): string {
    return view.viewKind === 'edit'
        ? `${view.entityName}.edit.${view.itemId}`
        : `${view.entityName}.${view.viewKind}`;
}

/**
 * The reverse of [viewToken]. A kebab-case entity name never contains a dot, which is what
 * makes the first two segments unambiguous. Returns null for anything else, so that a
 * hand-edited URL loses the tab instead of breaking the tab bar.
 */
export function parseViewToken(token: string): ViewDescriptor | null {
    const [entityName, viewKind, ...itemIdSegments] = token.split('.');
    if (entityName === '') {
        return null;
    }
    if (viewKind === 'board' || viewKind === 'create') {
        return itemIdSegments.length === 0 ? {entityName, viewKind} : null;
    }
    if (viewKind === 'edit') {
        const itemId = itemIdSegments.join('.');
        return itemId === '' ? null : {entityName, viewKind, itemId};
    }
    return null;
}
