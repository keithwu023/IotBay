package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AccessLog;
import model.User;
import model.dao.AccessLogDBManager;
import model.dao.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/accesslogs")
public class AccessLogsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DAO dao = (DAO) session.getAttribute("db");
        User user = (User) session.getAttribute("user");

        String dateFilter = req.getParameter("date");

        try {
            AccessLogDBManager logManager = new AccessLogDBManager(dao.getConnection());
            List<AccessLog> logs = logManager.getLogsByUser(user.getId(), dateFilter);
            req.setAttribute("logs", logs);
            req.getRequestDispatcher("access_logs.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Unable to load access logs.");
            req.getRequestDispatcher("access_logs.jsp").forward(req, resp);
        }
    }
}
