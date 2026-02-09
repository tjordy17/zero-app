# zero-app / EcoSim

A small Java/Swing prototype for a predator–prey simulation (EcoSim). This
repository contains a lightweight `BaseFrame` helper and a demo application
`PredPreySim` used for UI and simulation experiments.

## Prerequisites
- Java Development Kit (JDK) 11 or newer installed and on your `PATH`.
- (Optional) VS Code or IntelliJ IDEA for editing and debugging.


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
- Java Development Kit (JDK) 17 installed and on your `PATH`.
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

## How to run (three quick commands)
From the project root use these three commands as a quick workflow:

1. Compile all sources into `out/`:
```powershell
javac -d out *.java
```
2. Run the application:
```powershell
java -cp out PredPreySim
```
3. Capture console output to a log file (PowerShell example):
```powershell
java -cp out PredPreySim *> run.log
```

The log command is optional — use it when you want to keep a record of the run.

## UI notes
- The simulation window contains a settings area on the left. A `JSlider` was added
  to the settings region to control the starting energy for newly created sheep
  (backing field: `Sheep.START_ENERGY`). Use the slider to tweak initial energy
  before starting or reloading the simulation.
- `SliderExample.java` is a separate demo that demonstrates slider usage and
  can be run independently; it is not required to use the in-sim slider.

## Short description & controls
- Short description: EcoSim is a small, interactive predator–prey grid simulation
  where sheep eat regenerating grass and wolves hunt sheep. It demonstrates
  population dynamics and resource-driven collapse.

- Controls (left settings panel):
  - Spinners: `Initial Sheep`, `Initial Wolves` — set starting populations.
  - Sliders: birth thresholds and death/energy-loss rates for sheep and wolves, plus `Grass growth rate`.
  - Buttons: `reset` (apply settings + restart world), `play`, and `pause`.

Use the `reset` button after adjusting spinners/sliders to apply changes and restart the simulation.

## Parameters
The main parameters you may want to tweak are defined in the source or exposed
via the UI slider:

Current in-sim controls (left settings panel):

- `Initial Sheep` (spinner) — `liveSheep`, initial prey population.
- `Birth threshold` (sheep slider) — `sheepReproductionMin`, lower => more births.
- `Death rate` (sheep slider) — `sheepEnergyLossPerStep`, energy lost per sheep move.
- `Initial Wolves` (spinner) — `liveWolf`, initial predator population.
- `Birth threshold` (wolf slider) — `wolfReproductionMin`, lower => more wolf births.
- `Death rate` (wolf slider) — `energyLossPerStep`, energy lost per wolf move.
- `Grass growth rate` (slider) — `grassGrowthRate`, controls how quickly grass regenerates.

- Controls application: use the **reset** button ("Apply current settings and restart world") to apply spinner/slider values and restart the simulation with the new settings.
- Play/Pause buttons control simulation execution without changing parameters.

Notes on `START_ENERGY` and permanent edits:
- `Sheep.START_ENERGY` is currently a code-level field (set in `Sheep.java`) and is not exposed in the settings panel. To change it permanently, edit `Sheep.START_ENERGY` and recompile.
- Other fields such as `gridWidth`, `gridHeight`, `stepTimerLimit`, and `stepMax` are defined in `PredPreySim.java`; change them there for permanent effects and recompile.

To change parameters permanently, edit the fields in [PredPreySim.java](PredPreySim.java) or [Sheep.java](Sheep.java) and recompile. Runtime tweaks available through the settings panel take effect when you press the **reset** button.

## Example experiments
"""
# zero-app / EcoSim

A small Java/Swing predator–prey simulation prototype used for demos and
hackathon submissions. This README includes quick steps to build a runnable
JAR and package the project for sharing with judges.

## Hackathon — Quick share (recommended)
- Produce a runnable JAR and a short `README.md` + demo video/screenshots.
- Include these assets for judges: `PredPreySim.jar`, source files or a
  GitHub link, `README.md`, 30–60s demo video, and 2–3 screenshots.

## Prerequisites
- Java Development Kit (JDK) 17 or newer installed and on your `PATH`.
- (Optional) An IDE such as VS Code or IntelliJ for editing/debugging.

## Build & run (minimal)
From the project root (PowerShell or any shell):

1) Compile all sources into `out/`:
```powershell
javac -d out *.java
```

2) Create an executable (fat) JAR that uses `PredPreySim` as the main class:
```powershell
# create a jar with main class set; include compiled classes from out/
jar cfe PredPreySim.jar PredPreySim -C out .
```

3) Run the JAR:
```powershell
java -jar PredPreySim.jar
```

If you prefer running from classes without creating a JAR, use:
```powershell
java -cp out PredPreySim
```

## Create a ZIP for quick sharing (Windows PowerShell)
```powershell
Compress-Archive -Path * -DestinationPath ../zero-app.zip
```
This produces `zero-app.zip` one level above the project root; upload that or
attach it to a GitHub Release.

## GitHub Release (fast)
1. Push your repo to GitHub (include source and `README.md`).
2. Create a new Release and upload `PredPreySim.jar` (and optionally
   `zero-app.zip`) as release assets so judges can download a single file.

## What to include for judges
- `PredPreySim.jar` — runnable artifact (primary).
- Source files or a GitHub link — for review.
- `README.md` — this file, with run steps and Java version.
- Demo video (30–60s) and 2–3 screenshots — show the app running and controls.

## Troubleshooting & tips
- If `jar` complains about missing classes, ensure `javac -d out *.java` created
  `.class` files under `out/` and rerun the `jar` command.
- For GUI apps prefer a runnable JAR over Docker — judges can run it with
  `java -jar` if they have Java installed.
- Use environment variables or command-line args for any external endpoints.

## Development notes
- `PredPreySim.java` contains the `main` entrypoint; tweak parameters there for
  permanent changes or use the UI sliders/spinners for runtime tweaks.
- Key source files: `BaseFrame.java`, `PredPreySim.java`, `Sheep.java`, `Wolf.java`, `Grass.java`, `Creature.java`.

## Example quick commands recap
```powershell
javac -d out *.java
jar cfe PredPreySim.jar PredPreySim -C out .
java -jar PredPreySim.jar
Compress-Archive -Path * -DestinationPath ../zero-app.zip
```

---
