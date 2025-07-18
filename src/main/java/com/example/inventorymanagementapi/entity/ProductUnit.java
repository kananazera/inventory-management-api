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
public class ProductUnit extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
