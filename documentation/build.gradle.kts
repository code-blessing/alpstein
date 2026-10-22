val directoryForGeneratedSource = "generated-documentation"
tasks.register<Delete>("cleanGeneratedSources") {
    delete(projectDir.resolve(directoryForGeneratedSource))
}

tasks.register("clean") {
    dependsOn("cleanGeneratedSources")
}
