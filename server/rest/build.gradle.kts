import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom(SpringBootPlugin.BOM_COORDINATES)
    }
}

val directoryForGeneratedSource = "src/generated/kotlin"

kotlin {
    sourceSets["main"].kotlin.srcDir(directoryForGeneratedSource)
}

dependencies {
    implementation(project(":server:service"))

    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // Required so Jackson can (de)serialize the Kotlin data class WTOs from request bodies.
    // Must be the Jackson 3 (`tools.jackson`) module: Spring Boot 4 binds request bodies with
    // Jackson 3, so the `com.fasterxml.jackson` (Jackson 2) module is never registered there.
    implementation("tools.jackson.module:jackson-module-kotlin")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")
}

tasks.named("compileKotlin") {
    dependsOn( ":code-generation:code-generator:codegen")
}

tasks.register<Delete>("cleanGeneratedSources") {
    delete(projectDir.resolve(directoryForGeneratedSource))
}

tasks.named("clean") {
    dependsOn("cleanGeneratedSources")
}
