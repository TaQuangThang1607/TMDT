package com.example.Custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Custom.domain.Cart;
import com.example.Custom.domain.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByUser(User user);
    
}
