package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement; // Note: this import is for MySQL, if using a different database, a different statement import may be needed


public class AppPlayers {

    public void insertPlayerToDB(int numberTurns, String playerPiece, int playerMove) {
        try {
            Connection con = App.createC();
            String insertBoard = "insert into board(clientTurn,clientID,clientNumber) values(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(insertBoard);
            pstmt.setInt(1, numberTurns);
            pstmt.setString(2, playerPiece);
            pstmt.setInt(3, playerMove);

            pstmt.executeUpdate();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertStatsToDB(String playerPiece) {
        try {
            Connection con = App.createC();
            String insertStats = "insert into stats(playerID, wins) values(?,0) on duplicate key update wins = wins + 1";
            PreparedStatement pstmt = con.prepareStatement(insertStats);
            pstmt.setString(1, playerPiece);
            
            pstmt.executeUpdate();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetGame() {
        
        Connection conn = App.createC();
        Statement stmt = null;
        try {
            System.out.println("Record the game has been reset.");
            stmt = (Statement) conn.createStatement();
            String query1 = "delete from board";
            stmt.executeUpdate(query1);
      
        } 
        catch (SQLException excep) {
            excep.printStackTrace();
        } 
        catch (Exception excep) {
            excep.printStackTrace();
        } 
        finally {
            try {
                if (stmt != null)
                    conn.close();
            } 
            catch (SQLException se) {
                se.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } 
            catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}