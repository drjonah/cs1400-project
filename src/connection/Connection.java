package connection;

public class Connection {

    private String socketAddress;
    private int socketPort;

    public Connection(int port) {
        socketPort = port;
    }
    
    public Connection(String address, int port) {
        socketAddress = address;
        socketPort = port;
    }

    public String getSocketAddress() {
        return socketAddress;
    }

    public int getSocketPort() {
        return socketPort;
    }
}
