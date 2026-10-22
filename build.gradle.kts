buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        // Flyway discovers its database support modules through the class loader that loaded
        // flyway-core, which is this buildscript class loader. Putting them on the Flyway task
        // classpath instead (the plugin's `configurations` setting) leaves them undiscoverable.
        classpath("org.flywaydb:flyway-database-postgresql:${property("flywayVersion")}")
        classpath("org.postgresql:postgresql:${property("postgresqlVersion")}")
    }
}

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.flyway) apply false
}

group = "alpstein"
version = "0.0.1-SNAPSHOT"
