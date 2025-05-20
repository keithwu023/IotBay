<%@ page import="java.util.*, model.AccessLog" %>
<%@ page import="model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Access Log - IoT Bay</title>
    <link rel="stylesheet" href="styles.css">
</head>
<html>
<body>
<header>
    <div class="container">
        <nav>
            <a href="index.jsp" class="logo"><i class="fas fa-bolt"></i> IoT Bay</a>
            <div class="nav-links">
                <a href="access_logs.jsp">Access Logs</a>
                <a href="profile.jsp">Profile</a>
                <a href="logout">Logout</a>
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
    <a href="accesslogs"><button type="button">Clear</button></a>
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
