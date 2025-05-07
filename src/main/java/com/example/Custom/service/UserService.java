package com.example.Custom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Custom.domain.User;
import com.example.Custom.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public List<User> handleShowUser() {
        return this.userRepository.findAll();
    }

    public Optional<User> handleGetById(long id) {
        return this.userRepository.findById(id);
    }

    public User handleUpdateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleRemoveUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        userRepository.delete(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
