package src;

import java.awt.Color;
import java.awt.Image;
/**
 *
 * @author Hayk-PC
 */
public class Motor extends Sprite{
    private double velX;
    private double velY;
    private Color color;
    
    public Motor(int x, int y, int width, int height, Image image, Color color){
        super(x,y,width,height,image);
        this.color = color;
    }
    
    public void move() {
        x+=this.velX;
        y+=this.velY;
    }
    
    public double getVelx() {
        return velX;
    }

    public void setVelx(double velx) {
        this.velX = velx;
    }
        
    public double getVely() {
        return velY;
    }

    public void setVely(double vely) {
        this.velY = vely;
    }
}
