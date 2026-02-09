import java.awt.*;

public class Grass {
    int x, y;
    int growth;

    public static final Color Green = new Color(63, 222, 49);
    public static final Color Greenish = new Color(97, 187, 49);
    public static final Color GreenRed = new Color(132, 153, 49);
    public static final Color RedGreen = new Color(166, 118, 49);
    public static final Color Redish = new Color(201, 84, 49);
    public static final Color Red = new Color(235, 49, 49);

    public Grass(int x, int y, int growth){
        this.x = x;
        this.y = y;
        this.growth = growth;
    }

    public void draw(Graphics2D g2d){
        if(growth >= 100){
            g2d.setColor(Green);
        }else if(growth>=80 && growth<100 ){
            g2d.setColor(Greenish);
        }
        else if(growth>=60 && growth<80 ){
            g2d.setColor(GreenRed);
        }
        else if(growth>=40 && growth<60 ){
            g2d.setColor(RedGreen);
        }
        else if(growth>=20 && growth<40 ){
            g2d.setColor(Redish);
        }
        else if(growth>=0 && growth<20 ){
            g2d.setColor(Red);
        }

        g2d.fillRect(x * 20 + 715, y * 20 + 100, 20, 20);//settings area
    }

    public int getGrowth() {
        return growth;
    }
    public void setGrowth(int growth) {
        this.growth = growth;
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
}
