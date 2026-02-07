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
    // private Grass [][] grassArray = new Grass[gridHeight][gridWidth];
    // private Wolf [][] wolfArray = new Wolf[gridHeight][gridWidth];
    // private Sheep [][] sheepArray = new Sheep[gridHeight][gridWidth];

    int initGrass = 600;
    int initWolf = 30;
    int initSheep = 100;
    
    //------UI------
    int screenWidth = getScreenWidth() - 10; 
    int screenHeight = getScreenHeight() - 100;
    
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
            System.out.print(screenHeight - 300);
            load = true;
        }
    }

    //-----DRAWING STUFF!---------
    @Override
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if(load){
            background(g2d);
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
    }

    private void background(Graphics2D g2d){
        g2d.setColor(DARKBEIGE);
        g2d.fillRect(0, 0, getScreenWidth(), getScreenHeight());

        g2d.setColor(DARKERBEIGE);
        g2d.fillRect(50, 50, 400, screenHeight - 50);//settings area

        g2d.fillRect(525, 50, 940, screenHeight - 300);//simulation area
        
        g2d.fillRect(525, 600, 940, 200);//stats area
    }

    //------MAIN & HELPER FUNCTIONS-------------
    public static void main(){
        
        PredPreySim run = new PredPreySim();
        
    }
    public int getScreenWidth() {
        return getWidth();
    }

    public int getScreenHeight() {
        return getHeight();
    }

    public PredPreySim(){
        super("PredPreySim", (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 10, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100);
    }
}