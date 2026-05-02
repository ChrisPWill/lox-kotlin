plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "com.craftinginterpreters.lox"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("io.kotest:kotest-property:5.8.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

application { mainClass.set("com.craftinginterpreters.lox.LoxKt") }

kotlin { jvmToolchain(21) }
