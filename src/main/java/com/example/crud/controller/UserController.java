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

    // Menampilkan daftar pengguna (GET request for /users)
    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user-list";  // This will map to src/main/resources/templates/user-list.html
    }
    
    // Menampilkan form tambah pengguna (GET request for /users/new)
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User()); 
        return "user-form";  // This will map to src/main/resources/templates/user-form.html
    }

    // Menambah pengguna baru (POST request for /users/add)
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user); // Saves the new user
        return "redirect:/users"; // Redirect to the user list page after adding
    }

    // Menampilkan form edit pengguna (GET request for /users/edit/{id})
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.getUserById(id); // Find user by ID
        if (user.isPresent()) {
            model.addAttribute("user", user.get()); // Populate model with user for editing
            return "user-form"; // View name for the form to edit user
        } else {
            return "redirect:/users"; // If user not found, redirect to user list
        }
    }

    // Menghapus pengguna (POST request for /users/delete/{id})
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Call service to delete user
        return "redirect:/users"; // Redirect to the user list page after deletion
    }
}
