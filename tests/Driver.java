/*
 * THIS FILE IS JUST TO TEST THE PLAYER
 */

public class Driver {
    public static void main(String[] args) {
        String socketAddress = "127.0.0.1";
        int socketPort = 7777;

        System.out.println("*** Welcome to Client Driver ***");

        Player client = new Player(socketAddress, socketPort);
        client.connect();
    }
}
