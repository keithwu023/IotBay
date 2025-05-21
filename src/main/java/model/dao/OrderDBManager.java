package model.dao;

import model.Order;
import model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDBManager {
    private final Connection conn;

    public OrderDBManager(Connection conn) {
        this.conn = conn;
    }

    public void createOrder(Order order, List<OrderItem> orderItems) throws SQLException {
        String insertOrder = "INSERT INTO Orders (UserEmail, SessionId, Status) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, order.getUserEmail());
        stmt.setString(2, order.getSessionId());
        stmt.setString(3, order.getStatus());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        int orderId;
        if (rs.next()) {
            orderId = rs.getInt(1);
            order.setOrderId(orderId);
        } else {
            throw new SQLException("Failed to retrieve order ID.");
        }
        rs.close();
        stmt.close();

        String insertItem = "INSERT INTO OrderItems (OrderId, DeviceId, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
        for (OrderItem item : orderItems) {
            PreparedStatement itemStmt = conn.prepareStatement(insertItem);
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, item.getDeviceId());
            itemStmt.setInt(3, item.getQuantity());
            itemStmt.setDouble(4, item.getUnitPrice());
            itemStmt.executeUpdate();
            itemStmt.close();
        }
    }

    public Order getOrderById(int orderId) throws SQLException {
        String query = "SELECT * FROM Orders WHERE OrderId = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Order order = new Order();
            order.setOrderId(rs.getInt("OrderId"));
            order.setUserEmail(rs.getString("UserEmail"));
            order.setSessionId(rs.getString("SessionId"));
            order.setOrderDate(new Date(rs.getTimestamp("OrderDate").getTime()));
            order.setStatus(rs.getString("Status"));
            rs.close();
            stmt.close();
            return order;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public List<Order> getOrdersByUser(String userEmail, String sessionId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE UserEmail = ? OR (UserEmail IS NULL AND SessionId = ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, userEmail != null ? userEmail : "");
        stmt.setString(2, sessionId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Order order = new Order();
            order.setOrderId(rs.getInt("OrderId"));
            order.setUserEmail(rs.getString("UserEmail"));
            order.setSessionId(rs.getString("SessionId"));
            order.setOrderDate(new Date(rs.getTimestamp("OrderDate").getTime()));
            order.setStatus(rs.getString("Status"));
            orders.add(order);
        }
        rs.close();
        stmt.close();
        return orders;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM OrderItems WHERE OrderId = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            OrderItem item = new OrderItem();
            item.setOrderItemId(rs.getInt("OrderItemId"));
            item.setOrderId(rs.getInt("OrderId"));
            item.setDeviceId(rs.getInt("DeviceId"));
            item.setQuantity(rs.getInt("Quantity"));
            item.setUnitPrice(rs.getDouble("UnitPrice"));
            items.add(item);
        }
        rs.close();
        stmt.close();
        return items;
    }

    public void updateOrderItems(int orderId, List<OrderItem> updatedItems) throws SQLException {
        String deleteItems = "DELETE FROM OrderItems WHERE OrderId = ?";
        PreparedStatement deleteStmt = conn.prepareStatement(deleteItems);
        deleteStmt.setInt(1, orderId);
        deleteStmt.executeUpdate();
        deleteStmt.close();

        String insertItem = "INSERT INTO OrderItems (OrderId, DeviceId, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
        for (OrderItem item : updatedItems) {
            PreparedStatement itemStmt = conn.prepareStatement(insertItem);
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, item.getDeviceId());
            itemStmt.setInt(3, item.getQuantity());
            itemStmt.setDouble(4, item.getUnitPrice());
            itemStmt.executeUpdate();
            itemStmt.close();
        }
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String update = "UPDATE Orders SET Status = ? WHERE OrderId = ?";
        PreparedStatement stmt = conn.prepareStatement(update);
        stmt.setString(1, status);
        stmt.setInt(2, orderId);
        stmt.executeUpdate();
        stmt.close();
    }

    public void deleteOrder(int orderId) throws SQLException {
        String deleteItems = "DELETE FROM OrderItems WHERE OrderId = ?";
        PreparedStatement itemStmt = conn.prepareStatement(deleteItems);
        itemStmt.setInt(1, orderId);
        itemStmt.executeUpdate();
        itemStmt.close();

        String deleteOrder = "DELETE FROM Orders WHERE OrderId = ?";
        PreparedStatement orderStmt = conn.prepareStatement(deleteOrder);
        orderStmt.setInt(1, orderId);
        orderStmt.executeUpdate();
        orderStmt.close();
    }
}