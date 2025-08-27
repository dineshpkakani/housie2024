package com.ecw.auth.securityservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", columnDefinition = "VARCHAR(20) DEFAULT 'PLAYER'" , nullable = false, length = 255)
    private String role="ROLE_PLAYER";

    @PrePersist //If you want more control, you can set defaults right before the entity is saved.
    public void prePersist() {
        if (role == null || "".equals(role)) {
            role = "PLAYER"; // default role if not provided
        }
    }
}