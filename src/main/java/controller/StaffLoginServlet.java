package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import model.userType;
import model.dao.DAO;
import model.dao.DBManager;

import java.io.IOException;
import java.sql.SQLException;

// Map this servlet to the URL pattern /staffLogin
@WebServlet("/staffLogin")
public class StaffLoginServlet extends HttpServlet {

    // Handles POST requests when staff users try to log in
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get email and password from the login form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Set up database access
            DAO dao = new DAO();
            DBManager userDB = dao.getUserManager();

            // Validate user credentials against database
            User user = userDB.validateStaffUser(email, password);

            // If user is found and user type is Staff
            if (user != null && user.getUserType() == userType.Staff) {
                HttpSession session = request.getSession(); // Start session
                session.setAttribute("staffUser", user);     // Save user object
                session.setAttribute("userEmail", user.getEmail()); // Save user email
                response.sendRedirect("index.jsp"); // Redirect to homepage
            } else {
                // If login fails, set error and return to login page
                request.setAttribute("error", "Invalid credentials or not a staff user.");
                request.getRequestDispatcher("staffLogin.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            // Handle SQL/database errors
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("staffLogin.jsp").forward(request, response);
        }
    }
}
