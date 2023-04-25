package connection;

public class Connection {
    static int connections = 0;

    private String socketAddress;
    private int socketPort;

    public Connection(int port) {
        socketPort = port;
    }
    
    public Connection(String address, int port) {
        socketAddress = address;
        socketPort = port;

        connections++;
    }

    public int getConnectionsTotal() {
        return connections;
    }

    public String getSocketAddress() {
        return socketAddress;
    }

    public int getSocketPort() {
        return socketPort;
    }
}
