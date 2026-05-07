package com.craftinginterpreters.lox

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class AstPrinterTest :
    StringSpec({
        "should return example expression from book" {
            val expression =
                Binary(
                    Unary(
                        LoxToken(Minus, "-", 1),
                        Literal(123),
                    ),
                    LoxToken(Star, "*", 1),
                    Grouping(Literal(45.67)),
                )
            AstPrinter().print(expression) shouldBe "(* (- 123) (group 45.67))"
        }
    })
