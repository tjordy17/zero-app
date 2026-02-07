public class Wolf extends Creature {
    
    public Wolf(int energy) {
        super(energy);
    }

    @Override
    public void move(int x, int y) {
        //Wait to find out how big gamebaord is
    }

  @Override
    public boolean reproduce(Creature other) {
        if (other instanceof Wolf) { // Make sure a Wolf isn't trying to reproduce with a Sheep!
            Wolf partner = (Wolf) other;
            if (this.getEnergy() > 60 && partner.getEnergy() > 60) {
                Wolf offspring = new Wolf(20); 
                int energyUsed = getEnergy() / 2;
                int newEnergy = getEnergy() - energyUsed;
                setEnergy(newEnergy);
                return true;

            }
        }
        return false;
    }


    @Override
    public boolean eat(Creature prey){
        if (prey.isAlive()) {
            int energyGained = 10; // Gain energy from eating prey
            int newEnergy = getEnergy() + energyGained;
            setEnergy(newEnergy);
            prey.setEnergy(0); // Prey dies
            return true;
        } 
        return false;
        
    }
    
}
