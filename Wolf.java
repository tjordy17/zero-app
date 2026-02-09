import java.awt.*;

public class Wolf extends Creature {

    public static final Color Dark = new Color(70, 70, 70);
    public static final Color Black = new Color(0, 0, 0);

    public Wolf(int x, int y) {
        super(x, y);
        range = 2;
        energy = 30;
    }

    @Override
    public void move() {
        //Wait to find out how big gamebaord is
    }

    @Override
    public void reproduce() {
        // if (other instanceof Wolf) { // Make sure a Wolf isn't trying to reproduce with a Sheep!
        //     Wolf partner = (Wolf) other;
        //     if (this.getEnergy() > 60 && partner.getEnergy() > 60) {
        //         //Wolf offspring = new Wolf(20); 
        //         int energyUsed = getEnergy() / 2;
        //         int newEnergy = getEnergy() - energyUsed;
        //         setEnergy(newEnergy);
        //         //return true;

        //     }
        // }
        // //return false;
    }


    @Override
    public void eat(){
        int energyGained = 10; // Gain a fixed amount of energy from eating grass
        energy+= energyGained;
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(Dark);
        g2d.fillRect(x * 20 + 715 + 1, y * 20 + 100 + 1, 20 - 5, 20 - 5);//settings area
        g2d.setColor(Black);
        g2d.drawRect(x * 20 + 715 + 1, y * 20 + 100 + 1, 20 - 5, 20 - 5);//settings area
    }  
}
