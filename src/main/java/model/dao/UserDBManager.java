package model.dao;

import model.User;
import model.userType;

import java.sql.*;

public class UserDBManager {
    private final Connection connection;

    public UserDBManager(Connection connection) {
        this.connection = connection;
    }

    // CREATE: Register a new user
    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (Username, Email, Password, Phone, Address, RegistrationDate, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setTimestamp(6, user.getRegistrationDate());
            stmt.setString(7, user.getUserType().name());
            stmt.executeUpdate();
        }
    }

    // READ: Validate login by email and password
    public User validateUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }

    // READ: Find user by email
    public User findUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }

    // READ: Get user by ID
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE UserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractUserFromResultSet(rs);
                } else {
                    return null;
                }
            }
        }
    }

    // UPDATE: Update user profile
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET Username = ?, Email = ?, Password = ?, Phone = ?, Address = ?, Role = ? WHERE UserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getUserType().name());
            stmt.setInt(7, user.getId());

            stmt.executeUpdate();
        }
    }

    // DELETE: Delete user by ID (cancel registration)
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE UserId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    // UTILITY: Extract User from ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("UserId"));
        user.setUsername(rs.getString("Username"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setPhone(rs.getString("Phone"));
        user.setAddress(rs.getString("Address"));
        user.setRegistrationDate(rs.getTimestamp("RegistrationDate"));

        String role = rs.getString("Role");
        if ("Staff".equalsIgnoreCase(role)) {
            user.setUserType(userType.Staff);
        } else if ("Customer".equalsIgnoreCase(role)) {
            user.setUserType(userType.Customer);
        } else {
            user.setUserType(userType.Unknown); // Yes, spelling matches your enum
        }

        return user;
    }
}
