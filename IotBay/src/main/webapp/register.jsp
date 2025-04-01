<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - IoT Bay</title>
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

<div class="register-container">
    <div class="register-form">
        <h2>Create Your Account</h2>

        <%-- Handle form submission --%>
        <%
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String phone = request.getParameter("phone");

                // Simple validation
                if (!password.equals(confirmPassword)) {
                    request.setAttribute("error", "Passwords do not match!");
                } else {
                    // Get or create user database in application scope
                    Map<String, String> userDatabase = new HashMap<>();
                    if (application.getAttribute("userDatabase") != null) {
                        userDatabase = (Map<String, String>)application.getAttribute("userDatabase");
                    }

                    // Add new user
                    userDatabase.put(email, password);
                    application.setAttribute("userDatabase", userDatabase);

                    // Auto-login after registration
                    response.sendRedirect("index.jsp");
                }
            }
        %>

        <%-- Display error message if registration failed --%>
        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message">
            <i class="fas fa-exclamation-circle"></i> <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form method="post">
            <div class="form-row">
                <div class="input-group">
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" required>
                </div>
                <div class="input-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" required>
                </div>
            </div>

            <div class="input-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-row">
                <div class="input-group password-container">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                    <span class="toggle-password" onclick="togglePassword('password')">
                            <i class="fas fa-eye"></i>
                        </span>
                </div>
                <div class="input-group password-container">
                    <label for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                    <span class="toggle-password" onclick="togglePassword('confirmPassword')">
                            <i class="fas fa-eye"></i>
                        </span>
                </div>
            </div>

            <div class="input-group">
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" required>
            </div>

            <button type="submit" class="btn btn-primary">
                <i class="fas fa-user-plus"></i> Register
            </button>
        </form>

        <div class="form-footer">
            <p>Already have an account? <a href="login.jsp">Login here</a></p>
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