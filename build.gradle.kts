import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    kotlin("jvm") version "2.0.0"
    application
    id("com.adarshr.test-logger") version "4.0.0"
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

testlogger {
    theme = ThemeType.MOCHA
    showStandardStreams = true
    showPassedStandardStreams = false
    showSkippedStandardStreams = false
    showFailedStandardStreams = true
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
    standardOutput = System.`out`
}

application { mainClass.set("com.craftinginterpreters.lox.LoxKt") }

kotlin { jvmToolchain(21) }
