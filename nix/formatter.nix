{
  inputs,
  pkgs,
  ...
}:
(inputs.treefmt-nix.lib.evalModule pkgs {
  projectRootFile = "flake.nix";

  programs.alejandra.enable = true; # nix
  programs.ktlint.enable = true; # kotlin
}).config.build.wrapper
