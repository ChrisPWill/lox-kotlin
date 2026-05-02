package com.craftinginterpreters.lox

import java.io.File
import kotlin.system.exitProcess
import java.util.Scanner as InputScanner

object Lox {
    var hadError = false

    fun runFile(path: String) {
        val file = File(path)
        if (!file.exists()) {
            System.err.println("Error opening file: $path")
            exitProcess(1)
        }
        val content = file.readText()
        println("File contents:\n---\n$content\n---")
        run(content)

        if (hadError) {
            exitProcess(65) // EX_DATAERR
        }
    }

    fun runPrompt() {
        val scanner = InputScanner(System.`in`)
        while (true) {
            print("> ")
            if (!scanner.hasNextLine()) break
            val line = scanner.nextLine()
            if (line.isEmpty()) break
            run(line)
            hadError = false
        }
    }

    private fun run(source: String) {
        val scanner = Scanner(source)
        val tokens = scanner.scanTokens()
        for (token in tokens) {
            println(token.lexeme)
        }
    }

    fun error(
        line: Int,
        message: String,
    ) {
        report(line, "", message)
    }

    private fun report(
        line: Int,
        where: String,
        message: String,
    ) {
        System.err.println("[line $line] Error$where: $message")
        hadError = true
    }
}

fun main(args: Array<String>) {
    try {
        if (args.size > 1) {
            println("Usage: lox_interpreter [script]")
            exitProcess(64) // EX_USAGE
        } else if (args.size == 1) {
            Lox.runFile(args[0])
        } else {
            Lox.runPrompt()
        }
    } catch (e: Exception) {
        System.err.println("Fatal error: ${e.message}")
        exitProcess(1)
    }
}
