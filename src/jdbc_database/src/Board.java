import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Board {

  static String[] board;
  static String turn;
  static int numInput;

  

  // checkWinner() decides combination of three boxes.
  public static String checkWinner() 
  {

    for(int i = 0; i < 8; i++) 
    {

      String line = null;

      switch(i) 
      {
        case 0:
          line = board[0] + board[1] + board[2];
          break;
        case 1:
          line = board[3] + board[4] + board[5];
          break;
        case 2:
          line = board[6] + board[7] + board[8];
          break;
        case 3:
          line = board[0] + board[3] + board[6];
          break;
        case 4:
          line = board[1] + board[4] + board[7];
          break;
        case 5:
          line = board[2] + board[5] + board[8];
          break;
        case 6:
          line = board[0] + board[4] + board[8];
          break;
        case 7:
          line = board[2] + board[4] + board[6];
          break;
      }

      // X WINNER
      if(line.equals("XXX")) 
      {
        return "X WINS!";
      }

      // O WINNER
      else if(line.equals("OOO")) 
      {
        return "O WINS!";
      }
    
    }
  


    for (int i = 0; i < 9; i++) 
    {
      if (Arrays.asList(board).contains(String.valueOf(i+1))) 
      {
        break;
      }
      else if (i==8) 
      {
        return "It's a draw!";
      }
    }

    return null;

  }

  public static void printBoard()
  {
    System.out.printf("||-------||-------||-------||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||   %s   ||   %s   ||   %s   ||%n", board[0], board[1], board[2]);
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||-------||-------||-------||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||   %s   ||   %s   ||   %s   ||%n", board[3], board[4], board[5]);
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||-------||-------||-------||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||   %s   ||   %s   ||   %s   ||%n", board[6], board[7], board[8]);
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||-------||-------||-------||%n");
  }

  public static void main(String [] args)throws IOException {

    Scanner scnr = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    board = new String[9];
    turn = "X";
    String winner = null;
    int turnCounter = 0;


    for(int i = 0; i < 9; i++)
    {
      board[i] = String.valueOf(i + 1);
      
    }

    System.out.println("+------------------------+");
    System.out.println("| Welcome to TIC TAC TOE |");
    System.out.println("+------------------------+");
    System.out.println();

    System.out.println("X will play first. Enter a number that corresponds to a point on the board as shown in the example below.");
    System.out.println();

    System.out.printf("||-------||-------||-------||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||   1   ||   2   ||   3   ||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||-------||-------||-------||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||   4   ||   5   ||   6   ||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||-------||-------||-------||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||   7   ||   8   ||   9   ||%n");
    System.out.printf("||       ||       ||       ||%n");
    System.out.printf("||-------||-------||-------||%n");

    while (winner == null) {
      turnCounter++;

      System.out.println("It is " + turn + "'s turn, enter a number slot to place.");
      int numInput;

      try {
        numInput = scnr.nextInt();



        Player boardP = new Player(turnCounter, turn, numInput);
        boolean answer = AppPlayers.insertPlayerToDB(boardP);
        if (answer) {
        System.out.println("Your action has been recorded...");
        }
      else {
        System.out.println("Something went wrong, please try again...");
       }

        if(!(numInput > 0 && numInput <= 9))
        {
          System.out.println("Invalid input, please re-enter your slot number.");
          continue;
        }
      
      } 
      catch (InputMismatchException ime) {
        System.out.println("Invalid input, please re-enter your slot number.");
        continue;
      }



      if (board[numInput-1].equals(String.valueOf(numInput)))
      {
        board[numInput-1] = turn;

        if(turn.equals("X"))
        {
          turn = "O";
        }
        else 
        {
          turn = "X";
        }

        printBoard();
        winner = checkWinner();

      }
      else {
        System.out.println("Slot taken already, please re-enter your slot number.");
      }

    }

    if (winner.equalsIgnoreCase("draw")) {

      System.out.println("It's a draw! Thanks for playing.");

    }

    else {

      System.out.println("Congratulations! " + winner);

    }

    Connection conn = null;
    Statement stmt = null;
       try {
          conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/tictactoe", "root", "Jlsql!");
          System.out.println("Record the game has been reset.");
          stmt = (Statement) conn.createStatement();
          String query1 = "delete from board";
          stmt.executeUpdate(query1);
          
       } catch (SQLException excep) {
          excep.printStackTrace();
       } catch (Exception excep) {
          excep.printStackTrace();
       } finally {
          try {
             if (stmt != null)
             conn.close();
          } catch (SQLException se) {}
          try {
             if (conn != null)
             conn.close();
          } catch (SQLException se) {
             se.printStackTrace();
          }
       }

  }

}
