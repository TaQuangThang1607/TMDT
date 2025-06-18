package com.example.Custom.repository;

import com.example.Custom.model.DesignRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesignRequestRepository extends JpaRepository<DesignRequest, Long> {
}