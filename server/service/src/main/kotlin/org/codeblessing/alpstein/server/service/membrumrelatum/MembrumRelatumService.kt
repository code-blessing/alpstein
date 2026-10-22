package org.codeblessing.alpstein.server.service.membrumrelatum

import org.springframework.stereotype.Service
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumBO
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumByIdsCriteriaBO
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumSearchCriteriaBO
import java.util.UUID

/**
 * Business service of the MembrumRelatum business context. It is called by the REST layer
 * with [MembrumRelatumBO] business objects (never WTOs) and delegates persistence to the
 * [MembrumRelatumRepository] port.
 *
 * Every operation works on the whole [MembrumRelatumBO] aggregate as a single unit.
 */
@Service
class MembrumRelatumService(
    private val membrumRelatumRepository: MembrumRelatumRepository,
) {

    fun getMembrumRelatumList(): List<MembrumRelatumBO> = membrumRelatumRepository.findAll()

    fun getMembrumRelatumById(clavisPrimaria: UUID): MembrumRelatumBO? =
        membrumRelatumRepository.findById(clavisPrimaria)

    fun getMembrumRelatumListByIds(criteria: MembrumRelatumByIdsCriteriaBO): List<MembrumRelatumBO> =
        membrumRelatumRepository.findByIds(criteria)

    fun searchMembrumRelatumList(searchCriteria: MembrumRelatumSearchCriteriaBO): List<MembrumRelatumBO> =
        membrumRelatumRepository.search(searchCriteria)

    fun createMembrumRelatum(membrumRelatum: MembrumRelatumBO): MembrumRelatumBO {
        val toCreate = membrumRelatum.copy(clavisPrimaria = UUID.randomUUID())
        return membrumRelatumRepository.save(toCreate)
    }

    fun updateMembrumRelatum(membrumRelatum: MembrumRelatumBO): MembrumRelatumBO =
        membrumRelatumRepository.save(membrumRelatum)

    fun deleteMembrumRelatum(clavisPrimaria: UUID) = membrumRelatumRepository.deleteById(clavisPrimaria)
}
