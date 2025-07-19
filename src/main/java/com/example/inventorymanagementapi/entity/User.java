package com.example.inventorymanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseUser {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}