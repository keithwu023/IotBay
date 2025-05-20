package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.dao.DAO;
import model.dao.UserDBManager;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        req.getRequestDispatcher("profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        DAO dao = (DAO) session.getAttribute("db");

        if (dao == null) {
            throw new ServletException("Database connection not found in session.");
        }

        String fullName = req.getParameter("fullName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        try {
            UserDBManager userManager = new UserDBManager(dao.getConnection());

            User updatedUser = new User();
            updatedUser.setId(currentUser.getId());
            updatedUser.setUsername(fullName);
            updatedUser.setEmail(email);
            updatedUser.setPassword(password);
            updatedUser.setPhone(phone);
            updatedUser.setAddress(address);
            updatedUser.setUserType(currentUser.getUserType());
            updatedUser.setRegistrationDate(currentUser.getRegistrationDate());

            userManager.updateUser(updatedUser);

            session.setAttribute("user", updatedUser);
            req.setAttribute("message", "Profile detail updated successfully.");
            req.getRequestDispatcher("profile.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Error updating profile", e);
        }
    }
}

