rootProject.name = "alpstein"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            val kotlinVersion = version("kotlin", "2.3.21")
            val springBootVersion = version("springBoot", "4.1.0")
            val tavnitVersion = version("tavnit", "1.0.1")
            val sourceAmazingVersion = version("sourceAmazing", "4.0.1")
            val datafakerVersion = version("datafaker", "2.7.0")
            val flywayVersion = version("flyway", providers.gradleProperty("flywayVersion").get())

            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib").versionRef(kotlinVersion)
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef(kotlinVersion)
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef(kotlinVersion)
            plugin("kotlin-spring", "org.jetbrains.kotlin.plugin.spring").versionRef(kotlinVersion)

            plugin("spring-boot", "org.springframework.boot").versionRef(springBootVersion)
            plugin("spring-dependency-management", "io.spring.dependency-management").version("1.1.7")

            library("tavnit-api", "org.codeblessing.tavnit", "tavnit-api").versionRef(tavnitVersion)
            library("tavnit", "org.codeblessing.tavnit", "tavnit").versionRef(tavnitVersion)

            library(
                "sourceamazing-schema-api",
                "org.codeblessing.sourceamazing",
                "sourceamazing-schema-api",
            ).versionRef(sourceAmazingVersion)
            library("sourceamazing-schema", "org.codeblessing.sourceamazing", "sourceamazing-schema").versionRef(sourceAmazingVersion)
            library(
                "sourceamazing-builder-api",
                "org.codeblessing.sourceamazing",
                "sourceamazing-builder-api",
            ).versionRef(sourceAmazingVersion)
            library("sourceamazing-builder", "org.codeblessing.sourceamazing", "sourceamazing-builder").versionRef(sourceAmazingVersion)
            library(
                "sourceamazing-xmlschema-api",
                "org.codeblessing.sourceamazing",
                "sourceamazing-xml-schema-api",
            ).versionRef(sourceAmazingVersion)
            library(
                "sourceamazing-xmlschema",
                "org.codeblessing.sourceamazing",
                "sourceamazing-xml-schema",
            ).versionRef(sourceAmazingVersion)

            library("datafaker", "net.datafaker", "datafaker").versionRef(datafakerVersion)

            plugin("flyway", "org.flywaydb.flyway").versionRef(flywayVersion)
        }
    }
}

include("client")
include("database:postgresql-dsl-schema")
include("database:postgresql-sakila-db-dump")
include("documentation")
include("essential-model-data")
include("server:app")
include("server:service")
include("server:example-data")
include("server:rest")
include("server:persistence")
include("code-generation:renderer-creator")
include("code-generation:code-generator")
