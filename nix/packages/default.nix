{pkgs, ...}:
pkgs.stdenv.mkDerivation {
  pname = "lox_interpreter";
  version = "1.0";

  src = pkgs.lib.cleanSourceWith {
    src = ../../.;
    filter = name: type: let
      baseName = baseNameOf (toString name);
    in
      !(type == "directory" && (baseName == "build" || baseName == ".gradle" || baseName == ".direnv"));
  };

  nativeBuildInputs = with pkgs; [
    gradle
    jdk21
  ];

  buildPhase = ''
    export GRADLE_USER_HOME=$(mktemp -d)
    gradle installDist --no-daemon
  '';

  installPhase = ''
    mkdir -p $out
    cp -r build/install/lox_interpreter/* $out/
  '';
}
