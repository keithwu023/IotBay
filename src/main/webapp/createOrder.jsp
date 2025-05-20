<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="model.dao.DBConnector" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Order - IoT Bay</title>
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
        <h2>Create New Order</h2>

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

        <%-- Fetch available devices --%>
        <%
            DBConnector db = new DBConnector();
            Connection conn = db.getConnection();
            String query = "SELECT * FROM Devices WHERE Quantity > 0";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        %>

        <form action="CreateOrderServlet" method="post">
            <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px;">
                <thead style="background: var(--primary); color: white;">
                <tr>
                    <th style="padding: 10px;">Device Name</th>
                    <th style="padding: 10px;">Type</th>
                    <th style="padding: 10px;">Price ($)</th>
                    <th style="padding: 10px;">Available</th>
                    <th style="padding: 10px;">Quantity</th>
                </tr>
                </thead>
                <tbody>
                <% while (rs.next()) { %>
                <tr style="border-bottom: 1px solid #ddd;">
                    <td style="padding: 10px;"><%= rs.getString("DeviceName") %></td>
                    <td style="padding: 10px;"><%= rs.getString("DeviceType") %></td>
                    <td style="padding: 10px;"><%= String.format("%.2f", rs.getDouble("UnitPrice")) %></td>
                    <td style="padding: 10px;"><%= rs.getInt("Quantity") %></td>
                    <td style="padding: 10px;">
                        <input type="number" name="quantity_<%= rs.getInt("DeviceId") %>" min="0" max="<%= rs.getInt("Quantity") %>" value="0" style="width: 60px; padding: 5px;">
                    </td>
                </tr>
                <% } %>
                <% rs.close(); stmt.close(); db.closeConnection(); %>
                </tbody>
            </table>
            <button type="submit" name="action" value="save" class="btn-primary">
                <i class="fas fa-save"></i> Save Order
            </button>
            <button type="submit" name="action" value="submit" class="btn-primary" style="background: var(--success);">
                <i class="fas fa-check"></i> Submit Order
            </button>
        </form>

        <div class="form-footer">
            <p><a href="index.jsp">Back to Home</a></p>
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