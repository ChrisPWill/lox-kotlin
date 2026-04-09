#include <cstdlib>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <span>
#include <sstream>
#include <string>

#include <sysexits.h>

namespace fs = std::filesystem;

void run(const std::string &source) { std::cout << source << "\n"; }

std::ifstream openFileOrExit(std::string path) {
  fs::path filePath(path);
  std::ifstream file(filePath);

  if (!file.is_open()) {
    std::cerr << std::format("Error opening file: {}\n", path);
    exit(1);
  }

  return file;
}

void runFile(std::ifstream &file) {
  std::stringstream buffer;
  buffer << file.rdbuf();
  std::string content = buffer.str();
  std::cout << "File contents:\n---\n" << content << "\n---\n";
  run(content);
}

void runPrompt() {
  while (true) {
    std::string readLine;
    std::getline(std::cin, readLine);
    if (readLine == "") {
      break;
    }
    run(readLine);
  }
}

int main(int argc, char *argv[]) {
  if (argc > 2) {
    std::cout << "Usage: lox_interpreter [script]\n";
    exit(EX_USAGE);
  } else if (argc == 2) {
    auto args = std::span(argv, size_t(argc));
    auto file = openFileOrExit(args[1]);
    std::cout << std::format("RUNNING: {}\n", args[1]);
    runFile(file);
    file.close();
  } else {
    std::cout << "REPL MODE\n";
    runPrompt();
  }
}
