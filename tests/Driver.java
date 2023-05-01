/*
 * THIS FILE IS JUST TO TES THE CLIENT AND SERVER
 */

public class Driver {
    public static void main(String[] args) {
        String socketAddress = "127.0.0.1";
        int socketPort = 6666;

        System.out.println("*** Welcome to Client Driver ***");

        Player client = new Player(socketAddress, socketPort);
        client.connect();
    }
}
