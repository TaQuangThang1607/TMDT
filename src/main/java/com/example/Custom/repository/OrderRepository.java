package com.example.Custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Custom.domain.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{
    
}
