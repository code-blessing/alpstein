import {ViewOpenMode} from "@app/view-tabs/view-descriptor";

export interface SideNavLink {
    routeLink: string;
    nameTranslationKey: string;
    icon: string | undefined;
    /** How the route of this link opens its view. Defaults to a new tab. */
    openMode?: ViewOpenMode;
}
