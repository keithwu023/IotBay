package controller;

import model.User;
import model.dao.DBConnector;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;


import java.io.IOException;

@WebServlet("/addPayment")
public class AddPaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 获取用户信息
        int userId = user.getId(); // 假设存在 getId()
        String userEmail = user.getEmail(); // 新加入的 userEmail

        // 获取表单参数
        String cardholder = request.getParameter("cardholder");
        String cardNumber = request.getParameter("card_number");
        String cvv = request.getParameter("cvv");
        String expiryDate = request.getParameter("expiry_date");
        if (expiryDate.length() >= 7) {
            expiryDate = expiryDate.substring(0, 7); // 截取 YYYY-MM
        }


        if (cardholder == null || cardNumber == null || cvv == null || expiryDate == null ||
                cardholder.isEmpty() || cardNumber.isEmpty() || cvv.isEmpty() || expiryDate.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("addPayment.jsp").forward(request, response);
            return;
        }

        try {
            DBConnector db = new DBConnector();
            Connection conn = db.getConnection();

            // 先查询最大 payment_id
            String maxIdQuery = "SELECT MAX(payment_id) FROM payment_methods";
            PreparedStatement maxStmt = conn.prepareStatement(maxIdQuery);
            ResultSet rs = maxStmt.executeQuery();
            int newPaymentId = 1; // 默认从1开始
            if (rs.next()) {
                newPaymentId = rs.getInt(1) + 1;
            }
            rs.close();
            maxStmt.close();

            // 插入语句，使用 newPaymentId
            String sql = "INSERT INTO payment_methods (payment_id, user_ids, userEmail, cardholder_name, card_number, cvv, expiry_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, newPaymentId);
            stmt.setInt(2, userId);
            stmt.setString(3, userEmail);
            stmt.setString(4, cardholder);
            stmt.setString(5, cardNumber);
            stmt.setString(6, cvv);
            stmt.setString(7, expiryDate);

            stmt.executeUpdate();
            stmt.close();
            db.closeConnection();

            response.sendRedirect("payment.jsp");

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid date format.");
            request.getRequestDispatcher("addPayment.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to save payment method.");
            request.getRequestDispatcher("addPayment.jsp").forward(request, response);
        }
    }
}
