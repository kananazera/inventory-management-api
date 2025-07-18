package com.example.inventorymanagementapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true)
    private String sku;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private LocalDate manufactureDate;

    private LocalDate expiryDate;

    private Boolean active = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private ProductBrand brand;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private ProductUnit unit;
}
