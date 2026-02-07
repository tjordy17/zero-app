public class Creature {
    private int energy;
    private int x;
    private int y;


    public Creature(int energy) {
        this.energy = 30;
        this.x = 0;
        this.y = 0;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public void move(int x, int y) {
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

    public void draw(int x, int y){
        
        
    }




}

