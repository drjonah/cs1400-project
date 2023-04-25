
package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * THIS FILE IS THE PLAYER OR CLIENT
 */

public class Client extends Connection {

    private Socket clientSocket;
    private BufferedReader socketInput;
    private PrintWriter socketOutput;

    private String clientName;

    public Client(String address, int port, String name) {
        super(address, port);
        clientName = name;
    }

    public void connect() {

        // responsible for opening the socket
        try {
            clientSocket = new Socket(super.getSocketAddress(), super.getSocketPort());
            System.out.println("Connection to server successful...");
            System.out.println("\n------------------------\n");

            socketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // reading input
            socketOutput = new PrintWriter(clientSocket.getOutputStream(), true); // sending responses

            socketOutput.println(clientName);

            // Start a new thread to handle incoming messages from the server
            Thread receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String serverResponse = socketInput.readLine();

                            if (serverResponse == null) {
                                System.out.println("Error with server, shutting down...");
                                disconnect();
                                break;
                            }

                            System.out.println(serverResponse);
                        }
                        catch (IOException i) {
                            System.out.println("Error receiving message from server: " + i);
                            break;
                        }
                    }
                }
            });
            receiveThread.start();
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

    public void send(String content) {
        socketOutput.println(clientName);
        socketOutput.println(content);
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
 