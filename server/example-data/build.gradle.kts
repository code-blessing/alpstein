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
    implementation(libs.datafaker)

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
