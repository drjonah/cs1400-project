package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class RunDatabase 
{
    static Connection con;

    // this section establishes a connection between the code and the database
    public static Connection createC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //where the database is located
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe","root","Jaronisshort123!"); // information needed to host the database
            return con;
        }
        catch(Exception e) {
            e.printStackTrace(); // catches any exceptions
            return con;
        }
    }

}