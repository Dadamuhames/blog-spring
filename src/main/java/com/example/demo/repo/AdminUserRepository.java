package com.example.demo.repo;

import com.example.demo.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
