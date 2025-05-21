<%@ page import="model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - IoT Bay</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo"><i class="fas fa-bolt"></i> IoT Bay</a>
            <div class="nav-links">
                <a href="register.jsp">Register</a>
            </div>
        </nav>
    </div>
</header>

<%-- Handle logout message --%>
<%
    String message = request.getParameter("message");
    if ("logout_success".equals(message)) {
        request.setAttribute("success", "You have been successfully logged out.");
    }
%>

<div class="login-container">
    <div class="login-form">
        <h2>Login to Your Account</h2>

        <%-- ✅ Success Message Block properly closed --%>
        <% if (request.getAttribute("success") != null) { %>
        <div class="success-message">
            <i class="fas fa-check-circle"></i> <%= request.getAttribute("success") %>
        </div>
        <% } %> <%-- ✅ Closing tag added here --%>

        <%-- Show user email if logged in --%>
        <div class="user-email-display">
            <p>Currently logged in as:
                <%
                    if (session.getAttribute("user") == null) {
                %>
                Not logged in
                <%
                } else {
                %>
                <%= ((User) session.getAttribute("user")).getEmail() %>
                <%
                    }
                %>
            </p>
        </div>

        <%-- Show login error message --%>
        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message">
            <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <%-- Login Form --%>
        <form method="post" action="login">
            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email"
                       value="<%= session.getAttribute("userEmail") != null ? session.getAttribute("userEmail") : "" %>"
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
            <p><a href="#">Forgot your password?</a></p>
        </div>
    </div>
</div>

<script>
    // Password toggle visibility
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
