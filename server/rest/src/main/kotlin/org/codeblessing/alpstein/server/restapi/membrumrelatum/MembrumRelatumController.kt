package org.codeblessing.alpstein.server.restapi.membrumrelatum

import org.springframework.web.bind.annotation.*
import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumByIdsCriteriaWTO
import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumByIdsResultWTO
import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumSearchCriteriaWTO
import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumSearchResultWTO
import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumWTO
import org.codeblessing.alpstein.server.restapi.wto.mapper.MembrumRelatumMapper
import org.codeblessing.alpstein.server.restapi.wto.mapper.MembrumRelatumByIdsCriteriaMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.MembrumRelatumSearchCriteriaMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.MembrumRelatumMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.MembrumRelatumMapper.toWto
import org.codeblessing.alpstein.server.service.membrumrelatum.MembrumRelatumService
import java.util.UUID

/**
 * REST endpoints of the MembrumRelatum business context. Served under `/api/membrum-relatum`
 * (the `/api` prefix is added by `WebConfig`) and consumed by the Angular
 * `MembrumRelatumService`.
 *
 * The controller speaks WTOs to the outside world, maps them to BOs via
 * [MembrumRelatumMapper] and always calls the [MembrumRelatumService] with the whole
 * `MembrumRelatum` aggregate.
 */
@RestController
@RequestMapping("/membrum-relatum")
class MembrumRelatumController(
    private val membrumRelatumService: MembrumRelatumService,
) {

    @GetMapping
    fun getMembrumRelatumList(): List<MembrumRelatumWTO> =
        membrumRelatumService.getMembrumRelatumList().map { it.toWto() }

    @PostMapping("/search")
    fun searchMembrumRelatumList(@RequestBody searchCriteria: MembrumRelatumSearchCriteriaWTO): MembrumRelatumSearchResultWTO =
        MembrumRelatumSearchResultWTO(
            membrumRelatumList = membrumRelatumService.searchMembrumRelatumList(searchCriteria.toBo()).map { it.toWto() },
        )

    @PostMapping("/by-ids")
    fun getMembrumRelatumListByIds(@RequestBody criteria: MembrumRelatumByIdsCriteriaWTO): MembrumRelatumByIdsResultWTO =
        MembrumRelatumByIdsResultWTO(
            membrumRelatumList = membrumRelatumService.getMembrumRelatumListByIds(criteria.toBo()).map { it.toWto() },
        )

    @GetMapping("/{clavisPrimaria}")
    fun getMembrumRelatumById(@PathVariable clavisPrimaria: UUID): MembrumRelatumWTO? =
        membrumRelatumService.getMembrumRelatumById(clavisPrimaria)?.toWto()

    @PostMapping
    fun createMembrumRelatum(@RequestBody membrumRelatum: MembrumRelatumWTO): MembrumRelatumWTO {
        val created = membrumRelatumService.createMembrumRelatum(membrumRelatum.toBo())
        return created.toWto()
    }

    @PutMapping("/{clavisPrimaria}")
    fun updateMembrumRelatum(
        @PathVariable clavisPrimaria: UUID,
        @RequestBody membrumRelatum: MembrumRelatumWTO,
    ): MembrumRelatumWTO {
        val toUpdate = membrumRelatum.toBo().copy(clavisPrimaria = clavisPrimaria)
        return membrumRelatumService.updateMembrumRelatum(toUpdate).toWto()
    }

    @DeleteMapping("/{clavisPrimaria}")
    fun deleteMembrumRelatum(@PathVariable clavisPrimaria: UUID) =
        membrumRelatumService.deleteMembrumRelatum(clavisPrimaria)
}
