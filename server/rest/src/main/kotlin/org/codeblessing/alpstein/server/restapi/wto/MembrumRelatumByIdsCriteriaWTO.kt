package org.codeblessing.alpstein.server.restapi.wto

import java.util.UUID

data class MembrumRelatumByIdsCriteriaWTO(
    val clavisPrimariaList: List<UUID> = emptyList(),
)
