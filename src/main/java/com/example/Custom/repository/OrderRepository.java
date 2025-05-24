package com.example.Custom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Custom.domain.Order;
import com.example.Custom.domain.User;

public interface OrderRepository extends JpaRepository<Order,Long>{

    List<Order> findByUser(User user);
    
}
