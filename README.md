# zero-app / EcoSim

A small Java/Swing prototype for a predator–prey simulation (EcoSim). This
repository contains a lightweight `BaseFrame` helper and a demo application
`PredPreySim` used for UI and simulation experiments.

## Prerequisites
- Java Development Kit (JDK) 11 or newer installed and on your `PATH`.
- (Optional) VS Code or IntelliJ IDEA for editing and debugging.

## Quick build & run
From the project root (PowerShell):

Compile all sources into an `out/` directory:
```powershell
javac -d out *.java
```

Run the application:
```powershell
java -cp out PredPreySim
```

Notes:
- If you edit sources, re-run the `javac` step and restart the program.
- For IDE usage, create a run configuration that launches `PredPreySim`.

## Project layout
- `*.java` — Java source files (UI and simulation classes).
- `out/` — compiler output (ignored by Git via `.gitignore`).

## Git and branches
- Feature work should be done on branches (for example, `Graphics`). Push
  your branch and open a Pull Request on GitHub for review before merging.
- A preview branch (e.g. `merge/Graphics-preview`) can be used to test merges
  without updating `main` immediately.

## Troubleshooting
- If you see merge conflicts (e.g., `README.md`), resolve the conflicted
  regions, then mark resolution:
```powershell
git add README.md
git commit
```
- If compiled artifacts are accidentally tracked, remove them from the repo
  (they remain on disk) then commit:
```powershell
git rm --cached -r out
git rm --cached *.class
git add .gitignore
git commit -m "Remove compiled artifacts"
```

## Development tips
- Use VS Code's Java extensions or IntelliJ for quick run/debug and
  Hot Code Replace (limited to method-body changes).
- To inspect compiled bytecode, use `javap`:
```powershell
javap -c -p -classpath out PredPreySim
```

---
If you want screenshots, CI build steps, or Maven/Gradle integration added to
this README, tell me what to include and I'll update it.
# EcoSim: The Predator-Prey Balance

🌿 Project Overview
EcoSim is a focused biological simulation designed to demonstrate the "coupled" relationship between two species. By simulating individual agents, the tool reveals how predator and prey populations naturally oscillate and how external "stress tests" can trigger a total ecosystem collapse.
# zero-app / EcoSim

A small Java/Swing predator–prey simulation prototype.

## What this repository contains
- `BaseFrame.java` — lightweight Swing helper frame used by the demo.
- `PredPreySim.java` — main simulation application (Swing GUI + simulation).
- `Sheep.java`, `Wolf.java`, `Grass.java`, `Creature.java` — simulation agent classes.
- `SliderExample.java` — a standalone example showing a `JSlider` usage.

## Prerequisites
- Java Development Kit (JDK) 11 or newer installed and on your `PATH`.
- (Optional) An IDE such as VS Code or IntelliJ IDEA for editing/debugging.

## Quick build & run
From the project root (PowerShell or any shell):

Compile all sources into an `out/` directory:
```powershell
javac -d out *.java
```

Run the application (this launches the Swing GUI):
```powershell
java -cp out PredPreySim
```

Notes:
- If you edit sources, re-run the `javac` step and restart the program.
- `PredPreySim` now has a standard `public static void main(String[] args)` entrypoint.

## UI notes
- The simulation window contains a settings area on the left. A `JSlider` was added
  to the settings region to control the starting energy for newly created sheep
  (backing field: `Sheep.START_ENERGY`). Use the slider to tweak initial energy
  before starting or reloading the simulation.
- `SliderExample.java` is a separate demo that demonstrates slider usage and
  can be run independently; it is not required to use the in-sim slider.

## Project layout
- Root Java files: the Swing UI and simulation code lives at the repository root.
- `out/`: compiler output directory (ignored from version control).

## Troubleshooting
- Common compile command shown above; if `java` fails, confirm `javac` succeeded
  and that `out/` contains `.class` files.

## Contributing
- Work on a feature branch, push, and open a Pull Request for review.

If you'd like, I can add screenshots, CI steps, or an IDE launch configuration.