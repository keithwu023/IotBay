<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IoT Bay - Home</title>
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
                <% if (session.getAttribute("userEmail") == null) { %>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
                <a href="staffLogin.jsp">Staff Login</a>
                <% } else { %>
                <a href="deviceList.jsp">Device List</a>
                <a href="logout.jsp">Logout</a>
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

<footer>
    <div class="container">
        <p>&copy; 2023 IoT Bay. All rights reserved.</p>
    </div>
</footer>
</body>
</html>
