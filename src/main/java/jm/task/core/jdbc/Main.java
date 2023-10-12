package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Petr", "Petrov", (byte) 25);
        userService.saveUser("Sidr", "Sidorov", (byte) 30);
        userService.saveUser("Aleksandr", "Aleksandrov", (byte) 35);

        List<User> users = userService.getAllUsers();

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}