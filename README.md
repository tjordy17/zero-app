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