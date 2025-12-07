

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
    // inside Motor.java

    @Override
    public void draw(Graphics g) {
        int visualSize = 40; 
        int drawX = x - (visualSize - width) / 2;
        int drawY = y - (visualSize - height) / 2;

        double angle = 0;
        
        if (getVelx() > 0) {
            angle = 90;
        } else if (getVelx() < 0) {
            angle = -90;
        } else if (getVely() > 0) {
            angle = 180;
        } else {
            angle = 0; 
        }

        Graphics2D g2d = (Graphics2D) g;
        
        AffineTransform oldTransform = g2d.getTransform(); 
        
        if (image != null) {
            g2d.rotate(Math.toRadians(angle), drawX + visualSize / 2, drawY + visualSize / 2);
            g2d.drawImage(image, drawX, drawY, visualSize, visualSize, null);
            g2d.setTransform(oldTransform);
        } else {
            g.setColor(new Color(255, 0, 0));
            g.fillRect(drawX, drawY, visualSize, visualSize);
        }
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
    
    public Color getColor(){
        return this.color;
    }
}