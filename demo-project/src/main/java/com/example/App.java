import java.sql.*;
import java.util.Scanner;

public class SecureApp {

    // Database credentials (avoid hardcoding in production)
    private static final String DB_PASSWORD = "admin123";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";

    public static void main(String[] args) {
        // Use try-with-resources to ensure proper resource management
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter username:");

            // Get user input safely
            String userInput = scanner.nextLine();

            // Use prepared statement to prevent SQL injection
            String query = "SELECT * FROM users WHERE username = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, userInput);  // Set the user input in the prepared statement
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    System.out.println("User found: " + rs.getString("username"));
                }

            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
