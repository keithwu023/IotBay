package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Order;
import model.OrderItem;
import model.dao.DAO;
import model.dao.OrderDBManager;
import model.Device;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UpdateOrderServlet")
public class UpdateOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String status = "update".equals(action) ? "Saved" : "Submitted";

        try {
            DAO dao = new DAO();
            OrderDBManager orderManager = dao.getOrderManager();

            Order order = orderManager.getOrderById(orderId);
            if (order == null || !"Saved".equals(order.getStatus())) {
                request.setAttribute("error", "Order not found or cannot be updated.");
                request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderId).forward(request, response);
                return;
            }

            order.setStatus(status);

            List<OrderItem> orderItems = new ArrayList<>();
            for (String param : request.getParameterMap().keySet()) {
                if (param.startsWith("quantity_")) {
                    String deviceIdStr = param.substring(9);
                    int deviceId = Integer.parseInt(deviceIdStr);
                    int quantity = Integer.parseInt(request.getParameter(param));
                    if (quantity > 0) {
                        Device device = dao.getDeviceManager().getDeviceById(deviceId);
                        if (device != null) {
                            OrderItem item = new OrderItem();
                            item.setDeviceId(deviceId);
                            item.setQuantity(quantity);
                            item.setUnitPrice(device.getUnitPrice());
                            orderItems.add(item);
                        }
                    }
                }
            }

            if (!orderItems.isEmpty()) {
                orderManager.updateOrder(order, orderItems);
                request.setAttribute("success", "Order " + (status.equals("Saved") ? "updated" : "submitted") + " successfully!");
                response.sendRedirect("orderlist.jsp");
            } else {
                request.setAttribute("error", "No devices selected for the order.");
                request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderId).forward(request, response);
            }

            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderId).forward(request, response);
        }
    }
}