import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            // Get a connection to the database
            Connection connection = DatabaseConnection.getConnection();

            // Do something with the connection (e.g., query or update data)
            String sql = "INSERT INTO Customers (username, full_name, email) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set parameters
                preparedStatement.setString(1, "manager");
                preparedStatement.setString(2, "Manager");
                preparedStatement.setString(3, "manager@bu.edu");
                System.out.println("hi");
                // Execute the update
                preparedStatement.executeUpdate();
            }
            System.out.println("bye");

            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
