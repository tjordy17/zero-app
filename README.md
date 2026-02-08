# zero-app / EcoSim

A small Java/Swing prototype for a predator–prey simulation (EcoSim). This
repository contains a lightweight `BaseFrame` helper and a demo application
`PredPreySim` used for UI and simulation experiments.

## Prerequisites
- Java Development Kit (JDK) 11 or newer installed and on your `PATH`.
- (Optional) VS Code or IntelliJ IDEA for editing and debugging.

## Quick build & run
From the project root (PowerShell):

# zero-app / EcoSim

A small Java/Swing predator–prey simulation prototype.

## Project overview 🌿
EcoSim models interactions between prey (sheep) and predators (wolves) on a
grid with regenerating resources (grass). Agents have energy while acting each
step: prey gain energy from grass and reproduce when they have sufficient
energy; predators gain energy by hunting prey. The simulation is intended as a
teaching and exploration tool: you can tweak parameters (for example, starting
energy, reproduction thresholds, or population sizes) and observe how the
ecosystem responds.

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
- `PredPreySim` has a standard `public static void main(String[] args)` entrypoint.

## Running in VS Code
- A sample debug configuration is included at `.vscode/launch.json` so you can run
  and debug `PredPreySim` with F5 (requires the Java extensions in VS Code).
- If you prefer the shell, compile and run using the commands above; the launch
  configuration is optional and simply makes debugging more convenient.

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

