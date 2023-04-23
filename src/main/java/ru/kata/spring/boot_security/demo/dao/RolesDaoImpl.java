package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Repository
public class RolesDaoImpl implements RolesDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        List<Role> rolesList = entityManager.createQuery("from Role", Role.class).getResultList();
        return new HashSet<>(rolesList);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return entityManager.createQuery("select r from Role r where r.role = :roleName", Role.class)
                .setParameter("roleName", roleName).getSingleResult();
    }

    @Override
    public Set<Role> getRoles(String[] roles) {
        Set<Role> currentRole = new HashSet<>();
        for (String role : roles) {
            currentRole.add(getRoleByName(role));
        }
        return currentRole;
    }
}
