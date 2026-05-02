package com.craftinginterpreters.lox

data class LoxToken(
    val type: TokenType,
    val lexeme: String,
    val line: Int,
)
