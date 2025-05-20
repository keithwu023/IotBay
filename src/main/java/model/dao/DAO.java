package model.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    private Connection connection;
    private UserDBManager userManager;
    private DeviceDBManager deviceManager;
    private OrderDBManager orderManager;

    public DAO() throws SQLException {
        connection = new DBConnector().getConnection();
        userManager = new UserDBManager(connection);           // your existing DBManager for users
        deviceManager = new DeviceDBManager(connection);   // your DeviceDBManager for devices
        orderManager = new OrderDBManager(connection);
    }

    public Connection getConnection() {
        return connection;
    }

    public UserDBManager getUserManager() {
        return userManager;
    }

    public DeviceDBManager getDeviceManager() {
        return deviceManager;
    }

    public OrderDBManager getOrderManager() {
        return orderManager;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
