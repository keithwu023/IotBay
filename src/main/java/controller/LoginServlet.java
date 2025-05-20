package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import model.dao.DAO;
import model.dao.AccessLogDBManager;
import model.dao.UserDBManager;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        DAO dao = (DAO) session.getAttribute("db");
        UserDBManager userManager = new UserDBManager(dao.getConnection());
        AccessLogDBManager accessLogManager = new AccessLogDBManager(dao.getConnection());

        User user = null;
        try {
            user = userManager.validateUser(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user != null) {
            session.setAttribute("user", user);
            try {
                accessLogManager.logLogin(user.getId());  // Log the login time
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resp.sendRedirect("index.jsp");
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }

    }
}
