<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Invalidate the session completely
    if (session != null) {
        session.invalidate();
    }

    // Redirect to login page with a logout message
    response.sendRedirect("login.jsp?message=logout_success");
%>