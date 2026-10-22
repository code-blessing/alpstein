package org.codeblessing.alpstein.server.restapi.wto

import java.util.UUID

/**
 * Root WTO (Web Transfer Object), mirroring the Angular `MembrumRelatumWTO` interface
 * field by field so it serializes 1:1 for the client.
 */
data class MembrumRelatumWTO(
    val clavisPrimaria: UUID,
    val descriptioExDistanti: String,
)
