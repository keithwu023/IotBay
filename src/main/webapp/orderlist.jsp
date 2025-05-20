<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="model.dao.DBConnector" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order History - IoT Bay</title>
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
        <h2>Order History</h2>

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

        <%-- Search form --%>
        <form method="get" action="orderlist.jsp" style="margin-bottom: 30px; display: flex; gap: 15px; align-items: center; flex-wrap: wrap;">
            <label>
                Order Number:
                <input type="text" name="orderId" value="<%= request.getParameter("orderId") != null ? request.getParameter("orderId") : "" %>" class="input-group-input" style="padding: 10px; font-size: 16px; border-radius: 5px; border: 1px solid #ccc; width: 220px;">
            </label>
            <label>
                Date (YYYY-MM-DD):
                <input type="date" name="orderDate" value="<%= request.getParameter("orderDate") != null ? request.getParameter("orderDate") : "" %>" class="input-group-input" style="padding: 10px; font-size: 16px; border-radius: 5px; border: 1px solid #ccc; width: 220px;">
            </label>
            <button type="submit" class="btn-primary" style="padding: 12px 25px; font-size: 16px; font-weight: 600;">Search</button>
        </form>

        <%-- Fetch orders --%>
        <%
            String userEmail = (String) session.getAttribute("userEmail");
            String sessionId = (String) session.getAttribute("sessionId");
            if (sessionId == null) {
                sessionId = java.util.UUID.randomUUID().toString();
                session.setAttribute("sessionId", sessionId);
            }

            DBConnector db = new DBConnector();
            Connection conn = db.getConnection();
            String query = "SELECT * FROM Orders WHERE (UserEmail = ? OR UserEmail IS NULL AND SessionId = ?) AND (OrderId LIKE ? OR ? IS NULL) AND (OrderDate LIKE ? OR ? IS NULL)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userEmail != null ? userEmail : "");
            stmt.setString(2, sessionId);
            String searchId = request.getParameter("orderId") != null ? "%" + request.getParameter("orderId") + "%" : "%";
            String searchDate = request.getParameter("orderDate") != null ? "%" + request.getParameter("orderDate") + "%" : "%";
            stmt.setString(3, searchId);
            stmt.setString(4, request.getParameter("orderId"));
            stmt.setString(5, searchDate);
            stmt.setString(6, request.getParameter("orderDate"));
            ResultSet rs = stmt.executeQuery();
        %>

        <table style="width: 100%; border-collapse: collapse; box-shadow: 0 3px 8px rgba(0,0,0,0.1);">
            <thead style="background: var(--primary); color: white; font-weight: 600;">
            <tr>
                <th style="padding: 12px 15px;">Order ID</th>
                <th style="padding: 12px 15px;">Date</th>
                <th style="padding: 12px 15px;">Status</th>
                <th style="padding: 12px 15px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <% while (rs.next()) { %>
            <tr style="border-bottom: 1px solid #ddd;">
                <td style="padding: 12px 15px;"><%= rs.getInt("OrderId") %></td>
                <td style="padding: 12px 15px;"><%= rs.getString("OrderDate") %></td>
                <td style="padding: 12px 15px;"><%= rs.getString("Status") %></td>
                <td style="padding: 12px 15px; text-align: center;">
                    <a href="orderDetails.jsp?orderId=<%= rs.getInt("OrderId") %>" class="btn-primary" style="padding: 6px 12px; font-size: 14px; text-decoration: none; border-radius: 5px;">View</a>
                </td>
            </tr>
            <% } %>
            <% rs.close(); stmt.close(); db.closeConnection(); %>
            </tbody>
        </table>

        <div class="form-footer">
            <p><a href="createOrder.jsp">Create New Order</a></p>
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