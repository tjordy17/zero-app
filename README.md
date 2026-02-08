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

## Parameters
The main parameters you may want to tweak are defined in the source or exposed
via the UI slider:

- `Sheep.START_ENERGY` — initial energy for new sheep (adjustable with the in‑sim slider).
- `liveSheep`, `liveWolf` — initial population counts (set in `PredPreySim`).
- `sheepReproductionMin` — energy threshold required for a sheep to reproduce.
- `gridWidth`, `gridHeight` — simulation grid dimensions.
- `stepTimerLimit` / `stepMax` — controls simulation step speed and total steps.

To change parameters permanently, edit the fields in `PredPreySim.java` or
`Sheep.java` and recompile. Runtime tweaks (like `START_ENERGY`) can be done
with the UI slider.

## Example experiments
Try these small experiments to observe system behaviour:

- Overpopulation / Crash:
  1. Increase `Sheep.START_ENERGY` (use the slider) and `liveSheep`.
  2. Run the sim and observe whether sheep reproduce faster than grass regenerates.
  3. Expected: a population spike followed by a crash when resources are exhausted.

- Predator collapse:
  1. Increase `liveWolf` while keeping `liveSheep` low.
  2. Run until predators eliminate available prey.
  3. Expected: wolves die off after prey are depleted.

- Resource scarcity:
  1. Reduce grass initial growth in `loadCreatures()` (lower the `randint` range or
     adjust regeneration logic in `Grass.java`).
  2. Run and observe bottom-up failure across both species.

Collect screenshots or logs to compare runs; small parameter changes produce large
differences in long-term dynamics, which is the core teaching point of EcoSim.

## Project layout
- Root Java files: the Swing UI and simulation code lives at the repository root.
- `out/`: compiler output directory (ignored from version control).

## Troubleshooting
- Common compile command shown above; if `java` fails, confirm `javac` succeeded
  and that `out/` contains `.class` files.

## Contributing
- Work on a feature branch, push, and open a Pull Request for review.

