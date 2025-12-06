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
import java.util.List;
import javax.swing.JOptionPane;
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
    private List<Trace> traces;
    
    
    public GameEngine() {
        super();
        restart();

        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "p1_left");
        this.getActionMap().put("p1_left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
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
        
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }
    
    public void restart() {
        traces = new ArrayList<>();
        player1 = new Motor(100, 300, MOTOR_WIDTH, MOTOR_HEIGHT, p1Image, Color.RED);
        player1.setVelx(MOTOR_SPEED);
        player1.setVely(0);
        player2 = new Motor(700, 300, MOTOR_WIDTH, MOTOR_HEIGHT, p2Image, Color.BLUE);
        player2.setVelx(-MOTOR_SPEED);
        player2.setVely(0);
        
        paused = false;
    }
    
            
    private void gameOver(String winner){
        newFrameTimer.stop();
        try {
            HighScores db = new HighScores(10); 
            db.increaseScore(winner);
        } 
        catch (Exception ex) {
            System.out.println("Database error: " + ex.getMessage());
        }

        int choice = JOptionPane.showConfirmDialog(this, 
                winner + " Wins!\nDo you want to play again?", 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            restart();
        } else {
            System.exit(0); 
        }
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 600, null);
        for(Trace t : traces){
            t.draw(grphcs);
        }
        
        player1.draw(grphcs);
        player2.draw(grphcs);
    }

    class NewFrameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                player1.move();
                player2.move();
                if (checkWallCollision(player1)) {
                    gameOver("Player 2 wins");
                    return;
                }
                if (checkWallCollision(player2)) {
                    gameOver("Player 1 wins");
                    return;
                }
                
                int safeZone = 10;
                for(int i = 0; i<traces.size() - safeZone; i++){
                    Trace t = traces.get(i);
                    
                    if(player1.collides(t)){
                        gameOver("Player 2");
                        return;
                    }
                    if(player2.collides(t)){
                        gameOver("Player 1");
                        return;
                    }
                }
                
                if(player1.collides(player2)){
                    gameOver("Friendship");
                    return;
                }
                
                traces.add(new Trace(player1.getX(), player1.getY(), MOTOR_WIDTH, MOTOR_HEIGHT, player1.getColor()));
                traces.add(new Trace(player2.getX(), player2.getY(), MOTOR_WIDTH, MOTOR_HEIGHT, player2.getColor()));
            }
            repaint();
        }
        
        private boolean checkWallCollision(Motor m) {
           return m.getX() < 0 || m.getX() + m.getWidth() > 800 || 
           m.getY() < 0 || m.getY() + m.getHeight() > 600;
        }

    }
}