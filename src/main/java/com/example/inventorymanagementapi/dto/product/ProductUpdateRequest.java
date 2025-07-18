package com.example.inventorymanagementapi.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProductUpdateRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private Boolean active;
    private Long categoryId;
    private Long brandId;
    private Long unitId;
}