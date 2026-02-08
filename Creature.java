import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Creature {
    protected int energy;
    protected int x;
    protected int y;


    public Creature(int x, int y) {
        this.energy = 30;
        this.x = x;
        this.y = y;
    }

    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void move() {
        // move the creature to a new position (x, y)


    }
    public boolean reproduce(Creature other) {
        //in subclasses create new object of tyeop wolf or sheep
        return false;
    }

    //not sure how to connect sheep to eating t
    public boolean eat(Creature prey) {

        //in subclass delete an object of oposite type and gain energy from it
        return false;
    }

    public void draw(Graphics2D g2d){
        
    }
}

