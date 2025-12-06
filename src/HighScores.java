package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;


public class HighScores {
    Connection connection;

    public HighScores(int maxScores) throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "root");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/highscores";
        connection = DriverManager.getConnection(dbURL, connectionProps);
    }
    
    
    public void increaseScore(String name) throws SQLException{
        String checkQuery = "SELECT SCORE FROM HIGHSCORES WHERE NAME = ?";
        PreparedStatement check = connection.prepareStatement(checkQuery);
        check.setString(1, name);
        ResultSet rs = check.executeQuery();
        
        if(rs.next()){
            String updateQuery = "UPDATE HIGHSCORES SET SCORE = SCORE + 1 WHERE NAME = ?";
            PreparedStatement update = connection.prepareStatement(updateQuery);
            update.setString(1, name);
            update.executeUpdate();
        }
        else{
            String insertQuery = "INSERT INTO HIGHSCORES (NAME, SCORE) VALUES(?,1)";
            PreparedStatement insert = connection.prepareStatement(insertQuery);
            insert.setString(1, name);
            insert.executeUpdate();
        }
    }

    public ArrayList<HighScore> getTopTen() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES ORDER BY SCORE DESC LIMIT 10";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }


    /**
     * Sort the high scores in descending order.
     * @param highScores 
     */
    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }
}
