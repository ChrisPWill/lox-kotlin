package com.craftinginterpreters.lox

import io.kotest.core.config.AbstractProjectConfig

class ProjectConfig : AbstractProjectConfig() {
  override val parallelism = 3
}
