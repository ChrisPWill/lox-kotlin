package com.craftinginterpreters.lox

import java.util.Scanner as JavaScanner

data class Token(
    val content: String,
)

class Scanner(
    private val source: String,
) {
    fun scanTokens(): List<Token> {
        val scanner = JavaScanner(source)
        val tokens = mutableListOf<Token>()
        while (scanner.hasNext()) {
            tokens.add(Token(scanner.next()))
        }
        return tokens
    }
}
