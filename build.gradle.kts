plugins {
    kotlin("jvm") version "2.0.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

group = "dev.mshlz"
version = "1.0-SNAPSHOT"


application {
    // Define the main class for the application.
    mainClass = "dev.mshlz.MainKt"
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

buildscript {
    repositories { mavenCentral() }
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}