package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Order;
import model.dao.DAO;
import model.dao.OrderDBManager;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteOrderServlet")
public class DeleteOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");
        String sessionId = (String) session.getAttribute("sessionId");

        try {
            int orderId = Integer.parseInt(orderIdStr);
            DAO dao = new DAO();
            OrderDBManager orderManager = dao.getOrderManager();
            Order order = orderManager.getOrderById(orderId);

            if (order == null || (userEmail == null && !sessionId.equals(order.getSessionId())) || (userEmail != null && !userEmail.equals(order.getUserEmail()) && !sessionId.equals(order.getSessionId()))) {
                request.setAttribute("error", "Order not found or you do not have permission to delete it.");
                request.getRequestDispatcher("orderlist.jsp").forward(request, response);
                dao.close();
                return;
            }

            if (!"Saved".equals(order.getStatus())) {
                request.setAttribute("error", "Only saved orders can be deleted.");
                request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderId).forward(request, response);
                dao.close();
                return;
            }

            orderManager.deleteOrder(orderId);
            request.setAttribute("success", "Order deleted successfully!");
            request.getRequestDispatcher("orderlist.jsp").forward(request, response);
            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("orderlist.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid order ID.");
            request.getRequestDispatcher("orderlist.jsp").forward(request, response);
        }
    }
}