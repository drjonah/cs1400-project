package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class App 
{
    static Connection con;

    public static Connection createC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe","root","Jaronisshort123!");
            return con;
        }
        catch(Exception e) {
            e.printStackTrace();
            return con;
        }
    }

}