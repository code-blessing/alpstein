plugins {
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(libs.tavnit.api)
    runtimeOnly(libs.tavnit)
}

val targetDirectoryForTemplateRenderer = project(":code-generation:code-generator").projectDir.resolve("src/tavnit-generated/kotlin")

tasks.register<JavaExec>("createTavnitRenderers") {
    val angularClientProjectPath = project(":client").projectDir
    val serverServiceProjectPath = project(":server:service").projectDir
    val serverRestProjectPath = project(":server:rest").projectDir
    val serverPersistenceProjectPath = project(":server:persistence").projectDir
    val serverExampleDataProjectPath = project(":server:example-data").projectDir
    val databaseDslSchemaProjectPath = project(":database:postgresql-dsl-schema").projectDir
    val documentationProjectPath = project(":documentation").projectDir

    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("org.codeblessing.alpstein.codegen.renderercreator.TavnitRendererCreatorKt")

    args(
        targetDirectoryForTemplateRenderer.absolutePath,
        angularClientProjectPath.resolve("src/app").absolutePath,
        serverRestProjectPath.resolve("src/main/kotlin").absolutePath,
        serverServiceProjectPath.resolve("src/main/kotlin").absolutePath,
        serverPersistenceProjectPath.resolve("src/main/kotlin").absolutePath,
        serverExampleDataProjectPath.resolve("src/main/kotlin").absolutePath,
        databaseDslSchemaProjectPath.resolve("db/migration").absolutePath,
        documentationProjectPath.absolutePath,
    )

    dependsOn("cleanRenderers")
}

tasks.register<Delete>("cleanRenderers") {
    delete(targetDirectoryForTemplateRenderer)
}

tasks.named("clean") {
    dependsOn("cleanRenderers")
}
