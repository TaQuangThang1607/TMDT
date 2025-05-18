package com.example.Custom.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.example.Custom.domain.Role;
import com.example.Custom.domain.User;
import com.example.Custom.domain.dto.RegisterDTO;
import com.example.Custom.repository.RoleRepository;
import com.example.Custom.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        
    }

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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
