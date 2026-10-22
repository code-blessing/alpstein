package org.codeblessing.alpstein.server.restapi

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Prefixes every [RestController] endpoint with a shared `/api` path, so e.g. `/greeting`
 * is served under `/api/greeting`. New controllers are picked up automatically.
 */
@Configuration
class WebConfig : WebMvcConfigurer {

    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        configurer.addPathPrefix("/api") { it.isAnnotationPresent(RestController::class.java) }
    }

    /**
     * Allows the Angular dev server to call the `/api/...` endpoints from a different origin.
     * The browser otherwise blocks requests from `http://localhost:4200` to `http://localhost:8080`.
     */
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:5200")
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowedHeaders("*")
    }
}
