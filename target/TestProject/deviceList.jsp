<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="model.dao.DBConnector" %>
<%@ page import="model.User" %>
<%@ page import="model.userType" %>

<%
  // Check if user is logged in and is a staff member
  User user = (User) session.getAttribute("staffUser");
  boolean isStaff = (user != null && user.getUserType() == userType.Staff);

  // Initialize database connection and get search parameters
  DBConnector db = new DBConnector();
  Connection conn = db.getConnection();
  String searchName = request.getParameter("name") != null ? request.getParameter("name") : "";
  String searchType = request.getParameter("type") != null ? request.getParameter("type") : "";

  // Prepare search query using LIKE for partial matches
  String query = "SELECT * FROM DEVICES WHERE DeviceName LIKE ? AND DeviceType LIKE ?";
  PreparedStatement stmt = conn.prepareStatement(query);
  stmt.setString(1, "%" + searchName + "%");
  stmt.setString(2, "%" + searchType + "%");
  ResultSet rs = stmt.executeQuery();
%>

<div class="container" style="padding-top: 30px;">

  <!-- Search form to filter devices by name and type -->
  <form method="get" action="deviceList.jsp" style="margin-bottom: 30px; display: flex; gap: 15px; align-items: center; flex-wrap: wrap;">
    <label>
      Name:
      <input type="text" name="name" value="<%= searchName %>" class="input-group-input" style="padding: 10px; font-size: 16px; border-radius: 5px; border: 1px solid #ccc; width: 220px;">
    </label>
    <label>
      Type:
      <input type="text" name="type" value="<%= searchType %>" class="input-group-input" style="padding: 10px; font-size: 16px; border-radius: 5px; border: 1px solid #ccc; width: 220px;">
    </label>
    <button type="submit" class="btn-primary" style="padding: 12px 25px; font-size: 16px; font-weight: 600;">Search</button>
  </form>

  <!-- Devices table showing list of devices -->
  <table style="width: 100%; border-collapse: collapse; box-shadow: 0 3px 8px rgba(0,0,0,0.1);">
    <thead style="background: var(--primary); color: white; font-weight: 600;">
    <tr>
      <th style="padding: 12px 15px; text-align: left;">Name</th>
      <th style="padding: 12px 15px; text-align: left;">Type</th>
      <th style="padding: 12px 15px; text-align: right;">Price ($)</th>
      <th style="padding: 12px 15px; text-align: right;">Quantity</th>
      <th style="padding: 12px 15px;">Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
      // Loop through the result set and display each device
      while (rs.next()) {
        int id = rs.getInt("DeviceId");
    %>
    <tr style="border-bottom: 1px solid #ddd;">
      <td style="padding: 12px 15px;"><%= rs.getString("DeviceName") %></td>
      <td style="padding: 12px 15px;"><%= rs.getString("DeviceType") %></td>
      <td style="padding: 12px 15px; text-align: right;"><%= String.format("%.2f", rs.getDouble("UnitPrice")) %></td>
      <td style="padding: 12px 15px; text-align: right;"><%= rs.getInt("Quantity") %></td>
      <td style="padding: 12px 15px; text-align: center;">
        <% if (isStaff) { %>
        <!-- Edit and Delete actions available only for staff -->
        <a href="updateForm.jsp?id=<%= id %>" class="btn-primary" style="padding: 6px 12px; font-size: 14px; margin-right: 8px; text-decoration: none; border-radius: 5px;">Edit</a>
        <a href="DeleteDeviceServlet?id=<%= id %>" class="btn-primary" style="padding: 6px 12px; font-size: 14px; background: var(--accent); border-color: var(--accent); text-decoration: none; border-radius: 5px;">Delete</a>
        <% } else { %>
        <span style="color: #888; font-size: 14px;">No access</span>
        <% } %>
      </td>
    </tr>
    <%
      }
      // Clean up resources
      rs.close();
      stmt.close();
      db.closeConnection();
    %>
    </tbody>
  </table>

  <!-- Navigation button to return to home -->
  <div>
    <form action="index.jsp" method="get">
      <input type="submit" value="← Back to Home"
             style="font-weight:bold; font-size:16px; padding:8px 16px; cursor:pointer;">
    </form>
  </div>

</div>
