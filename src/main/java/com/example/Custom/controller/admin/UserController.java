package com.example.Custom.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.Custom.domain.User;
import com.example.Custom.service.UploadService;
import com.example.Custom.service.UserService;

@Controller
public class UserController {
    private UserService userService;
    private final UploadService uploadService;
     private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder=  passwordEncoder;
    }

    @GetMapping("/admin/users")
    public String show(Model model) {
        List<User> list = this.userService.handleShowUser();
        model.addAttribute("showUser", list);
        return "admin/user/show";
    }

    @GetMapping("/admin/create/user")
    public String showCreateUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/createUser";
    }

    @PostMapping(value = "admin/create/user")
    public String createUser(@ModelAttribute User user,  @RequestParam("getFileImage") MultipartFile file) {
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        user.setAvatar(avatar);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));

         String hashPassword = passwordEncoder.encode(user.getPassword());
         user.setPassword(hashPassword);
        
        this.userService.handleCreateUser(user);
        return "redirect:/admin/users";
    }

    // ---------------------------------------------------
    // Update user

    @GetMapping("/admin/user/update/{id}")
    public String showUpdateUser(Model model, @PathVariable long id) {
        Optional<User> user = this.userService.handleGetById(id);
        if (user.isPresent()) {
            model.addAttribute("updateUser", user.get());
        } else {
            return "redirect:/admin/users";
        }
        return "admin/user/updateUser";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@ModelAttribute User user) {
        Optional<User> aUser = this.userService.handleGetById(user.getId());
        if (aUser.isPresent()) {
            User currentUser = aUser.get();
            currentUser.setEmail(user.getEmail());
            currentUser.setFullName(user.getFullName());
            currentUser.setPhone(user.getPhone());
            currentUser.setAddress(user.getAddress());

            this.userService.handleUpdateUser(currentUser);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/remove/{id}")
    public String removeUser(@PathVariable Long id) {
        userService.handleRemoveUser(id);
        return "redirect:/admin/users";
    }
}