package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConfig;
import models.BeverageDiscount;

public class BeverageDiscountDAO {

    // Add a new beverage-discount mapping
    public void addBeverageDiscount(BeverageDiscount beverageDiscount) {
        String query = """
                        INSERT INTO "BeverageDiscount" (beverage_id, discount_id, created_at, updated_at)
                        VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, beverageDiscount.getBeverageId());
            stmt.setInt(2, beverageDiscount.getDiscountId());
            stmt.setTimestamp(3, beverageDiscount.getCreatedAt());
            stmt.setTimestamp(4, beverageDiscount.getUpdatedAt());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add beverage-discount mapping.");
        }
    }

    // Find all discount IDs associated with a given beverage ID
    public List<Integer> findDiscountIdsByBeverageId(int beverageId) {
        String query = """
                        SELECT discount_id FROM "BeverageDiscount" WHERE beverage_id = ?
                """;
        List<Integer> discountIds = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, beverageId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                discountIds.add(rs.getInt("discount_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to find discount IDs for beverage ID " + beverageId);
        }

        return discountIds;
    }

    // Check if a discount is orphaned (no associated beverages)
    public boolean isDiscountOrphaned(int discountId) {
        String query = """
                        SELECT COUNT(*) AS count FROM "BeverageDiscount" WHERE discount_id = ?
                """;
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, discountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("count") == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to check if discount is orphaned for discount ID " + discountId);
        }

        return false;
    }

    // Delete all entries by beverage ID
    public void deleteByBeverageId(int beverageId) {
        String query = """
                        DELETE FROM "BeverageDiscount" WHERE beverage_id = ?
                """;
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, beverageId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete beverage-discount mappings for beverage ID " + beverageId);
        }
    }
}
