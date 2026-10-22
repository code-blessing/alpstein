import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";

/**
 * The MembrumRelatum search result WTO (Web Transfer Object), returned by the search
 * endpoint. It wraps the found MembrumRelatum.
 */
export interface MembrumRelatumSearchResultWTO {
    membrumRelatumList: Array<MembrumRelatumWTO>;
}
