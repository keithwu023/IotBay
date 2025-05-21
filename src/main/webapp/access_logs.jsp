<%@ page import="java.util.*, model.AccessLog" %>
<%@ page import="model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Access Log - IoT Bay</title>
    <link rel="stylesheet" href="styles.css">

    <style>
        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            font-family: Arial, sans-serif;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #f4f4f4;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        h2 {
            text-align: center;
            margin-top: 30px;
        }

        form.form-group {
            text-align: center;
            margin: 20px;
        }

        button {
            margin-left: 10px;
            padding: 6px 12px;
        }
    </style>

</head>
<html>
<body>
<header>
    <div class="container">
        <nav>
            <i class="fas fa-bolt"></i> IoT Bay
            <div class="nav-links">
                Access Logs
                Profile
                Logout
            </div>
        </nav>
    </div>
</header>
    <%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String filterDate = request.getParameter("date");
    List<AccessLog> logs = (List<AccessLog>) request.getAttribute("logs");
%>

<h2>Your Access Logs</h2>

<form method="get" action="accesslogs" class="form-group">
    <label for="date">Filter by Date:</label>
    <input type="date" name="date" id="date" value="<%= (filterDate != null ? filterDate : "") %>">
    <button type="submit">Search</button>
    <button type="button">Clear</button>
</form>

<table>
    <thead>
    <tr>
        <th>Login Time</th>
        <th>Logout Time</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (logs != null && !logs.isEmpty()) {
            for (AccessLog log : logs) {
    %>
    <tr>
        <td><%= log.getLoginTime() %></td>
        <td><%= log.getLogoutTime() != null ? log.getLogoutTime() : "Still logged in" %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="2">No access logs found for this date.</td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

</html>