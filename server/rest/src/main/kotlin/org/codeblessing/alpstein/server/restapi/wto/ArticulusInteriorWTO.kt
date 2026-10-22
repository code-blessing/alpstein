package org.codeblessing.alpstein.server.restapi.wto

/**
 * WTO (Web Transfer Object) mirroring the Angular `ArticulusInteriorWTO` interface.
 */
data class ArticulusInteriorWTO(
    val scriptumTriviale: String,
    val numerusStupidus: Int,
)
