package ru.itmentor.spring.boot_security.demo.dao;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    public void addUser(User user) {
        entityManager.persist(user);
    }

    public void updateUser(User user) {
        entityManager.merge(user);
    }

    public void deleteUser(User user) {
        entityManager.remove(user);
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public UserDetails getUserByName(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class);
        query.setParameter("username", username);

        List<User> users = query.getResultList();

        User user = null;

        if (!users.isEmpty()) {
            user = users.get(0);
        }

        return user;
    }
}

