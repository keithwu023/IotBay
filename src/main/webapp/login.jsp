<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - IoT Bay</title>
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
                <% if (session.getAttribute("loggedInUser") != null) { %>
                <a href="logout.jsp">Logout</a>
                <% } %>
                <img src="images/search-icon.png" alt="Search"/></a>
            </div>
        </nav>
    </div>
</header>

<%
    // Check for logout message
    String message = request.getParameter("message");
    if ("logout_success".equals(message)) {
        request.setAttribute("success", "You have been successfully logged out.");
    }

    // Create a simple in-memory user database (replace with real database in production)
    Map<String, String> userDatabase = new HashMap<>();

    // Add default user
    userDatabase.put("user@iotbay.com", "password123");

    // Get registered users from application scope
    if (application.getAttribute("userDatabase") != null) {
        userDatabase.putAll((Map<String, String>)application.getAttribute("userDatabase"));
    }
%>

<div class="login-container">
    <div class="login-form">
        <h2>Login to Your Account</h2>

        <%-- Display success message if logged out --%>
        <% if (request.getAttribute("success") != null) { %>
        <div class="success-message">
            <i class="fas fa-check-circle"></i> <%= request.getAttribute("success") %>
        </div>
        <% } %>

        <%-- User email display --%>
        <div class="user-email-display">
            <% if (session.getAttribute("userEmail") != null) { %>
            <i class="fas fa-user-check"></i> Currently logged in as: <span><%= session.getAttribute("userEmail") %></span>
            <% } else { %>
            <i class="fas fa-user-times"></i> Status: <span>Not logged in</span>
            <% } %>
        </div>

        <%-- Handle form submission --%>
        <%
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                // Check against user database
                if (userDatabase.containsKey(email) && userDatabase.get(email).equals(password)) {
                    session.setAttribute("userEmail", email);
                    session.setAttribute("userName", email.split("@")[0]);
                    response.sendRedirect("index.jsp");
                } else {
                    request.setAttribute("error", "Invalid email or password. Please try again.");
                }
            }
        %>

        <%-- Display error message if login failed --%>
        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message">
            <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form method="post">
            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email"
                       value="<%= session.getAttribute("userEmail") != null ?
                                      session.getAttribute("userEmail") : "" %>"
                       required>
                <i class="fas fa-envelope input-icon"></i>
            </div>

            <div class="input-group password-container">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
                <i class="fas fa-lock input-icon"></i>
                <span class="toggle-password" onclick="togglePassword('password')">
                        <i class="fas fa-eye"></i>
                    </span>
            </div>

            <button type="submit" class="btn btn-primary">
                <i class="fas fa-sign-in-alt"></i> Login
            </button>
        </form>

        <div class="form-footer">
            <p>Don't have an account? <a href="register.jsp">Register here</a></p>
            <p><a href="#" style="font-size: 14px;">Forgot your password?</a></p>
        </div>
    </div>
</div>

<script>
    // Toggle password visibility
    function togglePassword(fieldId) {
        const field = document.getElementById(fieldId);
        const icon = document.querySelector(`[onclick="togglePassword('${fieldId}')] i`);

        if (field.type === "password") {
            field.type = "text";
            icon.classList.remove("fa-eye");
            icon.classList.add("fa-eye-slash");
        } else {
            field.type = "password";
            icon.classList.remove("fa-eye-slash");
            icon.classList.add("fa-eye");
        }
    }
</script>
</body>
</html>