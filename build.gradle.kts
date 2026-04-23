plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "com.craftinginterpreters.lox"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test { useJUnitPlatform() }

application { mainClass.set("com.craftinginterpreters.lox.LoxKt") }

kotlin { jvmToolchain(21) }
