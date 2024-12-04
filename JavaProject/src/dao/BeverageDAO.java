package dao;

import models.Beverage;
import models.Category;
import utils.Logger;
import config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeverageDAO {

    // Get all beverages from the database
    public List<Beverage> getAllBeverages() {
        Logger.info("getAllBeverages()");

        List<Beverage> beverages = new ArrayList<>();
        String query = """
                    SELECT b.*, c.name AS category_name
                    FROM "Beverage" b
                    LEFT JOIN "Category" c ON b.category_id = c.id
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Map Beverage details
                Beverage beverage = new Beverage();
                beverage.setId(rs.getInt("id"));
                beverage.setName(rs.getString("name"));
                beverage.setDescription(rs.getString("description"));
                beverage.setPrice(rs.getBigDecimal("price"));
                beverage.setAvailableQuantity(rs.getInt("available_quantity"));
                beverage.setBrand(rs.getString("brand"));
                beverage.setAlcoholPercentage(rs.getBigDecimal("alcohol_percentage"));
                beverage.setCreatedAt(rs.getTimestamp("created_at"));
                beverage.setUpdatedAt(rs.getTimestamp("updated_at"));
                beverage.setCategoryId(rs.getInt("category_id"));

                // Map Category details
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));

                beverage.setCategory(category);
                beverages.add(beverage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beverages;
    }

    // Get beverage by category name
    public List<Beverage> getBeveragesByCategoryName(String categoryName) {
        Logger.info("getBeveragesByCategoryName() with categoryName: " + categoryName);

        List<Beverage> beverages = new ArrayList<>();
        String query = """
                    SELECT b.*, c.name AS category_name
                    FROM "Beverage" b
                    LEFT JOIN "Category" c ON b.category_id = c.id
                    WHERE c.name = ?
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, categoryName); // Set the category name parameter

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Map Beverage details
                    Beverage beverage = new Beverage();
                    beverage.setId(rs.getInt("id"));
                    beverage.setName(rs.getString("name"));
                    beverage.setDescription(rs.getString("description"));
                    beverage.setPrice(rs.getBigDecimal("price"));
                    beverage.setAvailableQuantity(rs.getInt("available_quantity"));
                    beverage.setBrand(rs.getString("brand"));
                    beverage.setAlcoholPercentage(rs.getBigDecimal("alcohol_percentage"));
                    beverage.setCreatedAt(rs.getTimestamp("created_at"));
                    beverage.setUpdatedAt(rs.getTimestamp("updated_at"));
                    beverage.setCategoryId(rs.getInt("category_id"));

                    // Map Category details
                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));

                    beverage.setCategory(category);
                    beverages.add(beverage);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beverages;
    }

    public void addBeverage(Beverage beverage) {
        String query = """
                        INSERT INTO "Beverage" (name, description, price, available_quantity, brand, alcohol_percentage, created_at, updated_at, category_id)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, beverage.getName());
            stmt.setString(2, beverage.getDescription());
            stmt.setBigDecimal(3, beverage.getPrice());
            stmt.setInt(4, beverage.getAvailableQuantity());
            stmt.setString(5, beverage.getBrand());
            stmt.setBigDecimal(6, beverage.getAlcoholPercentage());
            stmt.setTimestamp(7, beverage.getCreatedAt());
            stmt.setTimestamp(8, beverage.getUpdatedAt());
            stmt.setInt(9, beverage.getCategoryId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBeverage(Beverage beverage) {
        String query = """
                UPDATE "Beverage" SET
                    price = ?,
                    available_quantity = ?,
                    updated_at = ?
                WHERE id = ?
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBigDecimal(1, beverage.getPrice());
            stmt.setInt(2, beverage.getAvailableQuantity());
            stmt.setTimestamp(3, beverage.getUpdatedAt());
            stmt.setInt(4, beverage.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a beverage by ID
    public void deleteBeverage(int id) {
        String query = """
                        DELETE FROM "Beverage" WHERE id = ?
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
