

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Hayk-PC
 */
public class TronGUI extends JFrame{
    private GameEngine gameArea;
    
    public TronGUI(){
        setTitle("Tron Light Cycles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        String p1Name = JOptionPane.showInputDialog(this, "Player 1 Name:", "Player 1");
        if (p1Name == null || p1Name.trim().isEmpty()) p1Name = "Player 1";
        
        Color p1Color = JColorChooser.showDialog(this, "Choose Player 1 Color", Color.RED);
        if (p1Color == null) p1Color = Color.RED;

        String p2Name = JOptionPane.showInputDialog(this, "Player 2 Name:", "Player 2");
        if (p2Name == null || p2Name.trim().isEmpty()) p2Name = "Player 2";
        
        Color p2Color = JColorChooser.showDialog(this, "Choose Player 2 Color", Color.BLUE);
        if (p2Color == null) p2Color = Color.BLUE;
        
        gameArea = new GameEngine(p1Name, p1Color, p2Name, p2Color);
        gameArea.setPreferredSize(new Dimension(800, 600));
        add(gameArea);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> gameArea.restart());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(restartItem);
        gameMenu.add(exitItem);
        
        JMenu scoreMenu = new JMenu("High Scores");
        JMenuItem top10Item = new JMenuItem("Top 10");
        top10Item.addActionListener(e -> showHighScores());
        scoreMenu.add(top10Item);

        menuBar.add(gameMenu);
        menuBar.add(scoreMenu);
        setJMenuBar(menuBar);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void showHighScores() {
        try {
            HighScores db = new HighScores(10);
            ArrayList<HighScore> scores = db.getTopTen();
            
            StringBuilder sb = new StringBuilder();
            sb.append("TOP 10 WINNERS:\n-----------------\n");
            for (HighScore h : scores) {
                sb.append(h.getName()).append(": ").append(h.getScore()).append(" wins\n");
            }
            
            JOptionPane.showMessageDialog(this, sb.toString(), "High Scores", JOptionPane.INFORMATION_MESSAGE);
            
        } 
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}