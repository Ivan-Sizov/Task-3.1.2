package ru.itmentor.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.dao.UserDAO;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final UserDAO usersDAO;

    @Autowired
    public UserService(UserDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public List<User> getAllUsers() {
        return usersDAO.getAllUsers();
    }

    public User getUser(int id) {
        return usersDAO.getUser(id);
    }

    public User getUserByUsername(String username) {
        return (User) usersDAO.getUserByName(username);
    }

    @Transactional
    public void addUser(User user) {
        usersDAO.addUser(user);
    }

    @Transactional
    public void updateUser(User user) {
        usersDAO.updateUser(user);
    }

    @Transactional
    public void deleteUser(int id) {
        User user = usersDAO.getUser(id);
        usersDAO.deleteUser(user);
    }
}
