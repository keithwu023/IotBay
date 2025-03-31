<%@page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (user)session.getAttribute("currentUser");
%>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="content">
    <button>
        <a href="login.jsp"></a>
    </button>
    <button>
        <a href="register.jsp"></a>
    </button>
</div>

</body>
</html>
