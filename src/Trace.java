package src;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Hayk-PC
 */
public class Trace extends Sprite {
    private Color color;

    public Trace(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, null); 
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
