
/*
 * THIS FILE IS JUST TO TEST THE SERVER
 */

import server.Server;

public class SeverDriver {
    public static void main(String[] args) {
        System.out.println("** Welcome to Server Driver ***");

        Server server = new Server(7777);
        server.connect();
    }
}
