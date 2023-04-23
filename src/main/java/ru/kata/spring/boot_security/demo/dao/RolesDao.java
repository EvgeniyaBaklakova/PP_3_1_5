package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RolesDao {
    Set<Role> getAllRoles();
    Role getRoleByName(String name);
    Set<Role> getRoles(String[] roles);


}
