import java.sql.Connection;

import java.sql.PreparedStatement;

public class AppPlayers {

    public static boolean insertPlayerToDB(Player boardP) {
        boolean f = false;

        try {
            Connection con = App.createC();
            String q = "insert into board(clientTurn,clientID,clientNumber) values(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(q);
            pstmt.setInt(1, boardP.getClientTurn());
            pstmt.setString(2, boardP.getClientID());
            pstmt.setInt(3, boardP.getClientNumber());

            pstmt.executeUpdate();
            f=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

}