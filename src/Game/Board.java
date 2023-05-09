package Game;
import java.util.*;

public class Board {

  static String[] board;
  static String turn;

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

    System.out.println("It is " + turn + "'s turn, enter a number slot to place: ");
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

  public static void main(String [] args) {

    Scanner scnr = new Scanner(System.in);
    board = new String[9];
    turn = "X";
    String winner = null;

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
      
      int numInput;

      try {
      
        numInput = scnr.nextInt();

        if(!(numInput > 0 && numInput <= 9))
        {
          throw new InputMismatchException();
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

      System.out.println("Congratulations! " + winner + ". Thanks for playing!");

    }

  }

}