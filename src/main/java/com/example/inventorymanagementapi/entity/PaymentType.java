package com.example.inventorymanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
