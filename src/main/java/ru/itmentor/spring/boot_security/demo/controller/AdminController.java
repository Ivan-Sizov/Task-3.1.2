package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

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
        return "users/allUsers";
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
    public String editUser(@PathVariable("id") int id, @ModelAttribute("user") User updatedUser) {
        User existingUser = userServiceImpl.getUser(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setAge(updatedUser.getAge());
        userServiceImpl.updateUser(existingUser);
        return "redirect:/admin";
    }


    @PostMapping("")
    public String addNewUser(@ModelAttribute("user") User user) {
        userServiceImpl.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userServiceImpl.deleteUser(id);
        return "redirect:/admin";
    }
}
