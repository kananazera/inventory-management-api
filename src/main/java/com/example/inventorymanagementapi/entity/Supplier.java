package com.example.inventorymanagementapi.entity;

import com.example.inventorymanagementapi.enums.ContactType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier extends BaseUser {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContactType type;
}