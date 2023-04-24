
package connection;

import java.io.*;
import java.net.*;

/*
 * THIS FILE IS THE SERVER TO TALK TO THE DB
 */

public class Server extends Thread {

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

            clientSocket = server.accept();

            socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOutput = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                try {
                    int connection = Integer.parseInt(socketInput.readLine());
                    String content = socketInput.readLine();

                    System.out.println("client " + connection + ": " + content);
                    
                    if (content.equals("quit")) {
                        socketOutput.println("end");
                        break;
                    }

                    socketOutput.println(content);
                }
                catch(IOException i) {
                    System.out.println(i);
                }
            }

        }
        catch(IOException i)
        {
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
            server.close();

            return true;
        }
        catch (IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return false;
        }
    }
}
