<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile - IoT Bay</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo"><i class="fas fa-bolt"></i> IoT Bay</a>
            <div class="nav-links">
                <a href="index.jsp">Home</a>
                <a href="login.jsp">Login</a>
                <a href="register.jsp">Register</a>
                <a href="logout.jsp">Logout</a>
                <a href="access_logs.jsp">Access Logs</a>
                <a href="profile.jsp">Profile</a>
            </div>
        </nav>
    </div>
</header>
    <style>
        main.edit-profile-section {
            padding: 40px 20px;
            display: flex;
            justify-content: center;
            min-height: calc(100vh - 70px);
        }

        .profile-card {
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
            max-width: 600px;
            width: 100%;
            padding: 30px 40px;
        }

        form .profile-details {
            display: flex;
            flex-direction: column;
            gap: 18px;
        }

        .detail-row {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .detail-label {
            flex: 0 0 120px;
            font-weight: 600;
            color: var(--dark);
        }

        .detail-value input {
            flex: 1;
            min-width: 300px;
            padding: 10px 15px;
            border-radius: 6px;
            border: 1.5px solid #ccc;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        .detail-value input:focus {
            outline: none;
            border-color: var(--primary);
            box-shadow: 0 0 8px rgba(52, 152, 219, 0.3);
        }

        .profile-actions {
            margin-top: 30px;
            display: flex;
            gap: 15px;
            justify-content: center;
        }

        .btn {
            padding: 12px 24px;
            border-radius: 6px;
            font-weight: 600;
            color: white;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: all 0.3s;
            text-decoration: none;
            justify-content: center;
        }

        .btn-access {
            background: #656565;
        }

        .btn-access:hover {
            background: #e37474;
        }

        .btn-save {
            background: #006387;
        }

        .btn-save:hover {
            background: #2dbdf1;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
        }

        @media (max-width: 600px) {
            .detail-row {
                flex-direction: column;
                align-items: flex-start;
            }

            .detail-label {
                margin-bottom: 6px;
            }

            .profile-actions {
                flex-direction: column;
                gap: 10px;
                justify-content: center;
            }

            .btn {
                width: 100%;
            }
        }
    </style>

<main class="edit-profile-section">
    <div class="profile-card">
        <h1>Profile</h1>
        <%
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
        <div style="text-align:center; margin-bottom: 20px; padding: 10px; background-color: #d4edda; color: #155724; border-radius: 6px; border: 1px solid #c3e6cb;">
            <%= message %>
        </div>
        <%
            }
        %>
        <form action="profile" method="POST">
            <div class="profile-details">
                <div class="detail-row">
                    <div class="detail-label">Full Name</div>
                    <div class="detail-value">
                        <input type="text" name="fullName" value="<%= user.getUsername() %>" required />
                    </div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Email Address</div>
                    <div class="detail-value">
                        <input type="email" name="email" value="<%= user.getEmail() %>" required />
                    </div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Phone Number</div>
                    <div class="detail-value">
                        <input type="text" name="phone" value="<%= user.getPhone() != null ? user.getPhone() : "" %>" />
                    </div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Address</div>
                    <div class="detail-value">
                        <input type="text" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>" />
                    </div>
                </div>

                <div class="detail-row">
                    <div class="detail-label">Password</div>
                    <div class="detail-value">
                        <!-- Changed to text input to show password -->
                        <input type="text" name="password" value="<%= user.getPassword() %>" required />
                    </div>
                </div>
            </div>

            <div class="profile-actions">
                <a href="deleteUser" class="btn btn-access">
                    Delete Account
                </a>
                <button type="submit" class="btn btn-save">
                    Save Changes
                </button>
            </div>
        </form>
    </div>
</main>

<footer>
    <div class="container">
        <p>&copy; 2023 IoT Bay. All rights reserved.</p>
    </div>
</footer>
</body>
</html>
