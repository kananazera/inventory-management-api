package com.example.inventorymanagementapi.entity;

import com.example.inventorymanagementapi.enums.ContactType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType type;

    @Column(nullable = false, unique = true)
    private String name;

    private String phone;

    private String email;

    private String address;
}

