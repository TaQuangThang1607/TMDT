package com.example.Custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Custom.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>{
    
}
