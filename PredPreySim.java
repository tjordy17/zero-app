import javax.swing.*;
//import java.awt.event.*;
//import java.io.*; 
//import javax.imageio.*; 
import java.awt.*;
import java.util.*;

public class PredPreySim extends BaseFrame{

    //------Logic------
    private boolean load = false;

    int gridWidth = 30, gridHeight = 20;
    private int [][] squares = new int[gridHeight][gridWidth];
    private Grass [][] grassArray = new Grass[gridHeight][gridWidth];
    private ArrayList<Wolf> wolflist = new ArrayList<Wolf>();
    private ArrayList<Sheep> sheeplist = new ArrayList<Sheep>();

    int liveGrass = 0;
    int liveWolf = 0;
    int liveSheep = 0;
    
    //------UI------
    int screenWidth = 1530; 
    int screenHeight = 850;
    
    //fonts
    public static final Font bahnschrift32 = new Font("Bahnschrift", Font.PLAIN, 32);
    public static final Font bahnschrift64 = new Font("Bahnschrift", Font.PLAIN, 64);
    //lines
    public static final BasicStroke thickPen = new BasicStroke(10);
    public static final BasicStroke medPen = new BasicStroke(4);
    public static final BasicStroke thinPen = new BasicStroke(2);
    //colors
    public static final Color BURNTORANGE = new Color(204, 85, 0);
    public static final Color BURNTORANGELITE = new Color(201, 136, 89);
    public static final Color BEIGEORANGE = new Color(254, 245, 231);
    public static final Color DARKBEIGE = new Color(217, 203, 182);
    public static final Color DARKERBEIGE = new Color(176, 163, 144);

    //----LOGIC & OTHER COOL STUFF---------
    @Override
    public void move(){
        //System.out.print("Working");
        if(!load){//loads objects
            System.out.print(screenWidth);
            loadCreatures();
            load = true;
        }
        runGrid();
    }

    private void loadCreatures(){
        for(int i = 0; i< gridWidth;i++){
            for(int j = 0; j< gridHeight ; j++){
                grassArray[j][i] = new Grass(i, j, randint(0,300));
                if(grassArray[j][i].getGrowth() >= 100 ){
                    liveGrass++;
                }
            }
        }

        for(int i = 0; i<liveSheep; i++){
            int x, y;
            x = randint(0, gridWidth-1);
            y = randint(0, gridHeight-1);
            for(int j = 0; j < i; j++){
                while(sheeplist.get(j).getX() == x && sheeplist.get(j).getY() == y){
                    x = randint(0, gridWidth-1);
                    y = randint(0, gridHeight-1);
                }   
            }
            sheeplist.add(new Sheep(x, y));
        }

        for(int i = 0; i<liveWolf; i++){
            int x, y;
            x = randint(0, gridWidth-1);
            y = randint(0, gridHeight-1);
            for(int j = 0; j < i; j++){
                for(int k = 0; k < sheeplist.size(); k++){
                    while((wolflist.get(j).getX() == x && wolflist.get(j).getY() == y) || (sheeplist.get(k).getX() == x && sheeplist.get(k).getY() == y)){
                        x = randint(0, gridWidth-1);
                        y = randint(0, gridHeight-1);
                    }  
                } 
            }
            wolflist.add(new Wolf(x, y));
        }
        
    }

    private void runGrid(){
        for(int i = 0; i < squares[0].length; i++){
                for(int j = 0; j < squares.length; j++){
                    
                }
            }

    }

    //-----DRAWING STUFF!---------
    @Override
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if(load){
            background(g2d);
            drawGrid(g2d); 
            drawGrass(g2d);
            drawSheep(g2d);
            drawWolf(g2d);
        }
    }

    //draws simulation elements
    private void drawGrid(Graphics2D g2d){
        boolean flag = true;
            for(int i = 0; i < squares[0].length; i++){
                for(int j = 0; j < squares.length; j++){
                    if(flag){
                        g2d.setColor(BURNTORANGE);  
                    }else{
                        g2d.setColor(BURNTORANGELITE);
                    }
                    flag = !flag;
                    g2d.fillRect(i * 20 + 715, j * 20 + 100, 20, 20);//settings area
                }
                flag = !flag;
            }
    }

    private void drawSheep(Graphics2D g2d){
        for(int i = 0; i<sheeplist.size(); i++){
            sheeplist.get(i).draw(g2d);
        }   
    }

    private void drawWolf(Graphics2D g2d){
        for(int i = 0; i<wolflist.size(); i++){
            wolflist.get(i).draw(g2d);
        }   
    }

    private void drawGrass(Graphics2D g2d){
        for(int i = 0; i< gridWidth;i++){
            for(int j = 0; j< gridHeight ; j++){
                grassArray[j][i].draw(g2d);
            }
        }
    }

    //draws background elements
    private void background(Graphics2D g2d){
        g2d.setColor(DARKBEIGE);
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        g2d.setColor(DARKERBEIGE);
        g2d.fillRect(50, 50, 400, screenHeight - 100);//settings area

        g2d.fillRect(525, 50, 940, screenHeight - 350);//simulation area
        
        g2d.fillRect(525, 590, 940, 200);//stats area
    }

    //------MAIN & HELPER FUNCTIONS-------------
    public static void main(){
        PredPreySim run = new PredPreySim();
    }

    // public int getScreenWidth() {
    //     return getWidth();
    // }

    // public int getScreenHeight() {
    //     return getHeight();
    // }

    private int randint(int a, int b){
        int range = b-a+1;
        return (int)(Math.random()*range + a );
    }


    public PredPreySim(){
        super("PredPreySim", 1530, 850);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
    }
}