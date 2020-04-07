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
    private static final String USER = "U04Khf";
    private static final String PASS = "53688264276";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static Connection conn;
    //private static Connection conn;

    public DbConnection() {}

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

    // Connect to Database
//    public static void connect() {
//        try {
//            Class.forName(DRIVER);
//            conn = DriverManager.getConnection(URL, USER, PASS);
//            System.out.println("Connected to MySQL Database");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Class Not Found " + e.getMessage());
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//            System.out.println("SQLState: " + e.getSQLState());
//            System.out.println("VendorError: " + e.getErrorCode());
//        }
//    }
//
//    // Close Database Connection
//    public static void disconnect() {
//        try {
//            conn.close();
//            System.out.println("Disconnected From MySQL Database");
//        } catch (SQLException e) {
//            System.out.println("SQLException: " + e.getMessage());
//        }
//    }
//
//    // Returns Database Connection
//    public static Connection getConnection() {
//        return conn;
//    }
}
