set shell := ["bash", "-uc"]

# Colors for output
cyan := `tput setaf 6`
green := `tput setaf 2`
yellow := `tput setaf 3`
reset := `tput sgr0`

# List all available commands
default:
    @just --list

# Build the project
build:
    @printf "{{cyan}}==> Building project with Gradle...{{reset}}\n"
    gradle build

# Build and run the project
run scriptname="":
    @printf "{{green}}==> Running executable...{{reset}}\n"
    if [ -n "{{scriptname}}" ]; then \
        gradle run --console=plain -q --args="{{scriptname}}"; \
    else \
        gradle run --console=plain -q; \
    fi

# Generate AST types
generate:
    @printf "{{green}}==> Generating AST types...{{reset}}\n"
    gradle generateAst

# Run tests with Kotest
test:
    @printf "{{cyan}}==> Running Kotest tests...{{reset}}\n"
    gradle test

# Run tests with Kotest (watch mode)
wtest:
    @printf "{{cyan}}==> Running Kotest tests (watch)...{{reset}}\n"
    gradle test --continuous

# Clean build artifacts
clean:
    @printf "{{yellow}}==> Cleaning build and cache artifacts...{{reset}}\n"
    gradle clean
    rm -rf build/ .gradle/

# Run static analysis
check:
    @printf "{{cyan}}==> Running Gradle checks...{{reset}}\n"
    gradle check
    @printf "{{green}}==> All checks passed!{{reset}}\n"

# Format all source files
format:
    @printf "{{cyan}}==> Formatting project with treefmt...{{reset}}\n"
    treefmt
