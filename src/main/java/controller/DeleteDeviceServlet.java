package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.dao.DAO;
import model.dao.DeviceDBManager;

import java.io.IOException;
import java.sql.SQLException;

// Map this servlet to the URL pattern /DeleteDeviceServlet
@WebServlet("/DeleteDeviceServlet")
public class DeleteDeviceServlet extends HttpServlet {

    // Handles GET requests (usually triggered by clicking a "delete" link)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id"); // Get device ID from request

        // If ID is missing or empty, redirect to device list page
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("deviceList.jsp");
            return;
        }

        int id = Integer.parseInt(idStr); // Convert ID string to integer

        try {
            DAO dao = new DAO(); // Create DAO to manage DB connection
            DeviceDBManager deviceManager = dao.getDeviceManager(); // Get device manager

            deviceManager.deleteDevice(id); // Call method to delete device by ID

            dao.close(); // Close DB connection

            // Redirect back to device list after successful deletion
            response.sendRedirect("deviceList.jsp");

        } catch (SQLException e) {
            e.printStackTrace(); // Log error
            // Set error message and forward back to list page
            request.setAttribute("error", "Error deleting device: " + e.getMessage());
            request.getRequestDispatcher("deviceList.jsp").forward(request, response);
        }
    }
}
