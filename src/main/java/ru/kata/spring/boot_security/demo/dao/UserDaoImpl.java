package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder encoder;
    private final RolesDao rolesDao;

    public UserDaoImpl(PasswordEncoder encoder, RolesDao rolesDao) {
        this.encoder = encoder;
        this.rolesDao = rolesDao;
    }


    @Override
    public void addUser(User user, String[] role) {
        user.setRoles(rolesDao.getRoles(role));
        user.setPassword(encoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findByUsername(String userName) {
        return entityManager.createQuery(
                        "SELECT u from User u  join fetch u.roles WHERE u.userName = :username", User.class).
                setParameter("username", userName).getSingleResult();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user, String[] roles) {
        user.setRoles(rolesDao.getRoles(roles));
        user.setPassword(encoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(getUserById(id));
    }
}
