package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.model.UserRole;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.model.UserDTO;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public Set<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO) {
        User user = fillUserObject(userDTO, new User());
        userService.addUser(user);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody UserDTO updatedUserDTO) {
        User user = fillUserObject(updatedUserDTO, userService.getUser(id));
        userService.updateUser(user);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    public User fillUserObject(UserDTO userDTO, User user) {
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        if (user.getRoles() != null) {
            user.getRoles().clear();
        } else {
            user.setRoles(new HashSet<>());
        }

        Set<UserRole> roles = Arrays.stream(userDTO.getRoles()).map(role -> {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole("ROLE_" + role);
            return userRole;
        }).collect(Collectors.toSet());

        user.getRoles().addAll(roles);

        return user;
    }
}
