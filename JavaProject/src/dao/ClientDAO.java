package dao;

import config.DatabaseConfig;
import models.Client;
import models.User;

import java.sql.*;

public class ClientDAO {

    public Client getClientByUserEmail(String userEmail) {
        String query = """
                    SELECT c.*, u.id AS user_id, u.email, u.created_at AS user_created_at, u.updated_at AS user_updated_at
                    FROM "Client" c
                    INNER JOIN "User" u ON c.email = u.email
                    WHERE u.email = ?
                """;

        Client client = null;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setName(rs.getString("name"));
                    client.setEmail(rs.getString("email"));
                    client.setCreatedAt(rs.getTimestamp("created_at"));
                    client.setUpdatedAt(rs.getTimestamp("updated_at"));

                    // Map the User entity
                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setCreatedAt(rs.getTimestamp("user_created_at"));
                    user.setUpdatedAt(rs.getTimestamp("user_updated_at"));

                    client.setUser(user); // Assuming Client has a User field
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    public boolean addClient(User user) {
        String query = """
                INSERT INTO "Client" (name, email, created_at, updated_at) VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "Default Name"); // Set a default name for now
            stmt.setString(2, user.getEmail());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
