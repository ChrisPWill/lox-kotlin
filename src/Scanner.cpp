module;
#include <list>
#include <sstream>
#include <string>
#include <string_view>
export module Scanner;

export struct Token {
  std::string content;
};

export class Scanner {
  std::string_view source;

public:
  explicit Scanner(std::string_view source) : source(source) {}

  std::list<Token> scanTokens() {
    std::istringstream stringStream((std::string(this->source)));
    std::string word;
    std::list<Token> tokens;
    while (stringStream >> word) {
      tokens.push_back({word});
    }
    return tokens;
  }
};
