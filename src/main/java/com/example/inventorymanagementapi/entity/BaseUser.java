package com.example.inventorymanagementapi.entity;

import com.example.inventorymanagementapi.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseUser extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String fullName;

    private String phone;

    private String address;

    private Gender gender;

    private LocalDate birthDate;

    private Boolean active = Boolean.TRUE;
}
