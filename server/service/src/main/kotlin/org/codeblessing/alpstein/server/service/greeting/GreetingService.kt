package org.codeblessing.alpstein.server.service.greeting

import org.springframework.stereotype.Service

/**
 * Simple example business service. It builds a greeting, stores it through the
 * [GreetingRepository] port and can return the history of all greetings.
 */
@Service
class GreetingService(
    private val greetingRepository: GreetingRepository,
) {

    fun greet(name: String): String {
        val greeting = "Hello, $name!"
        greetingRepository.save(greeting)
        return greeting
    }

    fun history(): List<String> = greetingRepository.findAll()
}
