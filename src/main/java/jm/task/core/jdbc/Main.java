package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ethan", "Smith", (byte) 52);
        userService.saveUser("Olivia", "Johnson", (byte) 13);
        userService.saveUser("Mike", "Williams", (byte) 46);
        userService.saveUser("Isabella", "Brown", (byte) 8);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
