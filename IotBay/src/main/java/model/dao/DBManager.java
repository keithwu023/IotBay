package model.dao;

import java.sql.*;
import model.User;

public class DBManager {
    private final Statement statement;
    private final Connection connection;

    public DBManager(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
    }

    public int getUserCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from user");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }
    public String getUserName(int userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select username from user where id = ?");
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString(1);
    }
    //CREATE
    public void addUser(User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into user values(Username,Email,Password)VALUES (?,?,?)");
        preparedStatement.setString(parameterIndex:1, user.getUsername());
        preparedStatement.setString(parameterIndex:2, user.getEmail());
        preparedStatement.setString(parameterIndex:3, user.getPassword());
        preparedStatement.executeUpdate();

        preparedStatement =connection.prepareStatement(sql:"SELECT MAX(UserId) FROM USERS");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int userId = resultSet.getInt(1);
    user.setID(userId);
    return user;
    }
    //Update
    public void updateUser(User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE USERS SET Email=?,Password=? WHERE ID=?");
        preparedStatement.setString(parameterIndex:1, user.getUsername());
        preparedStatement.setString(parameterIndex:2, user.getEmail());
        preparedStatement.setString(parameterIndex:3, user.getPassword());
        preparedStatement.setInt(parameterIndex:4, user.getID());
        preparedStatement.executeUpdate();
    }
    //DELETE
    public void removeUser(User user) throws SQLException {
        System.out.println("ID: " + user.getId());
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USERS WHERE UserId = ?");
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
    }
}