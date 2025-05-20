package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import model.dao.AccessLogDBManager;
import model.dao.DAO;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("user");
            DAO dao = (DAO) session.getAttribute("db");

            if (user != null && dao != null) {
                try {
                    AccessLogDBManager logManager = new AccessLogDBManager(dao.getConnection());
                    logManager.logLogout(user.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            session.invalidate();
        }

        resp.sendRedirect("login.jsp");
    }
}
