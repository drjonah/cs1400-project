
package connection;

import java.io.*;
import java.net.*;

/*
 * THIS FILE IS THE PLAYER OR CLIENT
 */

public class Client extends Connection {

    private Socket clientSocket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    private int clientId;

    public Client(String address, int port) {
        super(address, port);
        clientId = super.getConnectionsTotal();
    }

    public void connect() {

        // responsible for opening the socket
        try {
            clientSocket = new Socket(super.getSocketAddress(), super.getSocketPort());
            System.out.println("Socket connected...");

            socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // reading input
            socketOutput = new PrintWriter(clientSocket.getOutputStream(), true); // sending responses
        }
        catch (UnknownHostException u) {
            System.out.println("IP address not found [" + u + "]");
            return;
        }
        catch (IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return;
        }
    }

    public String send(String content) {
        try {
            socketOutput.println(clientId);
            socketOutput.println(content);

            String serverResponse = socketInput.readLine();

            if (serverResponse.equals("bye"))
                return null;

            return serverResponse;
        }
        catch (IOException i) {
            System.out.println("Unknown error has occured [" + i + "]");
            return null;
        }
    }

    public boolean disconnect() {
        // responsible for closing the sockets
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
 