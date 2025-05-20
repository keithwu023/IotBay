<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register - IoT Bay</title>
    <link rel="stylesheet" href="styles.css">
    <!-- Include Font Awesome for eye icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        .password-container {
            position: relative;
        }
        .toggle-password {
            position: absolute;
            top: 38px;
            right: 12px;
            cursor: pointer;
            color: #666;
        }
        .toggle-password:hover {
            color: #333;
        }
    </style>
</head>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo"><i class="fas fa-bolt"></i> IoT Bay</a>
            <div class="nav-links">
                <a href="login.jsp">Login</a>
            </div>
        </nav>
    </div>
</header>

<div class="register-container">
    <div class="register-form">
        <h2>Create Your Account</h2>

        <!-- ✅ CORRECTED FORM ACTION -->
        <form method="post" action="register">
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

            <div class="input-group">
                <label for="address">Address</label>
                <input type="text" id="address" name="address">
            </div>

            <div class="input-group">
                <label for="dateOfBirth">Date of Birth</label>
                <input type="date" id="dateOfBirth" name="dateOfBirth">
            </div>

            <div class="input-group">
                <label for="gender">Gender</label>
                <select id="gender" name="gender">
                    <option value="">-- Select Gender --</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </select>
            </div>

            <div class="input-group">
                <label for="userType">User Type</label>
                <select id="userType" name="userType">
                    <option value="Customer">Customer</option>
                    <option value="Staff">Staff</option>
                </select>
            </div>

            <div class="input-group">
                <label>
                    <input type="checkbox" id="tos" name="tos" required>
                    I agree to the Terms of Service
                </label>
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
