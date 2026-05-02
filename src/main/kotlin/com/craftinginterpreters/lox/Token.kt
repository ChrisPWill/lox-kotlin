import com.craftinginterpreters.lox.TokenType

data class LoxToken(
    val type: TokenType,
    val lexeme: String,
    val literal: Any?,
    val line: Int,
)
