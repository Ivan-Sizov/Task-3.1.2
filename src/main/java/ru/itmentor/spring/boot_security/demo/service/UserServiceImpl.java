package ru.itmentor.spring.boot_security.demo.service;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.dao.UserDAO;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO usersDAO;

    @Autowired
    public UserServiceImpl(UserDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public List<User> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    public User getUser(int id) {
        User user = usersDAO.findByIdWithRoles(id);
        if (user != null) {
            Hibernate.initialize(user.getRoles());
        }
        return user;
    }

    public User getUserByUsername(String username) {
        return (User) usersDAO.getUserByUsername(username);
    }

    @Transactional
    public void addUser(User user) {
        usersDAO.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        User existingUser = usersDAO.findById(user.getId());
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            Hibernate.initialize(existingUser.getRoles());
            existingUser.setRoles(user.getRoles());
            usersDAO.save(existingUser);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        usersDAO.deleteById(id);
    }
}
