import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class App 
{
    static Connection con;

    public static Connection createC() {
        try {
            /*Class.forName("com.mysql.cj.jdbc.Driver");

            String user = "root";
            String password = "Jlsql!";
            String url = "jdbc:mysql://localhost:3306/tictactoe";
            
            con = DriverManager.getConnection(url, user, password);
            */

            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tictactoe","root","Jlsql!");
            return con;
            // Statement stmt = con.createStatement();
            // ResultSet rs = stmt.executeQuery("select Name from country where Population<5000;");
            // while(rs.next())
            // {
            //     System.out.println(rs.getString(1));
            // }
            // con.close();
    }
   
        catch(Exception e)
        {
            e.printStackTrace();
            return con;
        }
    }

}
