package model.dao;

import model.Order;
import model.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDBManager {
    private final Connection connection;

    public OrderDBManager(Connection connection) {
        this.connection = connection;
    }

    // Create a new order plus item
    public void createOrder(Order order, List<OrderItem> orderItems) throws SQLException {
        connection.setAutoCommit(false); // Start transaction
        try {
            // Add order
            String orderSql = "INSERT INTO Orders (UserEmail, Status) VALUES (?, ?)";
            try (PreparedStatement orderStmt = connection.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setString(1, order.getUserEmail());
                orderStmt.setString(2, order.getStatus());
                orderStmt.executeUpdate();
                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        order.setOrderId(generatedKeys.getInt(1));
                    }
                }
            }

            // Add an item
            String itemSql = "INSERT INTO OrderItems (OrderId, DeviceId, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
            try (PreparedStatement itemStmt = connection.prepareStatement(itemSql)) {
                for (OrderItem item : orderItems) {
                    itemStmt.setInt(1, order.getOrderId());
                    itemStmt.setInt(2, item.getDeviceId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getUnitPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Get order by ID
    public Order getOrderById(int orderId) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE OrderId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("OrderId"));
                    order.setUserEmail(rs.getString("UserEmail"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setStatus(rs.getString("Status"));
                    return order;
                }
            }
        }
        return null;
    }

    // Get order items by order ID
    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM OrderItems WHERE OrderId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderItemId(rs.getInt("OrderItemId"));
                    item.setOrderId(rs.getInt("OrderId"));
                    item.setDeviceId(rs.getInt("DeviceId"));
                    item.setQuantity(rs.getInt("Quantity"));
                    item.setUnitPrice(rs.getDouble("UnitPrice"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    // Get orders by email
    public List<Order> getOrdersByUser(String userEmail) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE UserEmail = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("OrderId"));
                    order.setUserEmail(rs.getString("UserEmail"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setStatus(rs.getString("Status"));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    // Update order plus item
    public void updateOrder(Order order, List<OrderItem> orderItems) throws SQLException {
        connection.setAutoCommit(false);
        try {
            // Update order status
            String orderSql = "UPDATE Orders SET Status = ? WHERE OrderId = ?";
            try (PreparedStatement orderStmt = connection.prepareStatement(orderSql)) {
                orderStmt.setString(1, order.getStatus());
                orderStmt.setInt(2, order.getOrderId());
                orderStmt.executeUpdate();
            }

            // Remove an item
            String deleteItemsSql = "DELETE FROM OrderItems WHERE OrderId = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteItemsSql)) {
                deleteStmt.setInt(1, order.getOrderId());
                deleteStmt.executeUpdate();
            }

            // Add new order item
            String itemSql = "INSERT INTO OrderItems (OrderId, DeviceId, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
            try (PreparedStatement itemStmt = connection.prepareStatement(itemSql)) {
                for (OrderItem item : orderItems) {
                    itemStmt.setInt(1, order.getOrderId());
                    itemStmt.setInt(2, item.getDeviceId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getUnitPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // Remove order plus item
    public void deleteOrder(int orderId) throws SQLException {
        connection.setAutoCommit(false); // Start transaction
        try {
            // Remove order item
            String deleteItemsSql = "DELETE FROM OrderItems WHERE OrderId = ?";
            try (PreparedStatement deleteItemsStmt = connection.prepareStatement(deleteItemsSql)) {
                deleteItemsStmt.setInt(1, orderId);
                deleteItemsStmt.executeUpdate();
            }

            // Remove order
            String deleteOrderSql = "DELETE FROM Orders WHERE OrderId = ?";
            try (PreparedStatement deleteOrderStmt = connection.prepareStatement(deleteOrderSql)) {
                deleteOrderStmt.setInt(1, orderId);
                deleteOrderStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}