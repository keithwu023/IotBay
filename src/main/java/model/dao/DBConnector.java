package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            // If driver class is not found, throw a runtime error
            throw new RuntimeException("Failed to load SQLite JDBC driver", e);
        }

        // Path to the SQLite database file
        String url = "jdbc:sqlite:/Users/darbysidharta/Documents/GitHub/IotBay/IotBay.db";
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true); // Auto-commit each statement by default
            System.out.println("Connected to database");
        } catch (SQLException e) {
            // Print error and throw exception if connection fails
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    // Method to get the current database connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the connection when done
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed");
            } catch (SQLException e) {
                e.printStackTrace(); // Print any error during closing
            }
        }
    }
}
