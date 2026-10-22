package org.codeblessing.alpstein.server.restapi.wto.mapper

import org.codeblessing.alpstein.server.restapi.wto.MembrumRelatumWTO
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumBO

/**
 * Maps between the WTOs (transport layer) and BOs (business layer).
 */
object MembrumRelatumMapper {

    fun MembrumRelatumWTO.toBo(): MembrumRelatumBO = MembrumRelatumBO(
        clavisPrimaria = clavisPrimaria,
        descriptioExDistanti = descriptioExDistanti,
    )

    fun MembrumRelatumBO.toWto(): MembrumRelatumWTO = MembrumRelatumWTO(
        clavisPrimaria = clavisPrimaria,
        descriptioExDistanti = descriptioExDistanti,
    )
}
