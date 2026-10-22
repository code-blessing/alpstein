package org.codeblessing.alpstein.server.restapi.wto.mapper

import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumSearchCriteriaWTO
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumSearchCriteriaBO

object MembrumRelatumSearchCriteriaMapper {

    fun MembrumRelatumSearchCriteriaWTO.toBo(): MembrumRelatumSearchCriteriaBO = MembrumRelatumSearchCriteriaBO(
        query = query,
    )
}
