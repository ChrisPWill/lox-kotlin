# Kotlin Lox Interpreter

A Kotlin implementation of the Lox interpreter from "Crafting Interpreters", managed with Nix and Gradle.

Dual-purpose goal: learning how interpreters work, while also learning how to write Kotlin.

## Getting Started

1.  **Initialize the project:**
    If you're using `direnv`, just `cd` into the directory and run `direnv allow`.
    Otherwise, run `nix develop`.

2.  **Build the project:**

    ```bash
    just build
    ```

3.  **Run the project:**

    ```bash
    just run
    ```

4.  **Run tests:**

    ```bash
    just test
    ```

    Tests are powered by [Kotest](https://kotest.io/).

## Project Structure

- `src/`: Source files.
- `nix/`: Nix configuration files (packages, devshells, checks).
- `flake.nix`: Entry point for Nix.
- `justfile`: Command runner (shortcuts for common tasks).
- `treefmt.toml`: Formatter configuration.
- `build.gradle.kts`: Gradle build configuration.
