import javax.swing.*;
import java.awt.event.*;
//import java.io.*; 
//import javax.imageio.*; 
import java.awt.*;
import javax.swing.event.*;
import java.util.*;

public class PredPreySim extends BaseFrame {
    // ------Logic------
    private boolean load = false, simRunning = true, sheepFlag = true, wolfFlag = true;

    int gridWidth = 30, gridHeight = 20;
    private int[][] squares = new int[gridHeight][gridWidth];
    private Grass[][] grassArray = new Grass[gridHeight][gridWidth];
    private ArrayList<Wolf> wolflist = new ArrayList<Wolf>();
    private ArrayList<Sheep> sheeplist = new ArrayList<Sheep>();

    private int liveGrass = 0;
    private int liveWolf = 15;
    private int liveSheep = 100;

    private int step = 0, steptimer = 0;
    private int stepTimerLimit = 5, stepMax = 100;

    private int sheepReproductionMin = 65;

    private int grassGrowthRate = 20, initGrassPercent = 100;

    private int wolfReproductionMin = 85, energyLossPerStep = 20;
    private int sheepEnergyLossPerStep = 5;
    // ------UI------
    int screenWidth = 1530;
    int screenHeight = 850;
    // control buttons (made fields so we can update state)
    private CustomButton applyBtn;
    private CustomButton playBtn;
    private CustomButton pauseBtn;

    // fonts
    public static final Font bahnschrift32 = new Font("Bahnschrift", Font.PLAIN, 32);
    public static final Font bahnschrift64 = new Font("Bahnschrift", Font.PLAIN, 64);
    // lines
    public static final BasicStroke thickPen = new BasicStroke(10);
    public static final BasicStroke medPen = new BasicStroke(4);
    public static final BasicStroke thinPen = new BasicStroke(2);
    // colors
    public static final Color BURNTORANGE = new Color(204, 85, 0);
    public static final Color BURNTORANGELITE = new Color(201, 136, 89);
    public static final Color BEIGEORANGE = new Color(254, 245, 231);
    public static final Color DARKBEIGE = new Color(217, 203, 182);
    public static final Color DARKERBEIGE = new Color(176, 163, 144);

    // ----LOGIC & OTHER COOL STUFF---------
    @Override
    public void move() {
        if (!load) {// loads objects
            loadCreatures();
            load = true;
        }
        step();
    }

    // Update play/pause button visuals and enabled state
    private void updateControlStates() {
        if (playBtn != null && pauseBtn != null && applyBtn != null) {
            if (simRunning) {
                playBtn.setEnabled(false);
                playBtn.setActive(false);
                pauseBtn.setEnabled(true);
                pauseBtn.setActive(true);
            } else {
                playBtn.setEnabled(true);
                playBtn.setActive(true);
                pauseBtn.setEnabled(false);
                pauseBtn.setActive(false);
            }
            // apply button is always enabled, but we can reflect state if desired
            applyBtn.setEnabled(true);
        }
    }

    private void step() {
        if (simRunning) {
            steptimer++;
            if (steptimer >= stepTimerLimit) {
                steptimer = 0;
                step++;
                wolfAct();
                sheepAct();
                grassAct();
            }
            if (step >= stepMax) {
                simRunning = false;
            }
        }
    }


    private void wolfAct(){
        // reproduction and actions — iterate backwards to allow safe removals
        for (int i = wolflist.size() - 1; i >= 0; i--) {
            Wolf wolf = wolflist.get(i);
            Rectangle sight = new Rectangle(wolf.getX() - wolf.getRange(), wolf.getY() - wolf.getRange(),
                    (wolf.getRange() * 2) + 1, (wolf.getRange() * 2) + 1);
            if (wolf.getEnergy() >= wolfReproductionMin) {
                wolf.setEnergy(wolf.getEnergy() / 2);
                wolf.setTurn(false);
                addWolf(wolflist.size());
            }
            // eat sheep (iterate backwards so removals are safe)
            for (int j = sheeplist.size() - 1; j >= 0; j--) {
                Sheep sheep = sheeplist.get(j);
                if (sight.contains(sheep.getX(), sheep.getY())
                        && wolf.getTurn()
                        && randint(1, 10) >= 3) {
                    wolf.setX(sheep.getX());
                    wolf.setY(sheep.getY());
                    wolf.eat(sheep);
                    wolf.setTurn(false);
                    sheeplist.remove(j);
                }
            }

            // not eating sheep: move around
            for (int w = wolfFlag ? 0 : gridWidth - 1; (wolfFlag ? w : 0) < (wolfFlag ? gridWidth : w); w = wolfFlag
                    ? w + 1
                    : w - 1) {
                for (int k = wolfFlag ? 0 : gridHeight - 1; (wolfFlag ? k : 0) < (wolfFlag ? gridHeight
                        : k); k = wolfFlag ? k + 1 : k - 1) {
                    for (int l = 0; l < wolflist.size(); l++) {
                        Wolf wolf2 = wolflist.get(l);
                        if (sight.contains(grassArray[k][w].getX(), grassArray[k][w].getY())
                                && wolf.getTurn()
                                && randint(0, 10) >= 5
                                && grassArray[k][w].getX() != wolf2.getX()
                                && grassArray[k][w].getY() != wolf2.getY()) {
                            wolf.setX(grassArray[k][w].getX());
                            wolf.setY(grassArray[k][w].getY());
                            wolf.setTurn(false);
                            wolf.setEnergy(wolf.getEnergy() - energyLossPerStep);
                        }
                    }
                }
            }

            // kill wolves with no energy
            if (wolf.getEnergy() <= 0) {
                wolflist.remove(i);
                continue;
            }

            if (randint(0, 1) == 1) {
                wolfFlag = !wolfFlag;
            }
            wolf.setTurn(true);
        }
        liveWolf = wolflist.size();
    }
        

    

    private void sheepAct() {
        for (int i = sheeplist.size() - 1; i >= 0; i--) {
            Sheep sheep = sheeplist.get(i);
            // creates a rectangle and checks for different stuff
            Rectangle sight = new Rectangle(sheep.getX() - sheep.getRange(), sheep.getY() - sheep.getRange(),
                    (sheep.getRange() * 2) + 1, (sheep.getRange() * 2) + 1);

            // reproducing asexually. sheep can represent any number of sheep
            if (sheep.getEnergy() >= sheepReproductionMin && sheep.getTurn()) {
                sheep.setEnergy(sheep.getEnergy() / 2);
                sheep.setTurn(false);
                addSheep(sheeplist.size());
            }

            // eating grass
            for (int w = sheepFlag ? 0 : gridWidth - 1; (sheepFlag ? w : 0) < (sheepFlag ? gridWidth : w); w = sheepFlag
                    ? w + 1
                    : w - 1) {
                for (int k = sheepFlag ? 0 : gridHeight - 1; (sheepFlag ? k : 0) < (sheepFlag ? gridHeight
                        : k); k = sheepFlag ? k + 1 : k - 1) {
                    for (int l = 0; l < sheeplist.size(); l++) {
                        Sheep sheep2 = sheeplist.get(l);
                        // finds food
                        if (sight.contains(grassArray[k][w].getX(), grassArray[k][w].getY())
                                && sheep.getTurn()
                                && grassArray[k][w].getGrowth() >= 100
                                && randint(1, 10) >= 8
                                && grassArray[k][w].getX() != sheep2.getX()
                                && grassArray[k][w].getY() != sheep2.getY()) {
                            // System.out.print("found food to eat\n");
                            sheep.setX(grassArray[k][w].getX());
                            sheep.setY(grassArray[k][w].getY());
                            sheep.eatGrass();
                            grassArray[k][w].setGrowth(0);
                            sheep.setTurn(false);
                        }
                    }
                }
            }
            // not eating grass
            for (int w = sheepFlag ? 0 : gridWidth - 1; (sheepFlag ? w : 0) < (sheepFlag ? gridWidth : w); w = sheepFlag
                    ? w + 1
                    : w - 1) {
                for (int k = sheepFlag ? 0 : gridHeight - 1; (sheepFlag ? k : 0) < (sheepFlag ? gridHeight
                        : k); k = sheepFlag ? k + 1 : k - 1) {
                    for (int l = 0; l < sheeplist.size(); l++) {
                        Sheep sheep2 = sheeplist.get(l);
                        // finds food
                        if (sight.contains(grassArray[k][w].getX(), grassArray[k][w].getY())
                                && sheep.getTurn()
                                && randint(0, 10) >= 5
                                && grassArray[k][w].getX() != sheep2.getX()
                                && grassArray[k][w].getY() != sheep2.getY()) {
                            // System.out.print("no food, sad\n");
                            sheep.setX(grassArray[k][w].getX());
                            sheep.setY(grassArray[k][w].getY());
                            sheep.setTurn(false);
                            sheep.setEnergy(sheep.getEnergy() - sheepEnergyLossPerStep);
                            if (grassArray[k][w].getGrowth() >= 100) {
                                sheep.eatGrass();
                                grassArray[k][w].setGrowth(0);
                            }
                        }
                    }
                }
            }
            if (sheep.getEnergy() <= 0) {
                sheeplist.remove(i);
                continue;
            }

            if (randint(0, 1) == 1) {
                sheepFlag = !sheepFlag;
            }
            sheep.setTurn(true);
            liveSheep = sheeplist.size();
        }
        System.out.printf("Live sheep %d\n", liveSheep);
    }

    private void grassAct() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grassArray[j][i].setGrowth(grassArray[j][i].getGrowth() + grassGrowthRate);
            }
        }
    }

    private void loadCreatures() {
        // creates grass
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grassArray[j][i] = new Grass(i, j, randint(0, initGrassPercent));
                if (grassArray[j][i].getGrowth() >= 100) {
                    liveGrass++;
                }
            }
        }
        // creates sheep
        for (int i = 0; i < liveSheep; i++) {
            addSheep(i);
        }
        // creates wolves
        for (int i = 0; i < liveWolf; i++) {
            addWolf(i);
        }

    }

    private void addSheep(int i) {
        int x, y;
        x = randint(0, gridWidth - 1);
        y = randint(0, gridHeight - 1);
        for (int j = 0; j < i; j++) {
            while (sheeplist.get(j).getX() == x && sheeplist.get(j).getY() == y) {
                x = randint(0, gridWidth - 1);
                y = randint(0, gridHeight - 1);
            }
        }
        sheeplist.add(new Sheep(x, y));
    }

    private void addWolf(int i){
        int x, y;
            x = randint(0, gridWidth - 1);
            y = randint(0, gridHeight - 1);
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < sheeplist.size(); k++) {
                    while ((wolflist.get(j).getX() == x && wolflist.get(j).getY() == y)
                            || (sheeplist.get(k).getX() == x && sheeplist.get(k).getY() == y)) {
                        x = randint(0, gridWidth - 1);
                        y = randint(0, gridHeight - 1);
                    }
                }
            }
            wolflist.add(new Wolf(x, y));
    }

    // -----DRAWING STUFF!---------
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (load) {
            background(g2d);
            drawGrass(g2d);
            drawSheep(g2d);
            drawWolf(g2d);
        }
    }

    private void drawSheep(Graphics2D g2d) {
        for (int i = 0; i < sheeplist.size(); i++) {
            sheeplist.get(i).draw(g2d);
        }
    }

    private void drawWolf(Graphics2D g2d) {
        for (int i = 0; i < wolflist.size(); i++) {
            wolflist.get(i).draw(g2d);
        }
    }

    private void drawGrass(Graphics2D g2d) {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                grassArray[j][i].draw(g2d);
            }
        }
    }

    // draws background elements
    private void background(Graphics2D g2d) {
        g2d.setColor(DARKBEIGE);
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        g2d.setColor(DARKERBEIGE);
        g2d.fillRect(50, 50, 400, screenHeight - 100);// settings area

        g2d.fillRect(525, 50, 940, screenHeight - 350);// simulation area

        g2d.fillRect(525, 590, 940, 200);// stats area

        g2d.setColor(Color.BLACK);
        g2d.setFont(bahnschrift32);
        g2d.drawString(String.format("Step: %d", step), 730, 85);
    }

    // ------MAIN & HELPER FUNCTIONS-------------
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PredPreySim();
            }
        });
    }

    private int randint(int a, int b) {
        int range = b - a + 1;
        return (int) (Math.random() * range + a);
    }

    public PredPreySim() {
        super("PredPreySim", 1530, 850);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        // Create settings panel to hold controls (left side)
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        // popLimit used for labels and spinner max
        final int popLimit = Math.min(200, gridWidth * gridHeight);
        // style the panel for a cleaner look
        settingsPanel.setOpaque(true);
        settingsPanel.setBackground(new Color(245, 245, 240));
        settingsPanel.setBounds(50, 50, 400, screenHeight - 100);
        settingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulation Settings"));
        // add inner padding to the titled border for breathing room
        settingsPanel.setBorder(BorderFactory.createCompoundBorder(settingsPanel.getBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        // keep the panel from growing too wide inside the fixed bounds
        settingsPanel.setMaximumSize(new Dimension(400, screenHeight - 120));
        this.getLayeredPane().add(settingsPanel, JLayeredPane.PALETTE_LAYER);

        // Short description under the panel title (titled border already shows title)
        JLabel desc = new JLabel("Adjust initial populations and simulation parameters");
        desc.setFont(new Font("SansSerif", Font.ITALIC, 12));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        settingsPanel.add(Box.createVerticalStrut(6));
        settingsPanel.add(desc);
        settingsPanel.add(Box.createVerticalStrut(8));

        // (Starting energy removed from settings)

        // separator
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setMaximumSize(new Dimension(360, 4));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        settingsPanel.add(sep);
        settingsPanel.add(Box.createVerticalStrut(8));

        // === Prey (Sheep) Settings ===
        JPanel preyPanel = new JPanel();
        preyPanel.setLayout(new BoxLayout(preyPanel, BoxLayout.Y_AXIS));
        preyPanel.setOpaque(false);
        preyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        preyPanel.setMaximumSize(new Dimension(360, Short.MAX_VALUE));

        JLabel preyHeader = new JLabel("Prey (Sheep)");
        preyHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        preyHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        preyPanel.add(preyHeader);
        preyPanel.add(Box.createVerticalStrut(6));

        JPanel sheepRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sheepRow.setOpaque(false);
        sheepRow.setMaximumSize(new Dimension(360, 40));
        JLabel sheepLabel = new JLabel("Initial Sheep (max " + popLimit + "):");
        sheepLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        final JSpinner sheepSpinner = new JSpinner(new SpinnerNumberModel(liveSheep, 0, popLimit, 1));
        sheepSpinner.setPreferredSize(new Dimension(80, 24));
        sheepRow.add(sheepLabel);
        sheepRow.add(sheepSpinner);
        preyPanel.add(sheepRow);

        JPanel sheepBirthRow = new JPanel(new BorderLayout());
        sheepBirthRow.setOpaque(false);
        sheepBirthRow.setMaximumSize(new Dimension(360, 70));
        JLabel sheepBirthLabel = new JLabel("Birth threshold (lower -> more births):");
        sheepBirthLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sheepBirthRow.add(sheepBirthLabel, BorderLayout.NORTH);
        final JSlider sheepBirthSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, sheepReproductionMin);
        sheepBirthSlider.setMajorTickSpacing(20);
        sheepBirthSlider.setPaintTicks(true);
        sheepBirthSlider.setPaintLabels(true);
        sheepBirthSlider.setMaximumSize(new Dimension(340, 60));
        sheepBirthRow.add(sheepBirthSlider, BorderLayout.CENTER);
        preyPanel.add(sheepBirthRow);

        JPanel sheepDeathRow = new JPanel(new BorderLayout());
        sheepDeathRow.setOpaque(false);
        sheepDeathRow.setMaximumSize(new Dimension(360, 70));
        JLabel sheepDeathLabel = new JLabel("Death rate (energy loss per move):");
        sheepDeathLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sheepDeathRow.add(sheepDeathLabel, BorderLayout.NORTH);
        final JSlider sheepDeathSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, sheepEnergyLossPerStep);
        sheepDeathSlider.setMajorTickSpacing(5);
        sheepDeathSlider.setPaintTicks(true);
        sheepDeathSlider.setPaintLabels(true);
        sheepDeathSlider.setMaximumSize(new Dimension(340, 60));
        sheepDeathRow.add(sheepDeathSlider, BorderLayout.CENTER);
        preyPanel.add(sheepDeathRow);

        settingsPanel.add(preyPanel);
        settingsPanel.add(Box.createVerticalStrut(8));

        // === Predator (Wolf) Settings ===
        JPanel predPanel = new JPanel();
        predPanel.setLayout(new BoxLayout(predPanel, BoxLayout.Y_AXIS));
        predPanel.setOpaque(false);
        predPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        predPanel.setMaximumSize(new Dimension(360, Short.MAX_VALUE));

        JLabel predatorHeader = new JLabel("Predator (Wolf)");
        predatorHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        predatorHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        predPanel.add(predatorHeader);
        predPanel.add(Box.createVerticalStrut(6));

        JPanel wolfRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wolfRow.setOpaque(false);
        wolfRow.setMaximumSize(new Dimension(360, 40));
        JLabel wolfLabel = new JLabel("Initial Wolves (max " + popLimit + "):");
        wolfLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        final JSpinner wolfSpinner = new JSpinner(new SpinnerNumberModel(liveWolf, 0, popLimit, 1));
        wolfSpinner.setPreferredSize(new Dimension(80, 24));
        wolfRow.add(wolfLabel);
        wolfRow.add(wolfSpinner);
        predPanel.add(wolfRow);

        JPanel wolfBirthRow = new JPanel(new BorderLayout());
        wolfBirthRow.setOpaque(false);
        wolfBirthRow.setMaximumSize(new Dimension(360, 70));
        JLabel wolfBirthLabel = new JLabel("Birth threshold (lower -> more births):");
        wolfBirthLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        wolfBirthRow.add(wolfBirthLabel, BorderLayout.NORTH);
        final JSlider wolfBirthSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, wolfReproductionMin);
        wolfBirthSlider.setMajorTickSpacing(20);
        wolfBirthSlider.setPaintTicks(true);
        wolfBirthSlider.setPaintLabels(true);
        wolfBirthSlider.setMaximumSize(new Dimension(340, 60));
        wolfBirthRow.add(wolfBirthSlider, BorderLayout.CENTER);
        predPanel.add(wolfBirthRow);

        JPanel wolfDeathRow = new JPanel(new BorderLayout());
        wolfDeathRow.setOpaque(false);
        wolfDeathRow.setMaximumSize(new Dimension(360, 70));
        JLabel wolfDeathLabel = new JLabel("Death rate (energy loss per move):");
        wolfDeathLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        wolfDeathRow.add(wolfDeathLabel, BorderLayout.NORTH);
        final JSlider wolfDeathSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, energyLossPerStep);
        wolfDeathSlider.setMajorTickSpacing(10);
        wolfDeathSlider.setPaintTicks(true);
        wolfDeathSlider.setPaintLabels(true);
        wolfDeathSlider.setMaximumSize(new Dimension(340, 60));
        wolfDeathRow.add(wolfDeathSlider, BorderLayout.CENTER);
        predPanel.add(wolfDeathRow);

        settingsPanel.add(predPanel);
        settingsPanel.add(Box.createVerticalStrut(8));

        // reattach listeners for the new components
        sheepSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    int val = (Integer) sheepSpinner.getValue();
                    if (val > popLimit) {
                        JOptionPane.showMessageDialog(PredPreySim.this,
                                "Initial sheep cannot exceed " + popLimit + ". Value will be set to " + popLimit + ".",
                                "Population Limit",
                                JOptionPane.WARNING_MESSAGE);
                        val = popLimit;
                        sheepSpinner.setValue(val);
                    }
                    liveSheep = val;
                    System.out.println("Initial sheep set to: " + liveSheep);
                } catch (Exception ex) {
                }
            }
        });

        sheepBirthSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!sheepBirthSlider.getValueIsAdjusting()) {
                    sheepReproductionMin = sheepBirthSlider.getValue();
                    System.out.println("Sheep reproduction threshold set to: " + sheepReproductionMin);
                }
            }
        });

        sheepDeathSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!sheepDeathSlider.getValueIsAdjusting()) {
                    sheepEnergyLossPerStep = sheepDeathSlider.getValue();
                    System.out.println("Sheep energy loss per move set to: " + sheepEnergyLossPerStep);
                }
            }
        });

        wolfSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    int val = (Integer) wolfSpinner.getValue();
                    if (val > popLimit) {
                        JOptionPane.showMessageDialog(PredPreySim.this,
                                "Initial wolves cannot exceed " + popLimit + ". Value will be set to " + popLimit + ".",
                                "Population Limit",
                                JOptionPane.WARNING_MESSAGE);
                        val = popLimit;
                        wolfSpinner.setValue(val);
                    }
                    liveWolf = val;
                    System.out.println("Initial wolves set to: " + liveWolf);
                } catch (Exception ex) {
                }
            }
        });

        wolfBirthSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!wolfBirthSlider.getValueIsAdjusting()) {
                    wolfReproductionMin = wolfBirthSlider.getValue();
                    System.out.println("Wolf reproduction threshold set to: " + wolfReproductionMin);
                }
            }
        });

        wolfDeathSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!wolfDeathSlider.getValueIsAdjusting()) {
                    energyLossPerStep = wolfDeathSlider.getValue();
                    System.out.println("Wolf energy loss per move set to: " + energyLossPerStep);
                }
            }
        });

        // helper to keep button states consistent

        // Control buttons using CustomButton (smaller size)
        // create a transparent panel over the simulation area and stack controls vertically
        JPanel simControls = new JPanel();
        simControls.setLayout(new BoxLayout(simControls, BoxLayout.Y_AXIS));
        simControls.setOpaque(false);
        // place simControls on top of the simulation drawing area (same bounds as simulation area)
        int simX = 525;
        int simY = 50;
        int simW = 940;
        int simH = screenHeight - 350;
        simControls.setBounds(simX, simY, simW, simH);
        this.getLayeredPane().add(simControls, JLayeredPane.PALETTE_LAYER);

        applyBtn = new CustomButton("reset");
        applyBtn.setToolTipText("Apply current settings and restart world");
        applyBtn.setMaximumSize(new Dimension(60, 60));
        applyBtn.setPreferredSize(new Dimension(60, 60));
        applyBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        applyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // confirm with user
                int choice = JOptionPane.showConfirmDialog(PredPreySim.this,
                        "Apply settings and restart the world?",
                        "Confirm Restart",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }

                // disable the button and show wait cursor while restarting
                applyBtn.setEnabled(false);
                Cursor oldCursor = getCursor();
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    // remember whether simulation was running so we can restore that state
                    boolean wasRunning = simRunning;
                    // read control values
                    int newSheep = (Integer) sheepSpinner.getValue();
                    int newWolf = (Integer) wolfSpinner.getValue();
                    // enforce population cap at apply time as well
                    if (newSheep > popLimit) {
                        JOptionPane.showMessageDialog(PredPreySim.this,
                                "Initial sheep cannot exceed " + popLimit + ". Value will be set to " + popLimit + ".",
                                "Population Limit",
                                JOptionPane.WARNING_MESSAGE);
                        newSheep = popLimit;
                        sheepSpinner.setValue(newSheep);
                    }
                    if (newWolf > popLimit) {
                        JOptionPane.showMessageDialog(PredPreySim.this,
                                "Initial wolves cannot exceed " + popLimit + ". Value will be set to " + popLimit + ".",
                                "Population Limit",
                                JOptionPane.WARNING_MESSAGE);
                        newWolf = popLimit;
                        wolfSpinner.setValue(newWolf);
                    }
                    // starting energy is fixed in `Sheep.START_ENERGY` (not configurable)
                    // stop simulation while resetting
                    simRunning = false;
                    // clear current world
                    sheeplist.clear();
                    wolflist.clear();
                    liveGrass = 0;
                    // set counts
                    liveSheep = newSheep;
                    liveWolf = newWolf;
                    // reset step counter
                    step = 0;
                    // reload creatures immediately
                    load = false;
                    loadCreatures();
                    load = true;
                    // restore previous running/paused state
                    simRunning = wasRunning;
                    System.out.println("Applied settings and restarted world: sheep=" + newSheep + " wolves=" + newWolf);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    // restore UI state
                    setCursor(oldCursor);
                    applyBtn.setEnabled(true);
                    // ensure play/pause visuals reflect restored state
                    updateControlStates();
                }
            }
        });
        // add to the simControls panel so they sit inside the simulation area (left side)
        simControls.add(Box.createVerticalStrut(20));
        simControls.add(applyBtn);
        simControls.add(Box.createVerticalStrut(20));

        // Play and Pause buttons to control simulation
        playBtn = new CustomButton("play");
        playBtn.setToolTipText("Resume simulation");
        playBtn.setMaximumSize(new Dimension(60, 60));
        playBtn.setPreferredSize(new Dimension(60, 60));
        playBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        playBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simRunning = true;
                updateControlStates();
                System.out.println("Simulation resumed");
            }
        });
        simControls.add(playBtn);
        simControls.add(Box.createVerticalStrut(20));

        pauseBtn = new CustomButton("pause");
        pauseBtn.setToolTipText("Pause simulation");
        pauseBtn.setMaximumSize(new Dimension(60, 60));
        pauseBtn.setPreferredSize(new Dimension(60, 60));
        pauseBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        pauseBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simRunning = false;
                updateControlStates();
                System.out.println("Simulation paused");
            }
        });
        simControls.add(pauseBtn);

        // === Resources ===
        JPanel resourcesPanel = new JPanel();
        resourcesPanel.setLayout(new BoxLayout(resourcesPanel, BoxLayout.Y_AXIS));
        resourcesPanel.setOpaque(false);
        resourcesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        resourcesPanel.setMaximumSize(new Dimension(360, Short.MAX_VALUE));

        JLabel resourcesHeader = new JLabel("Resources");
        resourcesHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        resourcesHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        resourcesPanel.add(resourcesHeader);
        resourcesPanel.add(Box.createVerticalStrut(6));

        JPanel grassRow = new JPanel(new BorderLayout());
        grassRow.setOpaque(false);
        grassRow.setMaximumSize(new Dimension(360, 70));
        JLabel grassLabel = new JLabel("Grass growth rate:");
        grassLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        grassRow.add(grassLabel, BorderLayout.NORTH);
        final JSlider grassSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, grassGrowthRate);
        grassSlider.setMajorTickSpacing(20);
        grassSlider.setPaintTicks(true);
        grassSlider.setPaintLabels(true);
        grassSlider.setMaximumSize(new Dimension(340, 60));
        grassSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!grassSlider.getValueIsAdjusting()) {
                    grassGrowthRate = grassSlider.getValue();
                    System.out.println("Grass growth rate set to: " + grassGrowthRate);
                }
            }
        });
        grassRow.add(grassSlider, BorderLayout.CENTER);
        resourcesPanel.add(grassRow);

        settingsPanel.add(resourcesPanel);

        // initialize control visual states
        updateControlStates();
    }
}