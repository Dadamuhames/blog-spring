package com.example.demo.models;

import com.example.demo.validators.annotations.PhoneNumberConstraint;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CustomUser {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @PhoneNumberConstraint
    private String phoneNumber;

    private boolean isActive = true;
}
