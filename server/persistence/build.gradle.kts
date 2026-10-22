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

    implementation("org.springframework:spring-context")

    // JDBC access for the PostgreSQL repositories: brings spring-jdbc, HikariCP and the
    // auto configuration that builds the DataSource/JdbcClient from the spring.datasource.* properties.
    implementation("org.springframework.boot:spring-boot-starter-jdbc")

    // Serializes the attributes that have no flat relational representation — nested items and
    // lists of them — into jsonb columns.
    implementation("tools.jackson.module:jackson-module-kotlin")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
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
