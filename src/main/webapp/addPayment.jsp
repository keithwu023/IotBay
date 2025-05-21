<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String userEmail = null;
    if (user != null) {
        userEmail = user.getEmail();
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Payment Method - IoT Bay</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo">IoT Bay</a>
            <div class="nav-links">
                <a href="index.jsp">Home</a>
                <a href="payment.jsp">My Payments</a>
                <a href="logout.jsp">Logout</a>
            </div>
        </nav>
    </div>
</header>

<div class="container" style="padding-top: 30px;">
    <div class="register-form">
        <h2>Add Payment Method</h2>

        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message"><%= request.getAttribute("error") %></div>
        <% } %>

        <form action="addPayment" method="post">
            <label for="cardholder">Cardholder Name:</label>
            <input type="text" name="cardholder" id="cardholder" required>

            <label for="card_number">Card Number:</label>
            <input type="text" name="card_number" id="card_number" maxlength="16" required>

            <label for="cvv">CVV:</label>
            <input type="text" name="cvv" id="cvv" maxlength="3" required>

            <label for="expiry_date">Expiry Date:</label>
            <input type="date" name="expiry_date" id="expiry_date" required>

            <button type="submit" class="btn-primary">Add Payment Method</button>
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
