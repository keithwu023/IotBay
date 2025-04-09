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
    public User addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (Email, Password, Genre) VALUES (?, ?, ?)");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getGenre());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("SELECT MAX(UserId) FROM USERS");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int userId = resultSet.getInt(1);
        user.setId(userId);
        return user;
    }
    //UPDATE
    public void updateUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE USERS SET Email = ?, Password = ?, Genre = ? WHERE UserId = ?");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getGenre());
        preparedStatement.setInt(4, user.getId());
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