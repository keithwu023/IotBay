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
                request.setAttribute("error", "Order not found or you do not have permission to update it.");
                request.getRequestDispatcher("orderlist.jsp").forward(request, response);
                dao.close();
                return;
            }

            if (!"Saved".equals(order.getStatus()) && "update".equals(action)) {
                request.setAttribute("error", "Only saved orders can be updated.");
                request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderId).forward(request, response);
                dao.close();
                return;
            }

            if ("submit".equals(action)) {
                orderManager.updateOrderStatus(orderId, "Submitted");
                request.setAttribute("success", "Order submitted successfully!");
                request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderId).forward(request, response);
            } else if ("update".equals(action)) {
                List<OrderItem> updatedItems = new ArrayList<>();
                for (String param : request.getParameterMap().keySet()) {
                    if (param.startsWith("quantity_")) {
                        String deviceIdStr = param.substring(9);
                        int deviceId = Integer.parseInt(deviceIdStr);
                        int quantity = Integer.parseInt(request.getParameter(param));
                        if (quantity > 0) {
                            Device device = dao.getDeviceManager().getDeviceById(deviceId);
                            if (device != null) {
                                OrderItem item = new OrderItem();
                                item.setOrderId(orderId);
                                item.setDeviceId(deviceId);
                                item.setQuantity(quantity);
                                item.setUnitPrice(device.getUnitPrice());
                                updatedItems.add(item);
                            }
                        }
                    }
                }

                if (!updatedItems.isEmpty()) {
                    orderManager.updateOrderItems(orderId, updatedItems);
                    request.setAttribute("success", "Order updated successfully!");
                } else {
                    request.setAttribute("error", "No valid items selected for the order.");
                }
                request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderId).forward(request, response);
            }

            dao.close();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderIdStr).forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid input.");
            request.getRequestDispatcher("orderDetails.jsp?orderId=" + orderIdStr).forward(request, response);
        }
    }
}