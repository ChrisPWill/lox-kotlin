package com.craftinginterpreters.lox

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ScannerTest :
    StringSpec({
        "scan tokens" {
            val scanner = Scanner("var x = 123;")
            val tokens = scanner.scanTokens()
            tokens.size shouldBe 6
            tokens[0].lexeme shouldBe "var"
            tokens[1].lexeme shouldBe "x"
            tokens[2].lexeme shouldBe "="
            tokens[3].lexeme shouldBe "123"
            tokens[4].lexeme shouldBe ";"
            tokens[5].type shouldBe Eof
            tokens[5].lexeme shouldBe ""
        }

        "handles newlines and comments" {
            val scanner =
                Scanner(
                    """
// Test
var x    =  123;
""",
                )
            val tokens = scanner.scanTokens()
            tokens.size shouldBe 6
            tokens[0].lexeme shouldBe "var"
            tokens[1].lexeme shouldBe "x"
        }

        "handles multiline comments" {
            val scanner =
                Scanner(
                    """
/*
var y = 456;
*/
var x    =  123;
""",
                )
            val tokens = scanner.scanTokens()
            tokens.size shouldBe 6
            tokens[0].lexeme shouldBe "var"
            tokens[1].lexeme shouldBe "x"
        }

        "handles nested multiline comments" {
            val scanner =
                Scanner(
                    """
/*
/* ignore this too */
var y = 456;
*/
var x    =  123;
""",
                )
            val tokens = scanner.scanTokens()
            tokens.size shouldBe 6
            tokens[0].lexeme shouldBe "var"
            tokens[1].lexeme shouldBe "x"
        }

        "reports error on unterminated multiline comment" {
            Lox.hadError = false
            val scanner = Scanner("/* unterminated")
            scanner.scanTokens()
            Lox.hadError shouldBe true
        }
    })
