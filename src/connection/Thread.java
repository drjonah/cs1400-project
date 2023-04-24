package connection;

public class Thread {
    static int connections = 0;

    private String socketAddress;
    private int socketPort;

    public Thread(int port) {
        socketPort = port;
    }
    
    public Thread(String address, int port) {
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
