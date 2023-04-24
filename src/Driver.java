
import java.util.Scanner;

import connection.Client;
import connection.Server;

/*
 * THIS FILE IS JUST TO TES THE CLIENT AND SERVER
 */

public class Driver {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        String socketAddress = "127.0.0.1";
        int socketPort = 6666;

        System.out.println("*** Welcome to connection driver ***");

        System.out.print("s(erver) / c(lient): ");
        String input = scnr.nextLine();

        // client
        if (input.equals("s")) {
            Server server = new Server(socketPort);
            server.connect();

            if (server.disconnect())
                System.out.println("Server closed...");
            else
                System.out.println("Served closing error..."); 
        }

        // server
        else if (input.equals("c")) {
            String content, serverResponse;

            Client client = new Client(socketAddress, socketPort);

            if (client.getConnectionsTotal() <= 2) {
                client.connect();

                while (true) {
                    System.out.print("> ");
                    content = scnr.nextLine();

                    serverResponse = client.send(content);
                    if (serverResponse == null) {
                        System.out.println("Goodbye...");

                        if (client.disconnect())
                            break;
                    }

                    System.out.println("server says: " + serverResponse);

                }
            }
            else
                System.out.println("Too many connections...");
        }

        scnr.close();
    }
}
