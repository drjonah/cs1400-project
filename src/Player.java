
/*
 * THIS FILE IS THE PLAYER OR CLIENT
 */

import java.util.Scanner;

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

    private int playerID = 0;
    private boolean isTurn = false;

    private Scanner scnr = new Scanner(System.in);

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

            System.out.println("Welcome to Tic-Tac-Toe!");
            System.out.println("You are player " + playerID);

            // player 1 turn
            if (playerID == 1)
                isTurn = true;

            // Game Loop
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            if (isTurn) {
                                System.out.print("Move: ");
                                int move = scnr.nextInt();
                                
                                if (move < 0 || move > 8) {
                                    System.out.println("Illegal board position, try again...");
                                }
                                else {
                                    socketOutput.println(playerID);
                                    socketOutput.println(move);
    
                                    isTurn = false;
                                }
                            }
                            else {
                                System.out.println("Waiting for opponent's move...");
                                int oppMove = Integer.parseInt(socketInput.readLine());

                                if (oppMove == -1) 
                                    System.out.println("Second player not connected...");
                                else
                                    System.out.println("Opponent moved: " + oppMove);

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
 