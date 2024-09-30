plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com"
version = "1.0-SNAPSHOT"
val kotestVersion = "5.9.1"
val mockkVersion = "1.13.12"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
