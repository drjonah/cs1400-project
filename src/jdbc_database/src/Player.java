public class Player {
    private int clientTurn;
    private String clientID;
    private int clientNumber;

    public int getClientTurn() {
        return this.clientTurn;
    }

    public void setClientTurn(int clientTurn) {
        this.clientTurn = clientTurn;
    }

    public String getClientID() {
        return this.clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public int getClientNumber() {
        return this.clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }
    
    
    public Player(int clientTurn, String clientID, int clientNumber) {
        super();
        this.clientTurn = clientTurn;
        this.clientID = clientID;
        this.clientNumber = clientNumber;
    }
    
    public Player(String clientID, int clientNumber) {
        super();
        this.clientID = clientID;
        this.clientNumber = clientNumber;
    }

    public Player() {
        super();
    }

    @Override
    public String toString() {
        return "Turn: " + clientTurn + " clientID: " + clientID + " clientNumber: " + clientNumber;
    }
}
