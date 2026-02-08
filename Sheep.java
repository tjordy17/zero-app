import java.awt.*;
public class Sheep extends Creature {
    public static final Color White = new Color(245, 245, 245);
    public static final Color Grey = new Color(128, 128, 128);

    public Sheep(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean reproduce(Creature other){
       if (other instanceof Sheep) { // Make sure
       //  a Wolf isn't trying to reproduce with a Sheep
            Sheep partner = (Sheep) other;
            if (this.getEnergy() > 60 && partner.getEnergy() > 60) {
                //Sheep offspring = new Sheep(20); 
                int energyUsed = getEnergy() / 2;
                int newEnergy = getEnergy() - energyUsed;
                setEnergy(newEnergy);
                return true;
            }
        }
        return false;
    }    

    public void eatGrass() {
        int energyGained = 10; // Gain a fixed amount of energy from eating grass
        int newEnergy = getEnergy() + energyGained;
        setEnergy(newEnergy);
        System.out.println("Sheep eats grass and gains " + energyGained + " energy.");
    }

    public void draw(Graphics2D g2d){
        g2d.setColor(White);
        g2d.fillRect(x * 20 + 715, y * 20 + 100, 20, 20);//settings area
        g2d.setColor(Grey);
        g2d.drawRect(x * 20 + 715, y * 20 + 100, 20, 20);//settings area
    }   
}
