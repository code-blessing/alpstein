package org.codeblessing.alpstein.server.service

import org.codeblessing.alpstein.server.service.greeting.GreetingRepository
import org.codeblessing.alpstein.server.service.greeting.GreetingService
import kotlin.test.Test
import kotlin.test.assertEquals

class GreetingServiceTest {

    /** Lightweight in-memory fake of the [org.codeblessing.alpstein.server.service.greeting.GreetingRepository] port. */
    private class FakeGreetingRepository : GreetingRepository {
        val saved = mutableListOf<String>()
        override fun save(greeting: String) {
            saved.add(greeting)
        }

        override fun findAll(): List<String> = saved
    }

    @Test
    fun `greet builds greeting and stores it`() {
        val repository = FakeGreetingRepository()
        val service = GreetingService(repository)

        val result = service.greet("World")

        assertEquals("Hello, World!", result)
        assertEquals(listOf("Hello, World!"), repository.saved)
    }

    @Test
    fun `history returns all stored greetings`() {
        val repository = FakeGreetingRepository()
        val service = GreetingService(repository)

        service.greet("Alice")
        service.greet("Bob")

        assertEquals(listOf("Hello, Alice!", "Hello, Bob!"), service.history())
    }
}
