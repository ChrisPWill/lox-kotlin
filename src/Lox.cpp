#include "Scanner.hpp"

#include <cstdlib>
#include <filesystem>
#include <fstream>
#include <iostream>
#include <sysexits.h>

namespace fs = std::filesystem;

class Lox {
public:
  bool hadError = false;

  Lox() = default;

  void runFile(const std::string &path) const {
    auto file = Lox::openFileOrExit(path);
    std::stringstream buffer;
    buffer << file.rdbuf();
    std::string content = buffer.str();
    std::cout << "File contents:\n---\n" << content << "\n---\n";
    run(content);

    if (hadError) {
      exit(EX_DATAERR);
    }
  }

  void runPrompt() {
    while (true) {
      std::string readLine;
      std::getline(std::cin, readLine);
      if (readLine.empty()) {
        break;
      }
      run(readLine);
    }
  }

private:
  static void run(std::string_view source) {
    Scanner scanner(source);
    auto tokens = scanner.scanTokens();
    for (const auto &token : tokens) {
      std::cout << token.content << "\n";
    }
  }

  void report(int line, std::string &where, std::string &message) {
    std::cout << std::format("[line {}] Error{}: {}\n", line, where, message);
    hadError = true;
  }

  void error(int line, std::string &message) {
    auto where = std::string("");
    report(line, where, message);
  }

  static std::ifstream openFileOrExit(const std::string &path) {
    fs::path filePath(path);
    std::ifstream file(filePath);

    if (!file.is_open()) {
      std::cerr << std::format("Error opening file: {}\n", path);
      exit(1);
    }

    return file;
  }
};
