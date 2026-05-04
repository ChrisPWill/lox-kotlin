package com.craftinginterpreters.lox

sealed class Expr

data class Binary(
    val left: Expr,
    val operator: LoxToken,
    val right: Expr,
) : Expr()
