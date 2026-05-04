package com.craftinginterpreters.lox

sealed class Expr

data class Binary(
    val left: Expr,
    val operator: LoxToken,
    val right: Expr,
) : Expr()

data class Grouping(
    val expression: Expr,
) : Expr()

data class Literal(
    val value: Any,
) : Expr()

data class Unary(
    val operator: LoxToken,
    val right: Expr,
) : Expr()
