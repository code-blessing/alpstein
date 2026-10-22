import {ViewEntityRegistration} from "@app/view-tabs/view-registry";
import {ENTITY_VIEW_REGISTRATIONS} from "@app/entity-view-registry";
import {GENERATED_ENTITY_VIEW_REGISTRATIONS} from "@app/generated-entity-view-registry";

export const VIEW_REGISTRATIONS: ReadonlyArray<ViewEntityRegistration> = [
    ...ENTITY_VIEW_REGISTRATIONS,
    ...GENERATED_ENTITY_VIEW_REGISTRATIONS,
];
