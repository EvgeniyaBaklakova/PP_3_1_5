package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user, String[] roles);
    List<User> getAllUsers();
    User findByUsername(String userName);
    User getUserById(int id);
    void updateUser(User user, String[] role);
    void deleteUser(int id);
}
