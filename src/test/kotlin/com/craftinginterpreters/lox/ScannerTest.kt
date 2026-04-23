package com.craftinginterpreters.lox

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ScannerTest {
    @Test
    fun testScanTokens() {
        val scanner = Scanner("var x = 123;")
        val tokens = scanner.scanTokens()
        assertEquals(4, tokens.size)
        assertEquals("var", tokens[0].content)
        assertEquals("x", tokens[1].content)
        assertEquals("=", tokens[2].content)
        assertEquals("123;", tokens[3].content)
    }
}
