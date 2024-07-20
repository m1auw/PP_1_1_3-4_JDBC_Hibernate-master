package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS `mydbusers`.`users` (
                  `id` INT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(45) NULL,
                  `lastName` VARCHAR(45) NULL,
                  `age` INT NULL,
                  PRIMARY KEY (`id`))
                ENGINE = InnoDB
                DEFAULT CHARACTER SET = utf8;
                """;
        sqlPS(sql);
    }

    @Override
    public void dropUsersTable() {
        String sql = """
                DROP TABLE IF EXISTS `mydbusers`.`users`
                """;
        sqlPS(sql);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO `mydbusers`.`users` (name, lastName, age) VALUES (?, ?, ?)
                """;

        try (Connection conn = Util.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.printf("User с именем — %s добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM `mydbusers`.`users` WHERE id = " + id;
        sqlPS(sql);
    }

    @Override
    public List<User> getAllUsers() {
        String sql = """
                SELECT * FROM `mydbusers`.`users`
                """;
        List<User> users = new ArrayList<>();

        try (Connection conn = Util.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = """
                DELETE FROM `mydbusers`.`users`
                """;
        sqlPS(sql);
    }

    //Добавила этот метод, чтобы не дублировать код
    private static void sqlPS(String sql) {
        try (Connection conn = Util.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
