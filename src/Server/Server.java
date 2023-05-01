
/*
 * THIS FILE IS THE SERVER TO TALK TO THE DB
 */

package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Data {

    private int connectedUsers = 0; // total connections

    private ServerSocket server; // the servers socket
    private Socket clientSocket; // client's socket that is attempting to connect
    private PrintWriter socketOutput; // client's output that is attempting to connect

    public Server(int socketPort) {
        super(socketPort);
    }

    public void connect() {
        // opening to the server
        try {
            server = new ServerSocket(super.getSocketPort());
            System.out.println("Server started...");

            while (true) {
                clientSocket = server.accept(); // accepts a client

                socketOutput = new PrintWriter(clientSocket.getOutputStream(), true); // connects to the clients output

                // ensures that there is only two players
                if (connectedUsers < 2) {
                    connectedUsers += 1;

                    // 1 = 'x' (player 1), 2 = 'o' (player 2)
                    int playerName = connectedUsers;
                    socketOutput.println(playerName);

                    System.out.println("Player [" + playerName + "] has connected...");
                    System.out.println("Total conncted clients: " + connectedUsers);

                    // allows the client to continue the connection
                    ClientHandler client = new ClientHandler(clientSocket, playerName);
                    client.start();
                }
                else {
                    // sends a message back to the user and terminates their connection
                    socketOutput.println("Cannot join server (possible max users)...");
                    clientSocket.close();
                }
            }
        }
        catch(IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return;
        }
    }
}

class ClientHandler extends Thread {
    /*
     * This class handles multiple clients that connect to the server
     * This acts as a mediator between the two clients
     */

    // static ArrayList<Socket> connectedClientScoket = new ArrayList<Socket>(); // array of all connected clients (players/users)
    static Socket[] connectedClientSocket = new Socket[2];

    private Socket clientSocket; // individual client
    private BufferedReader socketInput; // individual client input

    public ClientHandler(Socket cSocket, int playerName) {
        clientSocket = cSocket;
        connectedClientSocket[playerName - 1] = cSocket;
    }

    public void run() {
        // opening to the server
        try {
            socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // connects to the clients input

            // sending back to client
            while (true) {
                try {
                    // reads data sent to the server 
                    int connection = Integer.parseInt(socketInput.readLine()); 
                    int playerclientMove = Integer.parseInt(socketInput.readLine());

                    System.out.println("Player " + connection + ": " + playerclientMove);

                    // sending to the other client
                    Socket oppSocket = connectedClientSocket[connection%2];
                    PrintWriter socketOutput = new PrintWriter(oppSocket.getOutputStream(), true);
                    socketOutput.println(playerclientMove);
                }
                catch(IOException i) {
                    System.out.println(i);
                }
            }

        }
        catch(IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return;
        }
    }
}
