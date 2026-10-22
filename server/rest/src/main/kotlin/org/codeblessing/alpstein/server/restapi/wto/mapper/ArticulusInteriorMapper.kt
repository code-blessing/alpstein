package org.codeblessing.alpstein.server.restapi.wto.mapper

import org.codeblessing.alpstein.server.restapi.wto.ArticulusInteriorWTO
import org.codeblessing.alpstein.server.service.bo.ArticulusInteriorBO

/**
 * Maps between the WTOs (transport layer) and BOs (business layer).
 */
object ArticulusInteriorMapper {

    fun ArticulusInteriorWTO.toBo() = ArticulusInteriorBO(
        scriptumTriviale = scriptumTriviale,
        numerusStupidus = numerusStupidus,
    )

    fun ArticulusInteriorBO.toWto() = ArticulusInteriorWTO(
        scriptumTriviale = scriptumTriviale,
        numerusStupidus = numerusStupidus,
    )
}
