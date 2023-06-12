package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.model.UserDTO;
import ru.itmentor.spring.boot_security.demo.model.UserRole;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminController {
    private final UserService userServiceImpl;

    @Autowired
    public AdminController(UserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("")

    public String getAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.getAllUsers());
        return "/users/adminPanel";
    }

    @GetMapping("{id}/edit")
    public String editUserPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userServiceImpl.getUser(id));
        return "/users/editUser";
    }

    @GetMapping("new")
    public String newUserPage(@ModelAttribute("user") User user) {
        return "/users/addNewUser";
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") int id, @RequestBody UserDTO updatedUserDTO) {
        User existingUser = userServiceImpl.getUser(id);
        existingUser.setUsername(updatedUserDTO.getUsername());
        existingUser.setName(updatedUserDTO.getName());
        existingUser.setSurname(updatedUserDTO.getSurname());
        existingUser.setAge(updatedUserDTO.getAge());
        existingUser.setEmail(updatedUserDTO.getEmail());
        existingUser.setPassword(updatedUserDTO.getPassword());

        Set<UserRole> newRoles = Arrays.stream(updatedUserDTO.getRoles()).map(roleStr -> {
            UserRole userRole = new UserRole();
            userRole.setUser(existingUser);
            userRole.setRole("ROLE_" + roleStr);
            return userRole;
        }).collect(Collectors.toSet());

        existingUser.getRoles().clear();
        existingUser.getRoles().addAll(newRoles);

        userServiceImpl.updateUser(existingUser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("")
    public String addNewUser(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        user.addRole(userRole);
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @ModelAttribute("loggedInUser")
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userServiceImpl.getUserByUsername(username);
    }
}
