import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Creature {
    protected int energy;
    protected int x;
    protected int y;
    protected int range;
    protected boolean turn = true;

    public Creature(int x, int y) {
        this.energy = 30;
        this.x = x;
        this.y = y;
    }

    public boolean getTurn() {
        return turn;
    }
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int getEnergy() {
        return energy;
    }
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getRange(){
        return range;
    }

    public void setRange(int range){
        this.range = range;
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
    public void reproduce() {
        //in subclasses create new object of tyeop wolf or sheep
    }

    public void eat() {

    }

    public boolean eat(Creature prey) {
        return false;
    }

    public void draw(Graphics2D g2d){
        
    }
}

