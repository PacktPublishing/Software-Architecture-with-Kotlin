plugins {
    application
    alias(libs.plugins.kotlin)
    alias(libs.plugins.openapi)
    alias(libs.plugins.ktor)
}

group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.bundles.ktor.client)
    testImplementation(libs.mockk)
    testImplementation(libs.moshi)
    implementation(libs.bundles.ktor.server)
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

sourceSets {
    test {
        kotlin {
            srcDirs("build/generated-sources/api/src/main/kotlin")
        }
    }
}

openApiValidate {
    inputSpec.set("$rootDir/src/main/resources/openapi.yaml")
}
openApiGenerate {
    inputSpec.set("$rootDir/src/main/resources/openapi.yaml")
    outputDir.set("$rootDir/build/generated-sources/api")
    generatorName.set("kotlin")
    library.set("jvm-ktor")
    apiPackage.set("example.api")
    invokerPackage.set("example.invoker")
    modelPackage.set("example.model")
}
tasks.compileKotlin {
    dependsOn(tasks.openApiValidate, tasks.openApiGenerate)
}

application {
    mainClass.set("example.server.ApplicationKt")
}
