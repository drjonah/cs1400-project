
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
        }

        // server
        else if (input.equals("c")) {
            String content;

            System.out.print("name: ");
            String name = scnr.nextLine();

            Client client = new Client(socketAddress, socketPort, name);
            client.connect();

            while (true) {
                content = scnr.nextLine();

                if (content.equals("quit")) {
                    if (client.disconnect())
                        break;
                }

                client.send(content);
            }
        }

        scnr.close();
    }
}
