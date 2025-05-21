package controller;

import model.User;
import model.userType;
import model.dao.UserDBManager;
import model.dao.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String fullName = firstName + " " + lastName;
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String type = req.getParameter("userType");

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Passwords do not match.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;  // Stop processing further
        }

        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        DAO dao = (DAO) req.getSession().getAttribute("db");
        if (dao == null) {
            throw new ServletException("Database connection not found in session.");
        }

        try {
            UserDBManager userManager = dao.getUserManager();

            if (userManager.findUserByEmail(email) != null) {
                req.setAttribute("error", "Email is already registered.");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
            }

            User newUser = new User();
            newUser.setUsername(fullName);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setPhone(phone);
            newUser.setAddress(address);
            newUser.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
            newUser.setUserType(type.equals("Customer") ? userType.Customer : userType.Staff);

            userManager.registerUser(newUser);

            resp.sendRedirect("login.jsp");

        } catch (SQLException e) {
            throw new ServletException("Database error during registration", e);
        }
    }
}