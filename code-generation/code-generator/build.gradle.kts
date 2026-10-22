plugins {
    kotlin("jvm")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":essential-model-data"))
    implementation(libs.sourceamazing.schema.api)
    runtimeOnly(libs.sourceamazing.schema)
    implementation(libs.sourceamazing.builder.api)
    runtimeOnly(libs.sourceamazing.builder)
    runtimeOnly(libs.kotlin.reflect)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val directoryForGeneratedTemplateRenderer = "src/tavnit-generated/kotlin"
val directoryForManualTemplateRenderer = "src/tavnit-manual/kotlin"

val angularClientProjectPath: File = project(":client").projectDir
val serverRestProjectPath: File = project(":server:rest").projectDir
val serverServiceProjectPath: File = project(":server:service").projectDir
val serverPersistenceProjectPath: File = project(":server:persistence").projectDir
val serverExampleDataProjectPath: File = project(":server:example-data").projectDir
val databaseDslSchemaProjectPath: File = project(":database:postgresql-dsl-schema").projectDir
val documentationPath: File = project(":documentation").projectDir

val directoryForAngularGeneratedSource = angularClientProjectPath.resolve("src/app-generated")
val directoryForRestGeneratedSource = serverRestProjectPath.resolve("src/generated/kotlin")
val directoryForServiceGeneratedSource = serverServiceProjectPath.resolve("src/generated/kotlin")
val directoryForPersistenceGeneratedSource = serverPersistenceProjectPath.resolve("src/generated/kotlin")
val directoryForExampleDataGeneratedSource = serverExampleDataProjectPath.resolve("src/generated/kotlin")
val directoryForDatabaseMigrationGeneratedSource = databaseDslSchemaProjectPath.resolve("db/generated-migration")
val directoryForDocumentationGeneratedSource = documentationPath.resolve("generated-documentation")

kotlin {
    sourceSets["main"].kotlin.srcDir(directoryForGeneratedTemplateRenderer)
    sourceSets["main"].kotlin.srcDir(directoryForManualTemplateRenderer)
}

tasks.register<JavaExec>("codegen") {
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("org.codeblessing.alpstein.codegen.CodeGenExecutorKt")

    args(
        directoryForAngularGeneratedSource,
        directoryForRestGeneratedSource,
        directoryForServiceGeneratedSource,
        directoryForPersistenceGeneratedSource,
        directoryForExampleDataGeneratedSource,
        directoryForDatabaseMigrationGeneratedSource,
        directoryForDocumentationGeneratedSource,
    )

    dependsOn("cleanCodegen")
}

tasks.register<Delete>("cleanCodegen") {
    delete(
        directoryForAngularGeneratedSource,
        directoryForExampleDataGeneratedSource,
        directoryForPersistenceGeneratedSource,
        directoryForRestGeneratedSource,
        directoryForServiceGeneratedSource,
        directoryForDatabaseMigrationGeneratedSource,
    )
}

tasks.named("clean") {
    dependsOn("cleanCodegen")
}

tasks.named("compileKotlin") {
    dependsOn(":code-generation:renderer-creator:createTavnitRenderers")
}
