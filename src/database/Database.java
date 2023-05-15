package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    // this section logs the amount of turns that have passed, the user that made each move (X or O), and the location of each move on the board
    public static void insertPlayerToDB(int numberTurns, String playerPiece, int playerMove) {
        try {
            Connection con = RunDatabase.createC(); //establishes connection to MySQL
            String insertBoard = "insert into board(clientTurn,clientID,clientNumber) values(?,?,?)"; //inputs command to MySQL which stores the information into a board table in the database
            PreparedStatement pstmt = con.prepareStatement(insertBoard); //makes it so that you can execute the SQL statement multiple times
            pstmt.setInt(1, numberTurns); //setting the numTurn; basically used to store in the correct column
            pstmt.setString(2, playerPiece); //setting playerPiece; basically used to store in the correct column
            pstmt.setInt(3, playerMove); //setting the number they chose; basically used to store in the correct column

            pstmt.executeUpdate(); //executes the info to be stored in the database
        } 
        catch (Exception e) { 
            e.printStackTrace(); // catching any exceptions
        }
    }

    // this section logs the wins of X and O
    public static void insertStatsToDB(String playerPiece) {
        try {
            Connection con = RunDatabase.createC(); //establishes connection to MySQL
            String insertStats = "insert into stats(playerID, wins) values(?,1) on duplicate key update wins = wins + 1"; //inputs command to MySQL which stores information into a stats table in the database
            PreparedStatement pstmt = con.prepareStatement(insertStats); //makes it so that you can execute the SQL statement multiple times
            pstmt.setString(1, playerPiece); //setting the playerPiece that won; basically used to store in the correct column
            
            pstmt.executeUpdate(); // executes the info to be stored in the database
        } 
        catch (Exception e) {
            e.printStackTrace(); // catching any exceptions
        }
    }

    // this section resets the game after a game has been completed
    public static void resetGame() {
        
        Connection con = RunDatabase.createC(); //establishes connection to MySQL
        PreparedStatement stmt = null; 
        try {
            System.out.println("The game has been reset");
            String query1 = "delete from board"; // this string will be used in MySQL to reset the board
            stmt = con.prepareStatement(query1); //makes it so that you can execute the SQL statement multiple times
            stmt.executeUpdate(query1); // executes the reset of the board
      
        } 
        catch (SQLException excep) {
            excep.printStackTrace(); //catches any errors when connecting MySQL
        } 
        catch (Exception excep) {
            excep.printStackTrace(); // catches any exceptions
        } 
        finally {
            try {
                if (stmt != null)
                    con.close();
            } 
            catch (SQLException se) {
                se.printStackTrace(); //catches any errors when connecting MySQL for stmt
            }
            try {
                if (con != null)
                    con.close();
            } 
            catch (SQLException se) {
                se.printStackTrace(); //catches any errors when connecting MySQL for con
            }
        }
    }
}