package org.codeblessing.alpstein.server.service.bo

import java.util.UUID

/**
 * Business object for the [MembrumRelatumBO] item.
 */
data class MembrumRelatumBO(
    val clavisPrimaria: UUID,
    val descriptioExDistanti: String,
)
