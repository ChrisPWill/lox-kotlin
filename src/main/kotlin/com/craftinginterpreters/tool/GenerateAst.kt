package com.craftinginterpreters.tool

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 2) {
        System.err.println("Usage: generateAst <output-directory>")
        exitProcess(64)
    }

    // val outputDir = args[0];
}
