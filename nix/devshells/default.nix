{pkgs, ...}:
pkgs.mkShell {
  # Add build dependencies
  nativeBuildInputs = with pkgs; [
    # Build Systems
    just
    ncurses # for tput

    # JVM & Tools
    jdk21
    kotlin
    gradle
    ktlint

    # Quality & Analysis
    treefmt
    alejandra
  ];

  buildInputs = with pkgs; [
    # Add library dependencies here
  ];

  # Load custom bash code
  shellHook = ''
    export PS1="(kotlin) $PS1"
    cyan=$(tput setaf 6)
    reset=$(tput sgr0)
    echo -e "''${cyan}==> Kotlin Dev Environment - Available Commands:''${reset}"
    just --list
  '';
}
