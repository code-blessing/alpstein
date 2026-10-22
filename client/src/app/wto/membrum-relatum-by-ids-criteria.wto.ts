import {UUID} from "@app/shared/uuid";

/**
 * The MembrumRelatum by-ids criteria WTO (Web Transfer Object), sent as the request body
 * of the by-ids endpoint. It carries the clavisPrimaria of every MembrumRelatum to resolve.
 */
export interface MembrumRelatumByIdsCriteriaWTO {
    clavisPrimariaList: Array<UUID>;
}
