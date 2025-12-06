package src;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.awt.Color;
/**
 *
 * @author Hayk-PC
 */
public class GameEngine extends JPanel{
    private final int FPS = 240;
    private final int MOTOR_WIDTH = 10;
    private final int MOTOR_HEIGHT = 10;
    private final int MOTOR_SPEED = 2;       
    
    private boolean paused = false;
    private Image background;
    private Image p1Image;
    private Image p2Image;
    private Motor player1;
    private Motor player2;
    private Timer newFrameTimer;
    
    
    public GameEngine() {
        super();
        player1 = new Motor(100, 300, MOTOR_WIDTH, MOTOR_HEIGHT, p1Image, Color.RED);
        player1.setVelx(MOTOR_SPEED);
        player1.setVely(0);

        player2 = new Motor(700, 300, MOTOR_WIDTH, MOTOR_HEIGHT, p2Image, Color.BLUE);
        player2.setVelx(-MOTOR_SPEED);
        player2.setVely(0);

        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "p1_left");
        this.getActionMap().put("p1_left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // FIXED: Check player1, not player2
                if (player1.getVelx() <= 0) { 
                    player1.setVelx(-MOTOR_SPEED);
                    player1.setVely(0);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "p1_right");
        this.getActionMap().put("p1_right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // FIXED: Check Velocity (getVelX), NOT Position (getX)
                if (player1.getVelx() >= 0) {
                    player1.setVelx(MOTOR_SPEED);
                    player1.setVely(0);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "p1_down");
        this.getActionMap().put("p1_down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player1.getVely() >= 0) { 
                    player1.setVelx(0);
                    player1.setVely(MOTOR_SPEED);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "p1_up");
        this.getActionMap().put("p1_up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // FIXED: Check player1, not player2
                if (player1.getVely() <= 0) { 
                    player1.setVelx(0);
                    player1.setVely(-MOTOR_SPEED);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "p2_left");
        this.getActionMap().put("p2_left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getVelx() <= 0) { 
                    player2.setVelx(-MOTOR_SPEED);
                    player2.setVely(0);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "p2_right");
        this.getActionMap().put("p2_right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getVelx() >= 0) {
                    player2.setVelx(MOTOR_SPEED);
                    player2.setVely(0);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "p2_down");
        this.getActionMap().put("p2_down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getVely() >= 0) { 
                    player2.setVelx(0);
                    player2.setVely(MOTOR_SPEED);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "p2_up");
        this.getActionMap().put("p2_up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (player2.getVely() <= 0) { 
                    player2.setVelx(0);
                    player2.setVely(-MOTOR_SPEED);
                }
            }
        });

        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });


    }
}
