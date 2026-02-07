public class Sheep extends Creature {
    public Sheep(int energy) {
        super(energy);
    }

    @Override
    public boolean reproduce(Creature other){
       if (other instanceof Sheep) { // Make sure
       //  a Wolf isn't trying to reproduce with a Sheep
            Sheep partner = (Sheep) other;
            if (this.getEnergy() > 60 && partner.getEnergy() > 60) {
                Sheep offspring = new Sheep(20); 
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
    
}
