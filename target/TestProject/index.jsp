<%@ page import="model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index - IoT Bay</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo"><i class="fas fa-bolt"></i> IoT Bay</a>
            <div class="nav-links">
                <% if (session.getAttribute("user") == null) { %>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
                <a href="createOrder.jsp">Create Order</a>
                <a href="orderlist.jsp">Order History</a>
                <% } else { %>
                <a href="createOrder.jsp">Create Order</a>
                <a href="orderlist.jsp">Order History</a>
                <a href="deviceList.jsp">Device List</a>
                <a href="access_logs.jsp">Access Logs</a>
                <a href="profile.jsp">Profile</a>
                <a href="logout">Logout</a>
                <% } %>
            </div>
        </nav>
    </div>
</header>

<main class="container">
    <section class="hero">
        <% if (session.getAttribute("userEmail") != null) { %>
        <h1>Welcome to IoT Bay, <%= session.getAttribute("userEmail") %></h1>

        <%
            // Check if userType is stored in session
            Object userObj = session.getAttribute("staffUser");
            if (userObj != null) {
                // This means user logged in as Staff
        %>
        <p>You are logged in as <strong>Staff</strong>.</p>
        <% } else { %>
        <p>You are logged in as <strong>User</strong>.</p>
        <% } %>

        <% } else { %>
        <h1>Welcome to IoT Bay</h1>
        <p>Your premier destination for IoT devices and smart technology solutions.</p>
        <% } %>
    </section>
</main>
<div class="login">
    <p>Currently logged-in user:
        <%
            if (session.getAttribute("user") == null) {
        %>
        No one
        <%
        } else {
        %>
        <%= ((User) session.getAttribute("user")).getEmail() %>
        <%
            }
        %>
    <p>You are logged in as:
        <%
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
        %>
        Guest
        <%
        } else {
        %>
        <%= currentUser.getUserType() %>
        <%
            }
        %>
    </p>
    </p>
    </div>


<footer>
    <div class="container">
        <p>&copy; 2025 IoT Bay. All rights reserved.</p>
    </div>
</footer>
</body>
</html>
