#include <string_view>

class Lox {
public:
  Lox() = default;

  void runFile(std::string &path);

  void runPrompt();

private:
  void run(std::string_view source);

  void report(int line, std::string &where, std::string &message);

  void error(int line, std::string &message);
};
