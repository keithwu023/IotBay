package model.dao;

import model.User;
import model.userType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
    private final Connection connection;

    // Constructor to receive an active database connection
    public DBManager(Connection connection) {
        this.connection = connection;
    }

    // Method to validate a staff user from the STAFF table
    public User validateStaffUser(String email, String password) throws SQLException {
        String sql = "SELECT StaffId, StaffName, Email, Password, Position FROM STAFF WHERE Email = ? AND Password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);     // Set email in SQL query
            stmt.setString(2, password);  // Set password in SQL query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // If user is found, populate User object
                    User user = new User();
                    user.setId(rs.getInt("StaffId"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setName(rs.getString("StaffName"));

                    user.setUserType(userType.Staff); // Set user type to Staff

                    return user;
                } else {
                    return null; // No matching record found
                }
            }
        }
    }

    // General method to validate a user from the USERS table
    public User validateUser(String email, String password) throws SQLException {
        String sql = "SELECT UserId, Email, Password, Role FROM Users WHERE Email = ? AND Password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);     // Set email in SQL query
            stmt.setString(2, password);  // Set password in SQL query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // If user is found, populate User object
                    User user = new User();
                    user.setId(rs.getInt("UserId"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));

                    // Determine user role and set userType accordingly
                    String role = rs.getString("Role");
                    if ("Staff".equalsIgnoreCase(role)) {
                        user.setUserType(userType.Staff);
                    } else if ("Customer".equalsIgnoreCase(role)) {
                        user.setUserType(userType.Customer);
                    } else {
                        user.setUserType(userType.Unknown);
                    }

                    return user;
                } else {
                    return null; // No match found
                }
            }
        }
    }
}
