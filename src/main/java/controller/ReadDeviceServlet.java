package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

// Define servlet mapping for URL pattern "/ReadDeviceServlet"
@WebServlet("/ReadDeviceServlet")
public class ReadDeviceServlet extends HttpServlet {

    // Handles GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get search parameters from the request (default to empty string if null)
        String searchName = request.getParameter("name") != null ? request.getParameter("name") : "";
        String searchType = request.getParameter("type") != null ? request.getParameter("type") : "";

        // Set response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Display search form with current search values
        out.println("<form method='get'>");
        out.println("Name: <input type='text' name='name' value='" + searchName + "'>");
        out.println("Type: <input type='text' name='type' value='" + searchType + "'>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form>");

        // Start HTML table to display results
        out.println("<table border='1'><tr><th>Name</th><th>Type</th><th>Price</th><th>Qty</th><th>Actions</th></tr>");

        try (Connection conn = new model.dao.DBConnector().getConnection()) {
            // Prepare SQL query with LIKE filters for name and type
            String sql = "SELECT * FROM DEVICES WHERE DeviceName LIKE ? AND DeviceType LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + searchName + "%"); // Bind name parameter
            stmt.setString(2, "%" + searchType + "%"); // Bind type parameter

            ResultSet rs = stmt.executeQuery(); // Execute query

            // Iterate through result set and display each device as a row
            while (rs.next()) {
                int id = rs.getInt("DeviceId"); // Get device ID

                out.println("<tr>");
                out.println("<td>" + rs.getString("DeviceName") + "</td>");
                out.println("<td>" + rs.getString("DeviceType") + "</td>");
                out.println("<td>" + rs.getDouble("UnitPrice") + "</td>");
                out.println("<td>" + rs.getInt("Quantity") + "</td>");
                // Add links to edit and delete the device
                out.println("<td><a href='updateForm.jsp?id=" + id + "'>Edit</a> | <a href='DeleteDeviceServlet?id=" + id + "'>Delete</a></td>");
                out.println("</tr>");
            }

            // Close result set and statement
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Print error if SQL fails
        }

        out.println("</table>"); // Close the HTML table
    }
}
