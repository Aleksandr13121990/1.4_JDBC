package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Вася", "Пупкин", (byte) 50);
        userService.saveUser("Петя", "Иванов", (byte) 25);
        userService.saveUser("Миша", "Петров", (byte) 20);
        userService.saveUser("Маша", "Сидорова", (byte) 18);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
