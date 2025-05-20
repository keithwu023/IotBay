package model.dao;

import model.AccessLog;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessLogDBManager {
    private final Connection connection;

    public AccessLogDBManager(Connection connection) {
        this.connection = connection;
    }

    public void logLogin(int userId) throws SQLException {
        String sql = "INSERT INTO AccessLogs (UserId, LoginTime) VALUES (?, CURRENT_TIMESTAMP)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public void logLogout(int userId) throws SQLException {
        String sql = "UPDATE AccessLogs SET LogoutTime = CURRENT_TIMESTAMP WHERE UserId = ? AND LogoutTime IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    public List<AccessLog> getLogsByUser(int userId, String dateFilter) throws SQLException {
        List<AccessLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM AccessLogs WHERE UserId = ?";
        if (dateFilter != null && !dateFilter.isEmpty()) {
            sql += " AND DATE(LoginTime) = ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            if (dateFilter != null && !dateFilter.isEmpty()) {
                stmt.setString(2, dateFilter);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AccessLog log = new AccessLog();
                    log.setLogId(rs.getInt("LogId"));
                    log.setUserId(rs.getInt("UserId"));
                    log.setLoginTime(rs.getTimestamp("LoginTime"));
                    log.setLogoutTime(rs.getTimestamp("LogoutTime"));
                    logs.add(log);
                }
            }
        }

        return logs;
    }

    public List<AccessLog> getAllLogs() throws SQLException {
        List<AccessLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM AccessLogs ORDER BY LoginTime DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AccessLog log = new AccessLog();
                log.setLogId(rs.getInt("LogId"));
                log.setUserId(rs.getInt("UserId"));
                log.setLoginTime(rs.getTimestamp("LoginTime"));
                log.setLogoutTime(rs.getTimestamp("LogoutTime"));
                logs.add(log);
            }
        }
        return logs;
    }
}
