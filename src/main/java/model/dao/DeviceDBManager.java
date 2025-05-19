package model.dao;

import model.Device;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceDBManager {
    private final Connection connection;

    // Constructor receives an existing database connection
    public DeviceDBManager(Connection connection) {
        this.connection = connection;
    }

    // CREATE - Add a new device to the database
    public Device addDevice(Device device) throws SQLException {
        String sql = "INSERT INTO Devices (DeviceName, DeviceType, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters in the SQL statement
            stmt.setString(1, device.getDeviceName());
            stmt.setString(2, device.getDeviceType());
            stmt.setDouble(3, device.getUnitPrice());
            stmt.setInt(4, device.getQuantity());

            // Execute the insert
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating device failed, no rows affected.");
            }

            // Retrieve the generated primary key (DeviceId)
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    device.setDeviceId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating device failed, no ID obtained.");
                }
            }
        }
        return device;
    }

    // READ - Get a list of all devices
    public List<Device> getAllDevices() throws SQLException {
        List<Device> devices = new ArrayList<>();
        String sql = "SELECT * FROM Devices";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate over result set and build Device objects
            while (rs.next()) {
                Device d = new Device();
                d.setDeviceId(rs.getInt("DeviceId"));
                d.setDeviceName(rs.getString("DeviceName"));
                d.setDeviceType(rs.getString("DeviceType"));
                d.setUnitPrice(rs.getDouble("UnitPrice"));
                d.setQuantity(rs.getInt("Quantity"));
                devices.add(d);
            }
        }
        return devices;
    }

    // READ - Search devices using partial matches for name and type
    public List<Device> searchDevices(String name, String type) throws SQLException {
        List<Device> devices = new ArrayList<>();
        String sql = "SELECT * FROM Devices WHERE DeviceName LIKE ? AND DeviceType LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Use LIKE operator for partial matching
            stmt.setString(1, "%" + (name == null ? "" : name) + "%");
            stmt.setString(2, "%" + (type == null ? "" : type) + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Device d = new Device();
                    d.setDeviceId(rs.getInt("DeviceId"));
                    d.setDeviceName(rs.getString("DeviceName"));
                    d.setDeviceType(rs.getString("DeviceType"));
                    d.setUnitPrice(rs.getDouble("UnitPrice"));
                    d.setQuantity(rs.getInt("Quantity"));
                    devices.add(d);
                }
            }
        }
        return devices;
    }

    // UPDATE - Update an existing device’s details
    public void updateDevice(Device device) throws SQLException {
        String sql = "UPDATE Devices SET DeviceName = ?, DeviceType = ?, UnitPrice = ?, Quantity = ? WHERE DeviceId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, device.getDeviceName());
            stmt.setString(2, device.getDeviceType());
            stmt.setDouble(3, device.getUnitPrice());
            stmt.setInt(4, device.getQuantity());
            stmt.setInt(5, device.getDeviceId());

            stmt.executeUpdate();
        }
    }

    // DELETE - Delete a device by ID
    public void deleteDevice(int deviceId) throws SQLException {
        String sql = "DELETE FROM DEVICES WHERE DeviceId = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, deviceId);
        stmt.executeUpdate();
        stmt.close();
    }
}
