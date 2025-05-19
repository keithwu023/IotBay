package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Device;
import model.dao.DAO;
import model.dao.DeviceDBManager;

import java.io.IOException;
import java.sql.SQLException;

// Map this servlet to the URL pattern /UpdateDeviceServlet
@WebServlet("/UpdateDeviceServlet")
public class UpdateDeviceServlet extends HttpServlet {

    // Handles POST requests to update a device
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form parameters
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("unit_price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            // Set up database access
            DAO dao = new DAO();
            DeviceDBManager deviceManager = dao.getDeviceManager();

            // Create and populate a Device object with updated values
            Device device = new Device();
            device.setDeviceId(id);
            device.setDeviceName(name);
            device.setDeviceType(type);
            device.setUnitPrice(price);
            device.setQuantity(quantity);

            // Call the update method in the manager
            deviceManager.updateDevice(device);

            dao.close(); // Close DB connection

            // Redirect to the device list after update
            response.sendRedirect("deviceList.jsp");

        } catch (SQLException e) {
            e.printStackTrace(); // Log error
            // If update fails, show error message and reload the update form
            request.setAttribute("error", "Error updating device: " + e.getMessage());
            request.getRequestDispatcher("updateForm.jsp?id=" + id).forward(request, response);
        }
    }
}
