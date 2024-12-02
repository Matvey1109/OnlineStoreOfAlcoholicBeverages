package dao;

import config.DatabaseConfig;
import models.Order;
import models.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public List<Order> getOrdersByClientId(int clientId) {
        String orderQuery = "SELECT * FROM \"Order\" WHERE client_id = ? ORDER BY created_at DESC;";
        String orderItemQuery = """
                    SELECT oi.id, oi.beverage_quantity, oi.beverage_price, oi.beverage_id, b.name AS beverage_name
                    FROM "OrderItem" oi
                    INNER JOIN "Beverage" b ON oi.beverage_id = b.id
                    WHERE oi.order_id = ?;
                """;

        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {

            orderStmt.setInt(1, clientId);

            try (ResultSet rs = orderStmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setPrice(rs.getBigDecimal("price"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    order.setUpdatedAt(rs.getTimestamp("updated_at"));
                    order.setClientId(rs.getInt("client_id"));

                    // Fetch order items
                    List<OrderItem> orderItems = new ArrayList<>();
                    try (PreparedStatement itemStmt = conn.prepareStatement(orderItemQuery)) {
                        itemStmt.setInt(1, order.getId());
                        try (ResultSet itemRs = itemStmt.executeQuery()) {
                            while (itemRs.next()) {
                                OrderItem item = new OrderItem();
                                item.setId(itemRs.getInt("id"));
                                item.setBeverageQuantity(itemRs.getInt("beverage_quantity"));
                                item.setBeveragePrice(itemRs.getBigDecimal("beverage_price"));
                                item.setBeverageId(itemRs.getInt("beverage_id"));
                                item.setBeverageName(itemRs.getString("beverage_name"));
                                orderItems.add(item);
                            }
                        }
                    }
                    order.setOrderItems(orderItems);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public boolean createOrderFromCart(int clientId) {
        String createOrderQuery = """
                    INSERT INTO "Order" (price, client_id)
                    SELECT SUM(ci.beverage_price * ci.beverage_quantity), c.client_id
                    FROM "CartItem" ci
                    JOIN "Cart" c ON ci.cart_id = c.id
                    WHERE c.client_id = ?
                    RETURNING id;
                """;

        String addOrderItemsQuery = """
                    INSERT INTO "OrderItem" (beverage_quantity, beverage_price, beverage_id, order_id)
                    SELECT ci.beverage_quantity, ci.beverage_price, ci.beverage_id, ?
                    FROM "CartItem" ci
                    JOIN "Cart" c ON ci.cart_id = c.id
                    WHERE c.client_id = ?;
                """;

        String clearCartQuery = """
                    DELETE FROM "CartItem"
                    USING "Cart"
                    WHERE "CartItem".cart_id = "Cart".id
                    AND "Cart".client_id = ?;
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement createOrderStmt = conn.prepareStatement(createOrderQuery);
                PreparedStatement addOrderItemsStmt = conn.prepareStatement(addOrderItemsQuery);
                PreparedStatement clearCartStmt = conn.prepareStatement(clearCartQuery)) {

            // Start transaction
            conn.setAutoCommit(false);

            // Step 1: Create the order
            createOrderStmt.setInt(1, clientId);
            int orderId;
            try (var rs = createOrderStmt.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                } else {
                    conn.rollback();
                    return false;
                }
            }

            // Step 2: Add items to the order
            addOrderItemsStmt.setInt(1, orderId);
            addOrderItemsStmt.setInt(2, clientId);
            addOrderItemsStmt.executeUpdate();

            // Step 3: Clear the cart
            clearCartStmt.setInt(1, clientId);
            clearCartStmt.executeUpdate();

            // Commit transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
