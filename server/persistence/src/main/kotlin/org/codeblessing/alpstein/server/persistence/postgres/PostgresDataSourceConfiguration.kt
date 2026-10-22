package org.codeblessing.alpstein.server.persistence.postgres

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * Activates Spring Boot's [DataSourceAutoConfiguration] for `org.codeblessing.alpstein.persistence.type=postgres`.
 *
 * `spring-boot-starter-jdbc` is on the classpath in every mode, so that auto configuration would
 * also run for the in-memory repositories and abort the startup with "Failed to determine a
 * suitable driver class" whenever no `spring.datasource.url` is set. `application.properties`
 * therefore excludes it globally, and it is imported back here with a plain [Import], which is not
 * an auto configuration import and therefore unaffected by `spring.autoconfigure.exclude`.
 *
 * The JdbcClient, transaction manager and initialization auto configurations need no such
 * treatment — they are conditional on a DataSource bean and stay dormant without one.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = ["org.codeblessing.alpstein.persistence.type"], havingValue = "postgres")
@Import(DataSourceAutoConfiguration::class)
class PostgresDataSourceConfiguration
