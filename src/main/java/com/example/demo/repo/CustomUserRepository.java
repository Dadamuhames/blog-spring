package com.example.demo.repo;

import com.example.demo.models.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    Optional<CustomUser> findByPhoneNumberAndIsActive(String phoneNumber, Boolean isActive);

    Boolean existsByPhoneNumberAndIsActive(String phoneNumber, Boolean isActive);
}
