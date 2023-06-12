package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface UserService {
    public Set<User> getAllUsers();

    public User getUser(int id);

    public User getUserByUsername(String username);

    public void addUser(User user);

    public void updateUser(User user);

    public void deleteUser(int id);
}
