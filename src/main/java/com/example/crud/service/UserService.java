package com.example.crud.service;

import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Add a new user
    public void addUser(User user) {
        userRepository.save(user);
    }

    // Update an existing user
    public void updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);  // Ensure the ID remains the same during update
            userRepository.save(user);
        }
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
