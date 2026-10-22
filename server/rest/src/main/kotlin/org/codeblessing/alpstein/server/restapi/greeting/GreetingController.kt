package org.codeblessing.alpstein.server.restapi.greeting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.codeblessing.alpstein.server.service.greeting.GreetingService

/**
 * Example REST endpoint that delegates to the [org.codeblessing.alpstein.server.service.greeting.GreetingService] in the service module.
 */
@RestController
class GreetingController(
    private val greetingService: GreetingService,
) {

    @GetMapping("/greeting")
    fun greeting(@RequestParam(defaultValue = "World") name: String): String =
        greetingService.greet(name)

    @GetMapping("/greeting/history")
    fun history(): List<String> = greetingService.history()
}
