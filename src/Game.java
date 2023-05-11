import jdbc.AppPlayers;

import java.util.*;

public class Game {
	
    private AppPlayers serverAccess;

	private Scanner scnr = new Scanner(System.in);
	private String board[] = {"-", "-", "-", "-", "-", "-", "-", "-", "-"};
    private String userPiece, oppPiece;
    private int numberTurns = 0;

	public Game(int playerID) {

        if(playerID == 1) {
			userPiece = "X";
			oppPiece = "O";
		}
		else {
			userPiece = "O";
			oppPiece = "X";
		}
	}

	public boolean checkWinner() { // Function to check for winner
		for(int i = 0; i < 8; i++) {

			String line = null;

			switch(i) {
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
			if(line.equals("XXX")) {
                serverAccess.insertStatsToDB("X");
                serverAccess.resetGame();
				return true;
			}
			// O WINNER
			else if(line.equals("OOO")) {
                serverAccess.insertStatsToDB("O");
                serverAccess.resetGame();
				return true;
			}
		}
		return false;
	}

	public boolean checkDraw() {   
        for (int i = 0; i < 9; i++) {
            if (board[i].equals("-")) {
                return false;
            }
        }
        return true;
    }

	public int move() {
		int numInput;

		while (true) {
		
			try {
				System.out.print("Enter a number to input: ");
                numInput = scnr.nextInt();
				// move on board || move occupied
				if(!(numInput > 0 && numInput <= 9) || board[numInput-1].equalsIgnoreCase("X") || board[numInput-1].equalsIgnoreCase("O") ) {
					throw new InputMismatchException();
				}
                serverAccess.insertPlayerToDB(numberTurns, userPiece, numInput);
				break;
			} 
			catch (InputMismatchException ime) {
				System.out.println("Invalid input, please re-enter your slot number.");
				continue;
			}
		}

		return numInput;
	}

	public void update(int position, boolean userMove) {
		if(userMove == true) {
			board[position-1] = userPiece;
        }
        else {
			board[position-1] = oppPiece;
		}
	}

	public void printBoard() {
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

    public void clearBoard() {
        for (int i = 0; i < board.length; i++)
            board[i] = "-";
    }

    public void incrementNumberTurns() {
        numberTurns += 1;
    }

}
