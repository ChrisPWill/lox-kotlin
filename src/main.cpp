#include <cstdlib>
#include <iostream>
#include <span>

#include <sysexits.h>

int main(int argc, char *argv[]) {
  if (argc > 2) {
    std::cout << "Usage: lox_interpreter [script]\n";
    exit(EX_USAGE);
  } else if (argc == 2) {
    auto args = std::span(argv, size_t(argc));
    std::cout << std::format("RUNNING: {}\n", args[1]);
  } else {
    std::cout << "REPL MODE\n";
  }
}
