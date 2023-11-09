package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS USERS( ID INT NOT NULL AUTO_INCREMENT, " +
                "NAME VARCHAR(15), LASTNAME VARCHAR(25), AGE TINYINT, PRIMARY KEY (ID))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table", e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USERS";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop table", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO USERS(NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM USERS WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove user", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user;
        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                user = new User(resultSet.getString("NAME"),
                        resultSet.getString("LASTNAME"),
                        resultSet.getByte("AGE"));
                user.setId(resultSet.getLong("ID"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all users", e);
        }
        users.stream().forEach(System.out::println);
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM USERS";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean users table", e);
        }
    }
}
