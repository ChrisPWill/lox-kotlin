package com.craftinginterpreters.lox

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ScannerTest :
    StringSpec({
        "test scan tokens" {
            val scanner = Scanner("var x = 123;")
            val tokens = scanner.scanTokens()
            tokens.size shouldBe 4
            tokens[0].content shouldBe "var"
            tokens[1].content shouldBe "x"
            tokens[2].content shouldBe "="
            tokens[3].content shouldBe "123;"
        }
    })
