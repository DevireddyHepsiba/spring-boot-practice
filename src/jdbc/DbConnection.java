package jdbc;

// Required imports for JDBC connectivity
import java.sql.Connection;        // For database connection handling
import java.sql.DriverManager;     // For managing database drivers
import java.sql.SQLException;      // For handling SQL exceptions

/**
 * Database Connection Utility Class
 * This class provides a centralized way to establish database connections
 */
public class DbConnection {
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/fleet_management";  // MySQL database URL
    private static final String USER = "root";                                         // Database username
    private static final String PASSWORD = "Hepsiba@2005";                            // Database password

    /**
     * Establishes and returns a connection to the MySQL database
     * 
     * @return Connection object if successful, null if connection fails
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Attempt to establish database connection using credentials
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.err.println("Connected to Database!");
        } catch (SQLException e) {
            // Print stack trace if connection fails
            e.printStackTrace();
        }
        return conn;
    }
}