<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="model.Order" %>
<%@ page import="model.OrderItem" %>
<%@ page import="model.Device" %>
<%@ page import="model.dao.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Details - IoT Bay</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo">
                <i class="fas fa-bolt"></i> IoT Bay
            </a>
            <div class="nav-links">
                <a href="index.jsp">Home</a>
                <a href="createOrder.jsp">Create Order</a>
                <a href="orderlist.jsp">Order History</a>
                <% if (session.getAttribute("userEmail") != null) { %>
                <a href="logout.jsp">Logout</a>
                <% } else { %>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
                <% } %>
            </div>
        </nav>
    </div>
</header>

<div class="container" style="padding-top: 30px;">
    <div class="register-form">
        <h2>Order Details</h2>

        <%
            String orderIdStr = request.getParameter("orderId");
            if (orderIdStr == null || orderIdStr.isEmpty()) {
                request.setAttribute("error", "No order ID provided.");
                request.getRequestDispatcher("orderlist.jsp").forward(request, response);
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(orderIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid order ID.");
                request.getRequestDispatcher("orderlist.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            String userEmail = (String) session.getAttribute("userEmail");
            String sessionId = (String) session.getAttribute("sessionId");
            if (sessionId == null) {
                sessionId = java.util.UUID.randomUUID().toString();
                session.setAttribute("sessionId", sessionId);
            }

            DAO dao = new DAO();
            OrderDBManager orderManager = dao.getOrderManager();
            Order order = orderManager.getOrderById(orderId);
            if (order == null || (userEmail == null && !sessionId.equals(order.getSessionId())) || (userEmail != null && !userEmail.equals(order.getUserEmail()) && !sessionId.equals(order.getSessionId()))) {
                request.setAttribute("error", "Order not found or you do not have permission to view it.");
                request.getRequestDispatcher("orderlist.jsp").forward(request, response);
                dao.close();
                return;
            }

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        %>

        <%-- Display success or error messages --%>
        <% if (request.getAttribute("success") != null) { %>
        <div class="success-message" style="color: green; font-weight: bold; margin-bottom: 20px;">
            <i class="fas fa-check-circle"></i> <%= request.getAttribute("success") %>
        </div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message" style="color: red; font-weight: bold; margin-bottom: 20px;">
            <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <%-- Order summary --%>
        <div style="margin-bottom: 30px;">
            <p><strong>Order ID:</strong> <%= order.getOrderId() %></p>
            <p><strong>Date:</strong> <%= sdf.format(order.getOrderDate()) %></p>
            <p><strong>Status:</strong> <%= order.getStatus() %></p>
        </div>

        <%-- Order items table --%>
        <form method="post" action="UpdateOrderServlet">
            <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
            <table style="width: 100%; border-collapse: collapse; box-shadow: 0 3px 8px rgba(0,0,0,0.1);">
                <thead style="background: var(--primary); color: white; font-weight: 600;">
                <tr>
                    <th style="padding: 12px 15px;">Device Name</th>
                    <th style="padding: 12px 15px;">Type</th>
                    <th style="padding: 12px 15px;">Price</th>
                    <th style="padding: 12px 15px;">Quantity</th>
                </tr>
                </thead>
                <tbody>
                <%
                    DeviceDBManager deviceManager = dao.getDeviceManager();
                    for (OrderItem item : orderManager.getOrderItemsByOrderId(orderId)) {
                        Device d = deviceManager.getDeviceById(item.getDeviceId());
                        if (d == null) continue;
                %>
                <tr style="border-bottom: 1px solid #ddd;">
                    <td style="padding: 12px 15px;"><%= d.getDeviceName() %></td>
                    <td style="padding: 12px 15px;"><%= d.getDeviceType() %></td>
                    <td style="padding: 12px 15px;"><%= String.format("%.2f", item.getUnitPrice()) %></td>
                    <td style="padding: 12px 15px;">
                        <% if ("Saved".equals(order.getStatus())) { %>
                        <input type="number" name="quantity_<%= d.getDeviceId() %>" value="<%= item.getQuantity() %>" style="width: 60px; padding: 5px;" min="0">
                        <% } else { %>
                        <%= item.getQuantity() %>
                        <% } %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>

            <%-- Action buttons for saved orders --%>
            <% if ("Saved".equals(order.getStatus())) { %>
            <div style="margin-top: 30px; display: flex; gap: 15px;">
                <button type="submit" name="action" value="update" class="btn-primary" style="padding: 12px 25px; font-size: 16px; font-weight: 600;">Update Order</button>
                <button type="submit" name="action" value="submit" class="btn-primary" style="padding: 12px 25px; font-size: 16px; font-weight: 600;">Submit Order</button>
                <a href="DeleteOrderServlet?orderId=<%= order.getOrderId() %>" class="btn-primary" style="padding: 12px 25px; font-size: 16px; font-weight: 600; text-decoration: none; border-radius: 5px;">Cancel Order</a>
            </div>
            <% } %>
        </form>

        <% dao.close(); %>

        <div class="form-footer">
            <p><a href="orderlist.jsp">Back to Order History</a></p>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <p>© 2025 IoT Bay. All rights reserved.</p>
    </div>
</footer>
</body>
</html>