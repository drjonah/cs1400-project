
package connection;

import java.io.*;
import java.net.*;

/*
 * THIS FILE IS THE SERVER TO TALK TO THE DB
 */

public class Server extends Connection {

    private Socket clientSocket;
    private ServerSocket server;

    public Server(int socketPort) {
        super(socketPort);
    }

    public void connect() {
        // opening to the server
        try {
            server = new ServerSocket(super.getSocketPort());
            // server = new ServerSocket();
            System.out.println("Server started...");

            while (true) {
                clientSocket = server.accept();

                System.out.println("A client has connected...");

                ClientHandler client = new ClientHandler(clientSocket);
                client.start();
            }
        }
        catch(IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return;
        }
    }

    public boolean disconnect() {
        // responsible for closing the server
        try {
            server.close();

            return true;
        }
        catch (IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return false;
        }
    }
}


class ClientHandler extends Thread {

    private Socket clientSocket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    public ClientHandler(Socket cSocket) {
        clientSocket = cSocket;
        // start();
    }

    public void run() {
        // opening to the server
        try {
            socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);

            // sending back to client
            while (true) {
                try {
                    int connection = Integer.parseInt(socketInput.readLine());
                    String content = socketInput.readLine();

                    System.out.println("client " + connection + ": " + content);
                    
                    if (content.equals("quit")) {
                        disconnect();
                        break;
                    }

                    socketOutput.println(content);
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

    public boolean disconnect() {
        // responsible for closing the server
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
