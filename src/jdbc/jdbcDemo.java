package jdbc;

// Import all SQL-related classes (Connection, Statement, ResultSet, etc.)
import java.sql.*;

/**
 * JDBC Demo Application
 * This class demonstrates basic CRUD operations using JDBC:
 * - Create: Creating a table and inserting records
 * - Read: Querying and displaying data
 * - Update: Modifying existing records
 * - Delete: Removing records
 */
public class jdbcDemo {
    public static void main(String[] args) {
        // Using try-with-resources to automatically close the connection
        try (Connection conn = DbConnection.getConnection()) {

            // Create a Statement object to execute SQL queries
            Statement stmt = conn.createStatement();

            System.err.println("\n=== Step 1: Creating Table ===");
            // DDL (Data Definition Language) - Create Table
            String create = "CREATE TABLE IF NOT EXISTS vehicle(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "vehicle_number VARCHAR(100)," +
                    "type VARCHAR(100)," +
                    "status VARCHAR(50))";
                stmt.execute(create);
                System.err.println("🚗 Table created!");
            
            System.err.println("\n=== Step 2: Inserting Records ===");
            // Insert multiple vehicles to better test our operations
            String[] vehicles = {
                "('TS09AA1001', 'Car', 'Active')",
                "('TS08BB2002', 'Truck', 'Active')",
                "('TS07CC3003', 'Bus', 'Active')"
            };
            
            for (String vehicle : vehicles) {
                stmt.executeUpdate("INSERT INTO vehicle(vehicle_number, type, status) VALUES" + vehicle);
                    System.err.println("✅ Inserted: " + vehicle);
            }

            System.err.println("\n=== Step 3: Reading Records ===");
            // First read - Show all inserted records
            displayAllRecords(stmt);

            System.err.println("\n=== Step 4: Updating Records ===");
            // Update the status of first two vehicles
            int updatedRows = stmt.executeUpdate(
                "UPDATE vehicle SET status='Inactive' WHERE id IN (1, 2)"
            );
                System.err.println("✅ Updated " + updatedRows + " vehicles to 'Inactive'");
            
            System.err.println("\n--- After Update ---");
            displayAllRecords(stmt);

            System.err.println("\n=== Step 5: Deleting Records ===");
            // Delete inactive vehicles
            int deletedRows = stmt.executeUpdate("DELETE FROM vehicle WHERE status='Inactive'");
                System.err.println("✅ Deleted " + deletedRows + " inactive vehicles");
            
            System.err.println("\n--- Final Records ---");
            displayAllRecords(stmt);

        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Helper method to display all records in the vehicle table
     */
    private static void displayAllRecords(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle");
            System.err.println("📋 Current Vehicle Records:");
        System.err.println("----------------------------------------");
        System.err.println("ID | Vehicle Number | Type | Status");
        System.err.println("----------------------------------------");
        
        boolean hasRecords = false;
        while (rs.next()) {
            hasRecords = true;
            System.err.printf("%2d | %-13s | %-4s | %s%n",
                rs.getInt("id"),
                rs.getString("vehicle_number"),
                rs.getString("type"),
                rs.getString("status"));
        }
        
        if (!hasRecords) {
            System.err.println("(No records found)");
        }
        System.err.println("----------------------------------------");
    }
}
