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

🧬 The Core Logic: The Energy Loop
The simulation operates on a closed-loop energy system. Every "step" (iteration) calculates the survival of two agent types:

The Prey: Gains energy by consuming a regenerating environmental resource. Their goal is to avoid predators while maintaining enough energy to reproduce.

The Predator: Gains energy exclusively by hunting prey. If the prey population drops too low, the predators face immediate starvation.

Agent Decision Tree
Can I Reproduce? (If Energy > Threshold + Mate is nearby) → Action: Spend energy to spawn new agent.

Am I Hungry? (If Food is in Range) → Action: Move to food and consume.

Am I Lost? (No food/mate detected) → Action: Random movement (Scout) at an energy cost.

Am I Out of Energy? (If Energy ≤ 0) → Action: Death/Removal from grid.

🛠 Environmental Stress Tests
The "Learning Tool" aspect allows users to intentionally break the ecosystem to see the consequences:

1. The "Invasive Growth" Stress Test
Users can set the Prey Reproduction Rate to maximum.

The Lesson: This demonstrates how prey can "overshoot" the environment's carrying capacity, leading to a massive population spike followed by a crash as they exhaust the regenerating resource.

2. The "Apex Predator" Stress Test
Users can increase the Predator Efficiency or Initial Population.

The Lesson: If predators are too successful, they eliminate their only food source. This shows the "suicide of the species"—once the last prey agent is eaten, the predator population is guaranteed to hit zero shortly after.

3. The "Resource Scarcity" Stress Test
Users can throttle the Resource Regeneration Rate.

The Lesson: This shows "Bottom-Up" failure. Even with healthy predators and prey, if the base energy of the environment fails, the entire tower collapses.

📈 Visualizing the Balance
A key feature of the tool is the real-time population graph. In a stable environment, these two lines should never stay flat; they should follow a "lagging" wave pattern.

The Peak: When Prey population peaks, the Predator population begins to rise.

The Crash: When Predator population peaks, the Prey population plummets, eventually causing the Predator line to drop in response.


# build

This project backend is built using Java (Spring Boot). Frontend work uses Next.js. See `backend/README.md` for build and run instructions for the Java service.