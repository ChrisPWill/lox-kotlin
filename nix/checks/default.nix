{pkgs, ...}:
pkgs.stdenv.mkDerivation {
  pname = "lox_interpreter-check";
  version = "1.0";

  src = pkgs.lib.cleanSourceWith {
    src = ../../.;
    filter = name: type: let
      baseName = baseNameOf (toString name);
    in
      !(type == "directory" && (baseName == "build" || baseName == ".gradle" || baseName == ".direnv"));
  };

  nativeBuildInputs = with pkgs; [
    just
    gradle
    jdk21
    ktlint
    treefmt
    alejandra
    ncurses
  ];

  dontConfigure = true;

  buildPhase = ''
    export GRADLE_USER_HOME=$(mktemp -d)
    just check
    just test
  '';

  installPhase = ''
    touch $out
  '';

  doCheck = true;
}
