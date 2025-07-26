package com.example.inventorymanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
