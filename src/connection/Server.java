
package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * THIS FILE IS THE SERVER TO TALK TO THE DB
 */

public class Server extends Connection {

    private int connectedUsers = 0;

    private Socket clientSocket;
    private ServerSocket server;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    public Server(int socketPort) {
        super(socketPort);
    }

    public void connect() {
        // opening to the server
        try {
            server = new ServerSocket(super.getSocketPort());
            System.out.println("Server started...");

            while (true) {
                clientSocket = server.accept();

                socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientName = socketInput.readLine();

                if (connectedUsers < 2) {
                    connectedUsers++;

                    System.out.println("Client [" + clientName + "] has connected...");
                    System.out.println("Total conncted clients: " + connectedUsers);

                    ClientHandler client = new ClientHandler(clientSocket);
                    client.start();
                }
                else {
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

    static ArrayList<Socket> connectedClientScoket = new ArrayList<Socket>();

    private Socket clientSocket;
    private BufferedReader socketInput;

    public ClientHandler(Socket cSocket) {
        clientSocket = cSocket;
        connectedClientScoket.add(cSocket);
    }

    public void run() {
        // opening to the server
        try {
            socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // sending back to client
            while (true) {
                try {
                    String connection = socketInput.readLine();
                    String content = socketInput.readLine();

                    System.out.println("client " + connection + ": " + content);

                    // sending to the other client
                    for (Socket client: connectedClientScoket) {
                        if (client != clientSocket) {
                            PrintWriter socketOutput = new PrintWriter(client.getOutputStream(), true);
                            socketOutput.println(connection + ": " + content);
                        }
                    }
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
