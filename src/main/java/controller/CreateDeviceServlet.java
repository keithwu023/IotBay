package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class CreateDeviceServlet extends HttpServlet {

    // Handles POST requests for creating a new device
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get form data from the request
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("unit_price")); // Parse price as double
        int quantity = Integer.parseInt(request.getParameter("quantity")); // Parse quantity as int

        // Connect to database and insert new device
        try (Connection conn = new model.dao.DBConnector().getConnection()) {
            // SQL insert statement
            String sql = "INSERT INTO DEVICES (DeviceName, DeviceType, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);      // Bind device name
            stmt.setString(2, type);      // Bind device type
            stmt.setDouble(3, price);     // Bind unit price
            stmt.setInt(4, quantity);     // Bind quantity
            stmt.executeUpdate();         // Execute insert
        } catch (SQLException e) {
            e.printStackTrace(); // Log SQL error if any
        }

        // Redirect back to the device listing page after insertion
        response.sendRedirect("ReadDeviceServlet");
    }
}
