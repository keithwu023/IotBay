<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="model.dao.DBConnector" %>
<%
    String idStr = request.getParameter("id");
    if (idStr == null) {
        response.sendRedirect("ReadDeviceServlet");
        return;
    }
    int id = Integer.parseInt(idStr);

    DBConnector db = new DBConnector();
    Connection conn = null;
    String name = "";
    String type = "";
    double price = 0;
    int quantity = 0;

    try {
        conn = db.getConnection();
        String sql = "SELECT * FROM DEVICES WHERE DeviceId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            name = rs.getString("DeviceName");
            type = rs.getString("DeviceType");
            price = rs.getDouble("UnitPrice");
            quantity = rs.getInt("Quantity");
        } else {
            response.sendRedirect("ReadDeviceServlet");
            return;
        }
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Update Device</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 40px auto;
            padding: 0 20px;
        }
        form label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }
        form input[type="text"],
        form input[type="number"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        form input[type="submit"] {
            margin-top: 20px;
            padding: 10px 25px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<h2>Update Device</h2>
<form action="UpdateDeviceServlet" method="post">
    <input type="hidden" name="id" value="<%= id %>">

    <label for="name">Name:</label>
    <input type="text" id="name" name="name" value="<%= name %>" required>

    <label for="type">Type:</label>
    <input type="text" id="type" name="type" value="<%= type %>" required>

    <label for="unit_price">Price:</label>
    <input type="number" step="0.01" id="unit_price" name="unit_price" value="<%= price %>" required>

    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" value="<%= quantity %>" required>

    <input type="submit" value="Update">
</form>
</body>
</html>
