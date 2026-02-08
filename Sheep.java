import java.awt.*;
public class Sheep extends Creature {
    public static final Color White = new Color(245, 245, 245);
    public static final Color Grey = new Color(128, 128, 128);
    int energyGained = 10;

    public Sheep(int x, int y) {
        super(x, y);
        range = 2;//will check two spots around it
        energy = 30;
    }

    @Override
    public void reproduce(){
        setEnergy((int) getEnergy() / 2);
    }    

    public void eatGrass() {
        int energyGained = 10; // Gain a fixed amount of energy from eating grass
        energy+= energyGained;
        //System.out.println("Sheep eats grass and gains " + energyGained + " energy.");
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(White);
        //g2d.fillRect(x * 20 + 715, y * 20 + 100, 20, 20); check for overlap
        g2d.fillRect(x * 20 + 715 + 2, y * 20 + 100 + 2, 20 - 6, 20 - 6);//settings area
        g2d.setColor(Grey);
        g2d.drawRect(x * 20 + 715 + 2, y * 20 + 100 + 2, 20 - 6, 20 - 6);//settings area
    }   
}
