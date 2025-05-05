package com.example.Custom.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Service;

import com.example.Custom.domain.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        // chỉ su dụng session đã có sẵn, và không tạo session mới
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // // logic
    // User user = this.userService.getUserByEmail(username);

    // if (user == null) {
    // throw new UsernameNotFoundException("user not found");
    // }
    // return new User(
    // user.getEmail(),
    // user.getPassword(),
    // Collections.singletonList(new SimpleGrantedAuthority("ROLE_" +
    // user.getRole().getName())));

    // }

}