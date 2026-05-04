package com.craftinginterpreters.tool

import java.io.File
import java.io.IOException
import java.io.PrintWriter
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println("Usage: generateAst <output-directory>")
        exitProcess(64)
    }

    val outputDir = args[0]

    defineAst(
        outputDir,
        "Expr",
        listOf(
            "Binary    : Expr left, Token operator, Expr right",
            "Grouping  : Expr expression",
            "Literal   : Object value",
            "Unary     : Token operator, Expr right",
        ),
    ).onFailure {
        System.err.println("Error: ${it.message}")
        exitProcess(1)
    }
}

private fun defineAst(
    outputDir: String,
    baseName: String,
    types: List<String>,
): Result<Unit> {
    try {
        val path = "$outputDir/$baseName.kt"
        val file = File(path)
        file.printWriter().use { writer ->
            writer.println("package com.craftinginterpreters.lox")
            writer.println()
            writer.println("sealed class " + baseName)
            for (type in types) {
                writer.println() // Blank space between each type (starting with sealed class)
                val className = type.split(":")[0].trim()
                val fields = type.split(":")[1].trim()
                defineType(writer, baseName, className, fields)
            }

            return Result.success(Unit)
        }
    } catch (exception: IOException) {
        return Result.failure(exception)
    }
}

private fun defineType(
    writer: PrintWriter,
    baseName: String,
    className: String,
    fieldList: String,
) {
    writer.println("data class " + className + "(")
    val fields = fieldList.split(",")
    for (i in fields.indices) {
        val field = fields[i].trim()
        val javaType = field.split(" ")[0].trim()
        val type = javaTypeToKotlin(javaType)
        val name = field.split(" ")[1].trim()
        writer.print("    val " + name + ": " + type)
        writer.println(",")
    }
    writer.println(") : " + baseName + "()")
}

// Main purpose of this is to avoid having to rewrite AST definitions from
// the crafting interpreters book
private fun javaTypeToKotlin(type: String): String {
    val javaToKotlinMap =
        mapOf(
            "Object" to "Any",
            "Token" to "LoxToken",
        )
    return javaToKotlinMap.getOrDefault(type, type)
}
