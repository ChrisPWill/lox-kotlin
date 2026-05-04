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

val generateAstScripts =
    tasks.register<CreateStartScripts>("generateAstScripts") {
        mainClass.set("com.craftinginterpreters.tool.GenerateAstKt")
        applicationName = "generateAst"
        outputDir = file(layout.buildDirectory.dir("scripts-ast"))

        // Use the classpath from the main startScripts task
        val startScripts = tasks.named<CreateStartScripts>("startScripts").get()
        classpath = startScripts.classpath
    }

tasks.register<JavaExec>("generateAst") {
    group = "tool"
    mainClass.set("com.craftinginterpreters.tool.GenerateAstKt")
    classpath = sourceSets["main"].runtimeClasspath
    args("src/main/kotlin/com/craftinginterpreters/lox")
}

distributions {
    main {
        contents {
            from(generateAstScripts) {
                into("bin")
                fileMode = 755
            }
        }
    }
}

kotlin { jvmToolchain(21) }
