package org.codeblessing.alpstein.server.persistence.greeting

import org.springframework.stereotype.Repository
import org.codeblessing.alpstein.server.service.greeting.GreetingRepository
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Simple in-memory implementation of the [org.codeblessing.alpstein.server.service.greeting.GreetingRepository] port defined in the
 * service module. Holds greetings in memory only; a real persistence framework can
 * replace this later without touching the service layer.
 */
@Repository
class InMemoryGreetingRepository : GreetingRepository {

    private val greetings = CopyOnWriteArrayList<String>()

    override fun save(greeting: String) {
        greetings.add(greeting)
    }

    override fun findAll(): List<String> = greetings.toList()
}
