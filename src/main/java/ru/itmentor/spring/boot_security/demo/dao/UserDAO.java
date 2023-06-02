package ru.itmentor.spring.boot_security.demo.dao;


import org.springframework.security.core.userdetails.UserDetails;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDAO {
    User getUser(int id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    List<User> getAllUsers();

    UserDetails getUserByName(String username);
}
