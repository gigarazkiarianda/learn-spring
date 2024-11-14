package com.example.crud.controller;

import com.example.crud.model.User;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Display list of users (GET request for /users)
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());  // Pass list of users to the view
        return "user-list";  // Maps to src/main/resources/templates/user-list.html
    }

    // Show form to add a new user (GET request for /users/new)
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());  // Empty User object to bind the form
        return "user-form";  // Maps to src/main/resources/templates/user-form.html
    }

    // Add a new user (POST request for /users/add)
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);  // Save the new user using UserService
        return "redirect:/users";  // Redirect to user list page after adding
    }

    // Show form to edit a user (GET request for /users/edit/{id})
    @GetMapping("/edit/{id}")
public String showEditForm(@PathVariable("id") Long id, Model model) {
    Optional<User> user = userService.getUserById(id);
    if (user.isPresent()) {
        model.addAttribute("user", user.get());
        return "user-form";
    } else {
        return "redirect:/users";  // Redirect if user not found
    }
}

    // Update user (POST request for /users/edit/{id})
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        user.setId(id);  // Ensure the correct user ID is set for updating
        userService.updateUser(id, user);  // Call service to update user details
        return "redirect:/users";  // Redirect to the user list after update
    }

    // Delete user (Use POST method for delete action in HTML forms)
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);  // Call the service to delete the user by ID
        return "redirect:/users";  // Redirect to the user list page after deletion
    }
}
