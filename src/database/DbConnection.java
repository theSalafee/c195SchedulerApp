package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Naasir
 */

public class DbConnection {
    private static final String DBNAME = "U04KhF";
    private static final String URL = "jdbc:mysql://3.227.166.251/" + DBNAME;
    private static final String USER = "U04KhF";
    private static final String PASS = "53688264276";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static Connection conn;

    //public DbConnection() {}

    public static void openConnection() throws ClassNotFoundException, SQLException, Exception
    {
        Class.forName(DRIVER);
        conn = (Connection) DriverManager.getConnection(URL, USER, PASS);
        System.out.println("Connection Opened Successfully");
    }
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        conn.close();
        System.out.println("Connection Closed Successfully");
    }
}
