{
  inputs,
  pkgs,
  ...
}:
(inputs.treefmt-nix.lib.evalModule pkgs {
  projectRootFile = "flake.nix";

  programs.alejandra.enable = true; # nix
  programs.clang-format.enable = true; # cpp, h
}).config.build.wrapper
