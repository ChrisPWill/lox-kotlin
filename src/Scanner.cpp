#include "Scanner.hpp"
#include <sstream>
#include <string>

std::list<Token> Scanner::scanTokens() {
  std::istringstream stringStream((std::string(this->source)));
  std::string word;
  std::list<Token> tokens;
  while (stringStream >> word) {
    tokens.push_back({word});
  }
  return tokens;
}
