package org.codeblessing.alpstein.server.service.greeting

/**
 * Port for persisting greetings. The implementation lives in the persistence module,
 * so the service (business) layer stays independent of any persistence technology.
 */
interface GreetingRepository {

    fun save(greeting: String)

    fun findAll(): List<String>
}
