package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findByUsername(String userName) {
        return entityManager.createQuery(
                        "SELECT u from User u  join fetch u.roles WHERE u.email = :username", User.class).
                setParameter("username", userName).getSingleResult();
    }


    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public boolean updateUser(User user) {
        if (getUserById(user.getId()) != null) {
            entityManager.merge(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        if (getUserById(id) != null) {
            entityManager.createQuery("delete from User where id = :id").setParameter("id", id).executeUpdate();
            return true;
        } else {
            return false;
        }
    }
}
