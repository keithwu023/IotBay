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

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String userEmail = (String) request.getSession().getAttribute("userEmail");
        String status = "save".equals(action) ? "Saved" : "Submitted";

        try {
            DAO dao = new DAO();
            OrderDBManager orderManager = dao.getOrderManager();

            Order order = new Order();
            order.setUserEmail(userEmail);
            order.setStatus(status);

            List<OrderItem> orderItems = new ArrayList<>();

            // Get device amount from parameter
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
                orderManager.createOrder(order, orderItems);
                request.setAttribute("success", "Order " + (status.equals("Saved") ? "saved" : "submitted") + " successfully!");
                response.sendRedirect("orderlist.jsp");
            } else {
                request.setAttribute("error", "No devices selected for the order.");
                request.getRequestDispatcher("createOrder.jsp").forward(request, response);
            }

            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("createOrder.jsp").forward(request, response);
        }
    }
}