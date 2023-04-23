package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user, String[] role);
    List<User> getAllUsers();
    User getUserById(int id);
    void updateUser(User user, String[] role);
    void deleteUser(int id);
    User findByUsername(String userName);
}
