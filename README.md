# Detekt and Android Lint custom rules sample 

This repository shows how to add modules with custom rules in your project directly. 

## How to use

### Detekt

See `detekt-rules` module for info about detekt. Use `baseline` branch for quick comparison of files.

Detekt module implements custom rule that tells to use a `when` instead of multiline `if` statement.

### Lint

See `lint-rules` module for more info about lint setup. Use `add_detekt` branch as a baseline 
for quick comparison of files

Lint module implements custom rule that tells to use `v` or `vg` prefix for ids of Views in layout files.
It also provides a quick fix suggesting to add one. 
