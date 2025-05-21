package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.User;
import model.dao.DBConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/deletePayment")
public class DeletePaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        deletePayment(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        deletePayment(request, response);
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String paymentIdStr = request.getParameter("payment_id");

        if (paymentIdStr == null || paymentIdStr.isEmpty()) {
            request.setAttribute("error", "Payment ID is missing.");
            request.getRequestDispatcher("payment.jsp").forward(request, response);
            return;
        }

        try {
            int paymentId = Integer.parseInt(paymentIdStr);
            int userId = user.getId();

            DBConnector db = new DBConnector();
            Connection conn = db.getConnection();

            String sql = "DELETE FROM payment_methods WHERE payment_id = ? AND user_ids = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, paymentId);
            stmt.setInt(2, userId);

            int result = stmt.executeUpdate();

            stmt.close();
            db.closeConnection();

            if (result > 0) {
                response.sendRedirect("payment.jsp");
            } else {
                request.setAttribute("error", "Payment method not found or not authorized.");
                request.getRequestDispatcher("payment.jsp").forward(request, response);
            }

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error while deleting payment method.");
            request.getRequestDispatcher("payment.jsp").forward(request, response);
        }
    }
}
