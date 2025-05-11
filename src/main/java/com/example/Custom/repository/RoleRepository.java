package com.example.Custom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Custom.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
