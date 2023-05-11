package server;

public class Data {

    private String socketAddress;
    private int socketPort;

    public Data(int port) {
        socketPort = port;
    }
    
    public Data(String address, int port) {
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
