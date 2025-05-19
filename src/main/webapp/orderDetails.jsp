<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="model.dao.DBConnector" %>
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
        <a href="orderList.jsp">Order History</a>
        <a href="createOrder.jsp">Create Order</a>
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

    <%-- Fetch order details --%>
    <%
      String userEmail = (String) session.getAttribute("userEmail");
      String orderId = request.getParameter("orderId");
      if (userEmail == null || orderId == null) {
        response.sendRedirect("login.jsp");
        return;
      }
      DBConnector db = new DBConnector();
      Connection conn = db.getConnection();
      String orderQuery = "SELECT * FROM Orders WHERE OrderId = ? AND UserEmail = ?";
      PreparedStatement orderStmt = conn.prepareStatement(orderQuery);
      orderStmt.setInt(1, Integer.parseInt(orderId));
      orderStmt.setString(2, userEmail);
      ResultSet orderRs = orderStmt.executeQuery();
      if (!orderRs.next()) {
        response.sendRedirect("orderList.jsp");
        return;
      }
      String status = orderRs.getString("Status");
      String orderDate = orderRs.getString("OrderDate");
    %>

    <p><strong>Order ID:</strong> <%= orderId %></p>
    <p><strong>Date:</strong> <%= orderDate %></p>
    <p><strong>Status:</strong> <%= status %></p>

    <%-- Fetch order items --%>
    <%
      String itemsQuery = "SELECT oi.*, d.DeviceName, d.DeviceType FROM OrderItems oi JOIN Devices d ON oi.DeviceId = d.DeviceId WHERE oi.OrderId = ?";
      PreparedStatement itemsStmt = conn.prepareStatement(itemsQuery);
      itemsStmt.setInt(1, Integer.parseInt(orderId));
      ResultSet itemsRs = itemsStmt.executeQuery();
    %>

    <form action="UpdateOrderServlet" method="post">
      <input type="hidden" name="orderId" value="<%= orderId %>">
      <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px;">
        <thead style="background: var(--primary); color: white;">
        <tr>
          <th style="padding: 10px;">Device Name</th>
          <th style="padding: 10px;">Type</th>
          <th style="padding: 10px;">Price ($)</th>
          <th style="padding: 10px;">Quantity</th>
        </tr>
        </thead>
        <tbody>
        <% while (itemsRs.next()) { %>
        <tr style="border-bottom: 1px solid #ddd;">
          <td style="padding: 10px;"><%= itemsRs.getString("DeviceName") %></td>
          <td style="padding: 10px;"><%= itemsRs.getString("DeviceType") %></td>
          <td style="padding: 10px;"><%= String.format("%.2f", itemsRs.getDouble("UnitPrice")) %></td>
          <td style="padding: 10px;">
            <% if ("Saved".equals(status)) { %>
            <input type="number" name="quantity_<%= itemsRs.getInt("DeviceId") %>" min="0" value="<%= itemsRs.getInt("Quantity") %>"  %>" style="width: 60px; padding: 5px;">
            <% } else { %>
            <%= itemsRs.getInt("Quantity") %>
            <% } %>
          </td>
        </tr>
        <% } %>
        <% itemsRs.close(); itemsStmt.close(); orderRs.close(); orderStmt.close(); db.closeConnection(); %>
        </tbody>
      </table>

      <% if ("Saved".equals(status)) { %>
      <button type="submit" name="action" value="update" class="btn-primary">
        <i class="fas fa-edit"></i> Update Order
      </button>
      <button type="submit" name="action" value="submit" class="btn-primary" style="background: var(--success);">
        <i class="fas fa-check"></i> Submit Order
      </button>
      <a href="DeleteOrderServlet?orderId=<%= orderId %>" class="btn-primary" style="padding: 10px 20px; background: var(--accent); text-decoration: none; border-radius: 5px;">
        <i class="fas fa-trash"></i> Cancel Order
      </a>
      <% } %>
    </form>

    <div class="form-footer">
      <p><a href="orderList.jsp">Back to Order History</a></p>
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