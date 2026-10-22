package org.codeblessing.alpstein.server.restapi.wto.mapper

import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumByIdsCriteriaWTO
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumByIdsCriteriaBO

object MembrumRelatumByIdsCriteriaMapper {

    fun MembrumRelatumByIdsCriteriaWTO.toBo(): MembrumRelatumByIdsCriteriaBO = MembrumRelatumByIdsCriteriaBO(
        clavisPrimariaList = clavisPrimariaList,
    )
}
