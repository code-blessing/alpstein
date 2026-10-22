/* @tt{{{

    @move-comment-backward
    @template-renderer [
        templateRendererClassName="ItemControllerRenderer"
        templateRendererPackageName="org.codeblessing.alpstein.codegen.renderer.be"
        templateRendererInterfaceName="BeItemRenderer"
        templateRendererInterfacePackageName="org.codeblessing.alpstein.codegen.renderer.be"
    ] [
        modelClassName="BeItemModel"
        modelPackageName="org.codeblessing.alpstein.codegen.renderer.model.be"
        modelName="model"
    ]

    @replace-value-by-expression
        [ searchValue="SilvaOptionum" replaceByExpression="model.itemName.pascalCase" ]
        [ searchValue="silvaOptionum" replaceByExpression="model.itemName.camelCase" ]
        [ searchValue="silva-optionum" replaceByExpression="model.itemName.kebabCase" ]
        [ searchValue="silvaoptionum" replaceByExpression="model.itemName.lowerCase" ]

    @replace-value-by-expression
        [ searchValue="indexUnicus" replaceByExpression="model.primaryKeyAttribute.attributeName.camelCase" ]
        [ searchValue="UUID" replaceByExpression="model.primaryKeyAttribute.kotlinAttributeType" ]

    @modify-provided-filepath-by-replacements

}}}@ */
package org.codeblessing.alpstein.server.restapi.silvaoptionum

import org.springframework.web.bind.annotation.*
import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumByIdsCriteriaWTO
import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumByIdsResultWTO
import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumSearchCriteriaWTO
import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumSearchResultWTO
import org.codeblessing.alpstein.server.restapi.wto.SilvaOptionumWTO
import org.codeblessing.alpstein.server.restapi.wto.mapper.SilvaOptionumMapper
import org.codeblessing.alpstein.server.restapi.wto.mapper.SilvaOptionumByIdsCriteriaMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.SilvaOptionumSearchCriteriaMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.SilvaOptionumMapper.toBo
import org.codeblessing.alpstein.server.restapi.wto.mapper.SilvaOptionumMapper.toWto
import org.codeblessing.alpstein.server.service.silvaoptionum.SilvaOptionumService
/* @tt{{{   @if [ conditionExpression="model.hasUuidPrimaryKey"]  }}}@ */
import java.util.UUID
/* @tt{{{   @end-if  }}}@ */

/**
 * REST endpoints of the SilvaOptionum business context. Served under `/api/silva-optionum`
 * (the `/api` prefix is added by `WebConfig`) and consumed by the Angular
 * `SilvaOptionumService`.
 *
 * The controller speaks WTOs to the outside world, maps them to BOs via
 * [SilvaOptionumMapper] and always calls the [SilvaOptionumService] with the whole
 * `SilvaOptionum` aggregate.
 */
@RestController
@RequestMapping("/silva-optionum")
class SilvaOptionumController(
    private val silvaOptionumService: SilvaOptionumService,
) {

    @GetMapping
    fun getSilvaOptionumList(): List<SilvaOptionumWTO> =
        silvaOptionumService.getSilvaOptionumList().map { it.toWto() }

    @PostMapping("/search")
    fun searchSilvaOptionumList(@RequestBody searchCriteria: SilvaOptionumSearchCriteriaWTO): SilvaOptionumSearchResultWTO =
        SilvaOptionumSearchResultWTO(
            silvaOptionumList = silvaOptionumService.searchSilvaOptionumList(searchCriteria.toBo()).map { it.toWto() },
        )

    /**
     * Resolves a whole set of references to this item at once, so that the client can show
     * stored identifiers by their display attributes instead of the bare primary keys.
     */
    @PostMapping("/by-ids")
    fun getSilvaOptionumListByIds(@RequestBody criteria: SilvaOptionumByIdsCriteriaWTO): SilvaOptionumByIdsResultWTO =
        SilvaOptionumByIdsResultWTO(
            silvaOptionumList = silvaOptionumService.getSilvaOptionumListByIds(criteria.toBo()).map { it.toWto() },
        )

    @GetMapping("/{indexUnicus}")
    fun getSilvaOptionumById(@PathVariable indexUnicus: UUID): SilvaOptionumWTO? =
        silvaOptionumService.getSilvaOptionumById(indexUnicus)?.toWto()

    @PostMapping
    fun createSilvaOptionum(@RequestBody silvaOptionum: SilvaOptionumWTO): SilvaOptionumWTO {
        val created = silvaOptionumService.createSilvaOptionum(silvaOptionum.toBo())
        return created.toWto()
    }

    @PutMapping("/{indexUnicus}")
    fun updateSilvaOptionum(
        @PathVariable indexUnicus: UUID,
        @RequestBody silvaOptionum: SilvaOptionumWTO,
    ): SilvaOptionumWTO {
        val toUpdate = silvaOptionum.toBo().copy(indexUnicus = indexUnicus)
        return silvaOptionumService.updateSilvaOptionum(toUpdate).toWto()
    }

    @DeleteMapping("/{indexUnicus}")
    fun deleteSilvaOptionum(@PathVariable indexUnicus: UUID) =
        silvaOptionumService.deleteSilvaOptionum(indexUnicus)
}
