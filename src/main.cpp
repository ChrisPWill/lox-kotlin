#include <Lox.hpp>

#include <cstdlib>
#include <exception>
#include <format>
#include <iostream>
#include <span>
#include <string>

#include <sysexits.h>

int main(int argc, char *argv[]) {
  try {
    Lox lox;
    if (argc > 2) {
      std::cout << "Usage: lox_interpreter [script]\n";
      exit(EX_USAGE);
    } else if (argc == 2) {
      auto args = std::span(argv, size_t(argc));
      auto fileName = std::string(args[1]);
      lox.runFile(fileName);
    } else {
      lox.runPrompt();
    }
  } catch (const std::exception &e) {
    std::cerr << std::format("Fatal error: {}\n", e.what());
    return EXIT_FAILURE;
  }
  return EXIT_SUCCESS;
}
