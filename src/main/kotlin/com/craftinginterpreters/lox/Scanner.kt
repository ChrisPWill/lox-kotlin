package com.craftinginterpreters.lox

import java.util.Scanner as JavaScanner

class Scanner(
    private val source: String,
) {
    companion object {
        val KEYWORDS =
            mapOf(
                "and" to And,
                "class" to Class,
                "else" to Else,
                "false" to False,
                "for" to For,
                "fun" to Fun,
                "if" to If,
                "nil" to Nil,
                "or" to Or,
                "print" to Print,
                "return" to Return,
                "super" to Super,
                "this" to This,
                "true" to True,
                "var" to Var,
                "while" to While,
            )
    }

    private val tokens: MutableList<LoxToken> = mutableListOf()
    private var start = 0
    private var current = 0
    private var line = 1

    fun scanTokens(): List<LoxToken> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }

        tokens.add(LoxToken(Eof, "", null, line))
        return tokens
    }

    private fun isAtEnd(): Boolean = current >= source.length

    private fun scanToken() {
        val char = advance()
        when (char) {
            '(' -> {
                addToken(LeftParen)
            }

            ')' -> {
                addToken(RightParen)
            }

            '{' -> {
                addToken(LeftBrace)
            }

            '}' -> {
                addToken(RightBrace)
            }

            ',' -> {
                addToken(Comma)
            }

            '.' -> {
                addToken(Dot)
            }

            '-' -> {
                addToken(Minus)
            }

            '+' -> {
                addToken(Plus)
            }

            ';' -> {
                addToken(Semicolon)
            }

            '*' -> {
                addToken(Star)
            }

            '!' -> {
                addToken(if (match('=')) BangEqual else Bang)
            }

            '=' -> {
                addToken(if (match('=')) EqualEqual else Equal)
            }

            '<' -> {
                addToken(if (match('=')) LessEqual else Less)
            }

            '>' -> {
                addToken(if (match('=')) GreaterEqual else Greater)
            }

            '/' -> {
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance()
                } else if (match('*')) {
                    while (!(peek() == '*' && peekNext() == '/') && !isAtEnd()) {
                        advance()
                    }
                    // Clear the "*/"
                    advance()
                    advance()
                } else {
                    addToken(Slash)
                }
            }

            // Skip the meaningless characters
            ' ', '\r', '\t' -> { }

            '\n' -> {
                line++
            }

            '"' -> {
                string()
            }

            else -> {
                // Digits/identifiers are too annoying to match
                if (isDigit(char)) {
                    number()
                } else if (isAlpha(char)) {
                    identifier()
                } else {
                    Lox.error(line, "Unexpected character")
                }
            }
        }
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false

        current++
        return true
    }

    private fun peek(): Char {
        if (isAtEnd()) return '\u0000'
        return source[current]
    }

    private fun peekNext(): Char {
        if (current + 1 >= source.length) return '\u0000'
        return source[current + 1]
    }

    private fun advance(): Char = source[current++]

    private fun addToken(type: TokenType) = addToken(type, null)

    private fun addToken(
        type: TokenType,
        literal: Any?,
    ) {
        val text = source.substring(start, current)
        tokens.add(LoxToken(type, text, literal, line))
    }

    private fun string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++
            advance()
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string")
            return
        }

        // The closing ".
        advance()

        val value = source.substring(start + 1, current - 1)
        addToken(LoxString(value), value)
    }

    private fun isAlpha(c: Char) = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_'

    private fun isDigit(char: Char) = """\d""".toRegex().matches(char.toString())

    private fun isAlphaNumeric(char: Char) = isAlpha(char) || isDigit(char)

    private fun number() {
        while (isDigit(peek())) advance()

        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance()

            while (isDigit(peek())) advance()
        }
        val value = source.substring(start, current).toDouble()
        addToken(Number(value), value)
    }

    private fun identifier() {
        while (isAlphaNumeric(peek())) advance()
        val text = source.substring(start, current)
        val type = Scanner.KEYWORDS.getOrElse(text) { Identifier(text) }
        addToken(type)
    }
}
