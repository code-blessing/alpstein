import {MembrumRelatumWTO} from "@app/wto/membrum-relatum.wto";

/**
 * The MembrumRelatum by-ids result WTO (Web Transfer Object), returned by the by-ids
 * endpoint. It wraps the resolved MembrumRelatum in the order they were requested;
 * unknown clavisPrimaria are omitted.
 */
export interface MembrumRelatumByIdsResultWTO {
    membrumRelatumList: Array<MembrumRelatumWTO>;
}
