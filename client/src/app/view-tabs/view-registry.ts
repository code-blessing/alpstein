import {Type} from '@angular/core';
import {ViewKind} from '@app/view-tabs/view-descriptor';

/**
 * The components and titles of the views of one UiEntity, so that a tab can be filled from
 * nothing but the entity name in its URL token.
 */
export interface ViewEntityRegistration {
    /** The kebab-case name of the UiEntity, as it appears in the URL token of its views. */
    entityName: string;
    boardComponent: Type<unknown>;
    /** Edits an existing item or creates a new one, depending on its `itemId` input. */
    editorComponent: Type<unknown>;
    titleTranslationKeys: Record<ViewKind, string>;
}
