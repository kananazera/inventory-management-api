package com.example.inventorymanagementapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseUser extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String fullName;

    private String phone;

    @Column(unique = true)
    private String email;

    private String address;

    private String gender;

    private LocalDate birthDate;

    private Boolean active = Boolean.TRUE;
}
