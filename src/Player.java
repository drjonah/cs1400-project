
/*
 * THIS FILE IS THE PLAYER OR CLIENT
 */

import Server.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Player extends Data {

    private Socket clientSocket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    Game game;

    private int playerID = 0;
    private boolean isTurn = false;

    public Player(String address, int port) {
        super(address, port);
    }

    public void connect() {

        // responsible for opening the socket
        try {
            clientSocket = new Socket(super.getSocketAddress(), super.getSocketPort());
            
            socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // reading input
            socketOutput = new PrintWriter(clientSocket.getOutputStream(), true); // sending responses
            
            playerID = Integer.parseInt(socketInput.readLine()); // recieves name from connection

            // player 1 turn
            if (playerID == 1)
                isTurn = true;

            System.out.println("+------------------------+");
            System.out.println("| Welcome to TIC TAC TOE |");
            System.out.println("+------------------------+");
            System.out.println();
            System.out.println("You are player " + playerID);

            System.out.println("X will play first.");

            game = new Game(playerID);

            // Game Loop
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (isTurn) {
                                game.printBoard();   
                                
                                int position = game.move();
                                game.update(position, isTurn);

                                socketOutput.println(playerID);
                                socketOutput.println(position);

                                boolean isWon = game.checkWinner();
                                boolean isDraw = game.checkDraw();

                                if(isWon == true) {
                                    System.out.println("YOU WIN!");
                                    disconnect();
                                    break;
                                }

                                if(isDraw == true) {
                                    System.out.println("YOU TIED!");
                                    disconnect();
                                    break;
                                }

                                isTurn = false;
                            }
                            else {
                                System.out.println("Waiting for opponent's move...");
                                int oppPosition = Integer.parseInt(socketInput.readLine());
                                game.update(oppPosition, isTurn);

                                if (oppPosition == -1) 
                                    System.out.println("Second player not connected...");
                                else {
                                    // opponent has recieved the move, prints the board, and checks if the move added completes the game
                                    System.out.println("Opponent moved: " + oppPosition);
                                
                                    boolean isWon = game.checkWinner();
                                    boolean isDraw = game.checkDraw();

                                    if(isWon == true) {
                                        System.out.println("YOU LOST!");
                                        disconnect();
                                        break;
                                    }

                                    if(isDraw == true) {
                                        System.out.println("YOU TIED!");
                                        disconnect();
                                        break;
                                    }
                                }

                                isTurn = true;
                            }

                        }
                        catch (NumberFormatException i) {
                            System.out.println("Illegal input, try again...");
                        }
                        catch (IOException i) {
                            System.out.println("Unknown error has occured [" + i + "]!");
                        }                 
                    }
                }
            });
            receiveThread.start();
        }
        catch (UnknownHostException u) {
            System.out.println("Server not found [" + u + "]!");
            return;
        }
        catch (IOException i) {
            System.out.println("Unknown error has occured [" + i + "]!");
            return;
        }
    }

    public boolean disconnect() {
        try {
            socketOutput.close();
            socketInput.close();
            clientSocket.close();

            return true;
        }
        catch (IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return false;
        }
    }
}
 