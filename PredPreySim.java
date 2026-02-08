import javax.swing.*;
//import java.awt.event.*;
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
    // ------UI------
    int screenWidth = 1530;
    int screenHeight = 850;

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
                            sheep.setEnergy(sheep.getEnergy() - 5);
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
         // Create a slider in the settings area to control starting energy for new sheep
        JSlider energySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, Sheep.START_ENERGY);
        energySlider.setMajorTickSpacing(10);
        energySlider.setPaintTicks(true);
        energySlider.setPaintLabels(true);
        energySlider.setSnapToTicks(true);
        // place slider inside the settings panel area (x=50..450, y=50..screenHeight-50)
        energySlider.setBounds(75, 120, 350, 60);
        energySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (!energySlider.getValueIsAdjusting()) {
                    int val = energySlider.getValue();
                    Sheep.START_ENERGY = val;
                    System.out.println("Starting energy set to: " + val);
                }
            }
        });
        // add slider to layered pane so it displays over the drawing panel
        this.getLayeredPane().add(energySlider, JLayeredPane.PALETTE_LAYER);
        // --- Initial population controls ---
        JLabel sheepLabel = new JLabel("Initial Sheep:");
        sheepLabel.setBounds(75, 200, 120, 25);
        sheepLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        this.getLayeredPane().add(sheepLabel, JLayeredPane.PALETTE_LAYER);

        JSpinner sheepSpinner = new JSpinner(new SpinnerNumberModel(liveSheep, 0, gridWidth * gridHeight, 1));
        sheepSpinner.setBounds(190, 200, 80, 25);
        sheepSpinner.setToolTipText("Set starting number of sheep");
        sheepSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    liveSheep = (Integer) sheepSpinner.getValue();
                    System.out.println("Initial sheep set to: " + liveSheep);
                } catch (Exception ex) {
                }
            }
        });
        this.getLayeredPane().add(sheepSpinner, JLayeredPane.PALETTE_LAYER);

        JLabel wolfLabel = new JLabel("Initial Wolves:");
        wolfLabel.setBounds(75, 235, 120, 25);
        wolfLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        this.getLayeredPane().add(wolfLabel, JLayeredPane.PALETTE_LAYER);

        JSpinner wolfSpinner = new JSpinner(new SpinnerNumberModel(liveWolf, 0, gridWidth * gridHeight, 1));
        wolfSpinner.setBounds(190, 235, 80, 25);
        wolfSpinner.setToolTipText("Set starting number of wolves");
        wolfSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                try {
                    liveWolf = (Integer) wolfSpinner.getValue();
                    System.out.println("Initial wolves set to: " + liveWolf);
                } catch (Exception ex) {
                }
            }
        });
        this.getLayeredPane().add(wolfSpinner, JLayeredPane.PALETTE_LAYER);
    }
}