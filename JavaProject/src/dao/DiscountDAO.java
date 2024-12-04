package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConfig;
import models.Discount;

public class DiscountDAO {

    // Add a new discount
    public int addDiscount(Discount discount) {
        String query = """
                        INSERT INTO "Discount" (name, description, percent, is_active, created_at)
                        VALUES (?, ?, ?, ?, ?)
                        RETURNING id
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, discount.getName());
            stmt.setString(2, discount.getDescription());
            stmt.setDouble(3, discount.getPercent());
            stmt.setBoolean(4, discount.getIsActive());
            stmt.setTimestamp(5, discount.getCreatedAt());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new SQLException("Failed to retrieve discount ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add discount.");
        }
    }

    // Update an existing discount
    public void updateDiscount(Discount discount) {
        String query = """
                        UPDATE "Discount"
                        SET percent = ?, is_active = ?
                        WHERE id = ?
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, discount.getPercent());
            stmt.setBoolean(2, discount.getIsActive());
            stmt.setInt(3, discount.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update discount.");
        }
    }

    // Get all discounts
    public List<Discount> getAllDiscounts() {
        String query = """
                        SELECT id, name, description, percent, is_active, created_at
                        FROM "Discount"
                """;

        List<Discount> discounts = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Discount discount = new Discount();
                discount.setId(rs.getInt("id"));
                discount.setName(rs.getString("name"));
                discount.setDescription(rs.getString("description"));
                discount.setPercent(rs.getDouble("percent"));
                discount.setIsActive(rs.getBoolean("is_active"));
                discount.setCreatedAt(rs.getTimestamp("created_at"));
                discounts.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch discounts.");
        }

        return discounts;
    }

    // Delete a discount by ID
    public void deleteDiscountById(int discountId) {
        String query = """
                        DELETE FROM "Discount" WHERE id = ?
                """;
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, discountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete discount with ID " + discountId);
        }
    }

}
