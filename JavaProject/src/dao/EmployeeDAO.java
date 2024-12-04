package dao;

import java.math.BigDecimal;
import java.sql.*;

import config.DatabaseConfig;
import models.User;

public class EmployeeDAO {
    public boolean addEmployee(User user) {
        String query = """
                INSERT INTO "Employee" (first_name, last_name, salary, phone, position, email, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "Default First Name"); // Default first name
            stmt.setString(2, "Default Last Name"); // Default last name
            stmt.setBigDecimal(3, new BigDecimal("50000.00")); // Default salary (e.g., $50,000 annually)
            stmt.setString(4, "000-000-0000"); // Default phone number
            stmt.setString(5, "Employee"); // Default position
            stmt.setString(6, user.getEmail()); // Email from the User object
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis())); // Current timestamp for created_at
            stmt.setTimestamp(8, new Timestamp(System.currentTimeMillis())); // Current timestamp for updated_at

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
