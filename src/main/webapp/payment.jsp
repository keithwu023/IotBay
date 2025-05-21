<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="model.dao.DBConnector" %>
<%@ page import="model.User" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Methods - IoT Bay</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo"><i class="fas fa-bolt"></i> IoT Bay</a>
            <div class="nav-links">
                <a href="index.jsp">Home</a>
                <a href="createOrder.jsp">Create Order</a>
                <% if (session.getAttribute("user") != null) { %>
                <a href="payment.jsp">payment</a>
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
        <h2>My Payment Methods</h2>

        <%-- Search form --%>
        <form method="get" action="payment.jsp" style="margin-bottom: 30px; display: flex; gap: 15px; align-items: center; flex-wrap: wrap;">
            <label>
                Last 4 Digits:
                <input type="text" name="lastFour" value="<%= request.getParameter("lastFour") != null ? request.getParameter("lastFour") : "" %>" class="input-group-input" style="padding: 10px; font-size: 16px; border-radius: 5px; border: 1px solid #ccc; width: 220px;">
            </label>
            <button type="submit" class="btn-primary" style="padding: 12px 25px; font-size: 16px; font-weight: 600;">Search</button>
        </form>

        <%-- Fetch payment methods --%>
        <%
            User user = (User) session.getAttribute("user");
            String userEmail = null;
            if (user != null) {
                userEmail = user.getEmail();
            }
            if (userEmail == null) {
                request.setAttribute("error", "Please log in to view payment methods.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            DBConnector db = new DBConnector();
            Connection conn = db.getConnection();
            String query = "SELECT * FROM payment_methods WHERE userEmail = ? AND (card_number LIKE ? OR ? IS NULL)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userEmail);
            String lastFour = request.getParameter("lastFour") != null ? "%" + request.getParameter("lastFour") : "%";
            stmt.setString(2, lastFour);
            stmt.setString(3, request.getParameter("lastFour"));
            ResultSet rs = stmt.executeQuery();
        %>

        <table style="width: 100%; border-collapse: collapse; box-shadow: 0 3px 8px rgba(0,0,0,0.1);">
            <thead style="background: var(--primary); color: white; font-weight: 600;">
            <tr>
                <th style="padding: 12px 15px;">Cardholder</th>
                <th style="padding: 12px 15px;">Card Number</th>
                <th style="padding: 12px 15px;">Expiry</th>
            </tr>
            </thead>
            <tbody>
            <% while (rs.next()) {
                String masked = "**** **** **** " + rs.getString("card_number").substring(rs.getString("card_number").length() - 4);
            %>
            <tr style="border-bottom: 1px solid #ddd;">
                <td style="padding: 12px 15px;"><%= rs.getString("cardholder_name") %></td>
                <td style="padding: 12px 15px;"><%= masked %></td>
                <td style="padding: 12px 15px;"><%= rs.getString("expiry_date") %></td>
                <td style="padding: 12px 15px; text-align: center;">
                    <a href="deletePayment?payment_id=<%= rs.getInt("payment_id") %>" class="btn-primary"
                       style="padding: 6px 12px; background-color: #f44336; font-size: 14px; border-radius: 5px;"
                       onclick="return confirm('Confirm deletion?')">Delete</a>
                </td>
            </tr>
            <% }
                rs.close(); stmt.close(); db.closeConnection();
            %>
            </tbody>
        </table>

        <div class="form-footer" style="margin-top: 20px;">
            <p><a href="addPayment.jsp">Add New Payment Method</a></p>
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