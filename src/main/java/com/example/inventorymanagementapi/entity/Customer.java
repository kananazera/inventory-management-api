package com.example.inventorymanagementapi.entity;

import com.example.inventorymanagementapi.enums.ContactType;
import com.example.inventorymanagementapi.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String fullName;

    private String phone;

    private String address;

    private Gender gender;

    private LocalDate birthDate;

    private Boolean active = Boolean.TRUE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType type;

    @OneToMany(mappedBy = "customer")
    private List<Contract> contracts;
}

