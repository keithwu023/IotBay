package model.dao;

import java.sql.*;

public class DBConnector {
    private Connection connection;

    // Constructor to initialize database connection
    public DBConnector() {
        System.setProperty("org.sqlite.lib.verbose", "true");

        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC driver loaded");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found", e);
        }

        // Connect to SQLite database
        String url = "jdbc:sqlite:/Users/SokkimhayUm/Documents/GitHub/IoTBay/AccessLog.db";
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
            System.out.println("Connected to: " + url);

        } catch (SQLException e) {
            System.err.println("Connection failed:");
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    // Get database connection
    public Connection getConnection() {
        return connection;
    }

    // Close database connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                System.err.println("Failed to close connection");
                e.printStackTrace();
            }
        }
    }
}
