package org.codeblessing.alpstein.server.service.bo

/**
 * Business object for the search criteria of the MembrumRelatum aggregates.
 *
 * Holds a single free text [query]; a blank query matches every [MembrumRelatumBO].
 */
data class MembrumRelatumSearchCriteriaBO(
    val query: String,
)
