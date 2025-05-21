package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.dao.DAO;
import model.dao.OrderDBManager;
import model.Order;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteOrderServlet")
public class DeleteOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            DAO dao = new DAO();
            OrderDBManager orderManager = dao.getOrderManager();

            Order order = orderManager.getOrderById(orderId);
            if (order != null && "Saved".equals(order.getStatus())) {
                orderManager.deleteOrder(orderId);
                request.setAttribute("success", "Order deleted successfully!");
            } else {
                request.setAttribute("error", "Order not found or cannot be deleted.");
            }

            dao.close();
            response.sendRedirect("orderlist.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("orderlist.jsp").forward(request, response);
        }
    }
}