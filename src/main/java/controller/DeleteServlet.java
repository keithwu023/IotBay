package controller;

import model.User;
import model.dao.UserDBManager;
import model.dao.DAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteUser")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        DAO dao = (DAO) session.getAttribute("db");
        if (dao == null) {
            throw new ServletException("Database connection not found in session.");
        }

        try {
            UserDBManager userManager = new UserDBManager(dao.getConnection());

            // Delete user by ID
            userManager.deleteUser(user.getId());

            // Invalidate session after deletion
            session.invalidate();

            // Redirect to homepage or goodbye page
            resp.sendRedirect("index.jsp");

        } catch (SQLException e) {
            throw new ServletException("Database error while deleting user", e);
        }
    }
}
