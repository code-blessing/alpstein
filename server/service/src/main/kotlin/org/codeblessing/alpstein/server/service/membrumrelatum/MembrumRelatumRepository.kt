package org.codeblessing.alpstein.server.service.membrumrelatum

import org.codeblessing.alpstein.server.service.bo.MembrumRelatumBO
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumByIdsCriteriaBO
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumSearchCriteriaBO
import java.util.UUID

/**
 * Port for persisting the MembrumRelatum root object [MembrumRelatumBO]. The implementation
 * lives in the persistence module, so the service (business) layer stays independent of
 * any persistence technology.
 *
 * Always operates on the whole [MembrumRelatumBO] aggregate, never on nested items.
 */
interface MembrumRelatumRepository {

    fun findAll(): List<MembrumRelatumBO>

    fun findById(clavisPrimaria: UUID): MembrumRelatumBO?

    fun findByIds(criteria: MembrumRelatumByIdsCriteriaBO): List<MembrumRelatumBO>

    fun search(searchCriteria: MembrumRelatumSearchCriteriaBO): List<MembrumRelatumBO>

    fun save(membrumRelatum: MembrumRelatumBO): MembrumRelatumBO

    fun deleteById(clavisPrimaria: UUID)
}
