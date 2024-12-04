package dao;

import config.DatabaseConfig;
import models.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public List<CartItem> getCartItemsByUserEmail(String userEmail) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = """
                    SELECT ci.*, b.name AS beverage_name, b.price AS beverage_price
                    FROM "CartItem" ci
                    INNER JOIN "Cart" c ON ci.cart_id = c.id
                    INNER JOIN "Client" cl ON c.client_id = cl.id
                    INNER JOIN "User" u ON cl.email = u.email
                    INNER JOIN "Beverage" b ON ci.beverage_id = b.id
                    WHERE u.email = ?
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CartItem cartItem = new CartItem();
                    cartItem.setId(rs.getInt("id"));
                    cartItem.setBeverageQuantity(rs.getInt("beverage_quantity"));
                    cartItem.setBeveragePrice(rs.getBigDecimal("beverage_price"));
                    cartItem.setCreatedAt(rs.getTimestamp("created_at"));
                    cartItem.setUpdatedAt(rs.getTimestamp("updated_at"));

                    // Optionally, map beverage details if CartItem contains them
                    cartItem.setBeverageName(rs.getString("beverage_name"));

                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    public boolean addItemToCart(int clientId, int beverageId, int quantity) {
        Connection conn = null; // Declare the connection outside the try block

        try {
            conn = DatabaseConfig.getConnection(); // Initialize the connection
            conn.setAutoCommit(false);

            // Step 1: Check if the item already exists in the cart
            String checkQuery = """
                        SELECT id, beverage_quantity
                        FROM "CartItem"
                        WHERE cart_id = (SELECT id FROM "Cart" WHERE client_id = ?)
                          AND beverage_id = ?;
                    """;

            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, clientId);
                checkStmt.setInt(2, beverageId);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // Step 2: Update the quantity if the item exists
                        int currentQuantity = rs.getInt("beverage_quantity");
                        int newQuantity = currentQuantity + quantity;

                        String updateQuery = """
                                    UPDATE "CartItem"
                                    SET beverage_quantity = ?, updated_at = CURRENT_TIMESTAMP
                                    WHERE id = ?;
                                """;

                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, newQuantity);
                            updateStmt.setInt(2, rs.getInt("id"));
                            updateStmt.executeUpdate();
                        }
                    } else {
                        // Step 3: Insert a new row if the item does not exist
                        String insertQuery = """
                                    INSERT INTO "CartItem" (cart_id, beverage_id, beverage_quantity, beverage_price)
                                    VALUES (
                                        (SELECT id FROM "Cart" WHERE client_id = ?),
                                        ?,
                                        ?,
                                        (
                                            SELECT
                                                CASE
                                                    WHEN D.percent IS NOT NULL AND D.is_active THEN
                                                        B.price - (B.price * D.percent / 100.0)
                                                    ELSE B.price
                                                END AS final_price
                                            FROM "Beverage" B
                                            LEFT JOIN "BeverageDiscount" BD ON B.id = BD.beverage_id
                                            LEFT JOIN "Discount" D ON BD.discount_id = D.id
                                            WHERE B.id = ?
                                            LIMIT 1
                                        )
                                    );
                                """;

                        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, clientId);
                            insertStmt.setInt(2, beverageId);
                            insertStmt.setInt(3, quantity);
                            insertStmt.setInt(4, beverageId);
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }

            // Step 4: Update the available quantity in the Beverage table
            String updateBeverageQuery = """
                        UPDATE "Beverage"
                        SET available_quantity = available_quantity - ?
                        WHERE id = ? AND available_quantity >= ?;
                    """;

            try (PreparedStatement updateBeverageStmt = conn.prepareStatement(updateBeverageQuery)) {
                updateBeverageStmt.setInt(1, quantity);
                updateBeverageStmt.setInt(2, beverageId);
                updateBeverageStmt.setInt(3, quantity);

                int rowsUpdated = updateBeverageStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new SQLException("Not enough stock available for beverage ID: " + beverageId);
                }
            }

            // Commit transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}
