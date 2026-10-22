package org.codeblessing.alpstein.server.exampledata.membrumrelatum

import org.springframework.stereotype.Component
import org.codeblessing.alpstein.server.exampledata.DataContext
import org.codeblessing.alpstein.server.exampledata.framework.datafaker.FakerHelper
import org.codeblessing.alpstein.server.service.bo.MembrumRelatumBO
import org.codeblessing.alpstein.server.service.membrumrelatum.MembrumRelatumRepository
import java.util.UUID

/**
 * Fetches already persisted MembrumRelatum example data so that other example data creators can
 * reference it.
 *
 * Unlike the example data creators, this does not create anything: it reads the
 * [MembrumRelatumBO] instances that the [MembrumRelatumExampleDataPopulator] has written before,
 * so that a reference is always a valid one. It therefore only returns something once
 * MembrumRelatum example data has been created.
 */
@Component
class MembrumRelatumExampleDataFetcher(
    private val membrumRelatumRepository: MembrumRelatumRepository,
) {

    /**
     * A random subset of the clavisPrimaria of the existing [MembrumRelatumBO] instances, or an
     * empty list if none exist yet.
     */
    fun fetchRandomKeysList(dataContext: DataContext): List<UUID> =
        FakerHelper.manyOfRandom(
            dataContext = dataContext,
            array = membrumRelatumRepository.findAll().toTypedArray(),
            size = FakerHelper.referenceListRandomSize(dataContext),
        ).map { it.clavisPrimaria }

    /**
     * The clavisPrimaria of one random existing [MembrumRelatumBO], or `null` if none exist yet.
     */
    fun fetchRandomKey(dataContext: DataContext): UUID? {
        val membrumRelatumList = membrumRelatumRepository.findAll()
        if (membrumRelatumList.isEmpty()) {
            return null
        }
        return FakerHelper.oneRandomOf(
            dataContext = dataContext,
            array = membrumRelatumList.toTypedArray(),
        ).clavisPrimaria
    }
}
