
import Server.Server;

public class SeverDriver {
    public static void main(String[] args) {
        System.out.println("** Welcome to Server Driver ***");

        Server server = new Server(6666);
        server.connect();
    }
}
