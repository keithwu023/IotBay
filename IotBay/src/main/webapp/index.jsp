<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User" %>

<%
    // Retrieve the logged-in user object from session
    String userEmail = (String) session.getAttribute("userEmail");
    String userName = (String) session.getAttribute("userName");
%>

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
                <a href="index.jsp">Home</a>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
                <a href="logout.jsp">Logout</a>
            </div>
        </nav>
    </div>
</header>

<main class="container">
    <section class="hero">
        <h1>Welcome to our Internet of Things Store - IoT Bay</h1>
        <p>Your premier destination for IoT devices and smart technology solutions.</p>
    </section>

    <div class="Image">
        <img src="images/online-store.png" alt="Online Store Image" class="online-store">
    </div>
</main>



<div>
    <h2>User Details:</h2>
    <%
        if (userEmail == null) {
    %>
    <p>No user logged in.</p>
    <%
    } else {
    %>
    <ul>
        <li>Username:<%= userName %></li>
        <li>Email:<%= userEmail %></li>
    </ul>
    <%
        }
    %>
</div>

<footer>
    <div class="container">
        <p>&copy; 2025 IoT Bay. All rights reserved.</p>
    </div>
</footer>
</body>
</html>