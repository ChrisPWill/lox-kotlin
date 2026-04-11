#ifndef SCANNER_HPP
#define SCANNER_HPP

#include <list>
#include <string>
#include <string_view>

struct Token {
  std::string content;
};

class Scanner {
  std::string_view source;

public:
  explicit Scanner(std::string_view src) : source(src) {}
  std::list<Token> scanTokens();
};

#endif // SCANNER_HPP
