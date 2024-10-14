plugins {
    kotlin("jvm") version "2.0.20"
    id("dev.zacsweers.redacted") version "1.10.0"
    application
}
group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

redacted {
    redactedAnnotation = "redacted/Redacted"
    replacementString = "*"
}

application {
    mainClass = "redacted.MainKt"
}