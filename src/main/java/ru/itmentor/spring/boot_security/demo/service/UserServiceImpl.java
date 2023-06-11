package ru.itmentor.spring.boot_security.demo.service;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.dao.UserDAO;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO usersDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO usersDAO, PasswordEncoder passwordEncoder) {
        this.usersDAO = usersDAO;
        this.passwordEncoder = passwordEncoder;
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
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        usersDAO.save(user);
    }

    @Transactional
    public void updateUser(User user) {
        User existingUser = usersDAO.findById(user.getId());
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(encryptedPassword);
            existingUser.setRoles(user.getRoles());
            usersDAO.save(existingUser);
        }
    }

    @Transactional
    public void deleteUser(int id) {
        usersDAO.deleteById(id);
    }
}
