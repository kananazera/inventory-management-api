package com.example.inventorymanagementapi.dto.product;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private Boolean active;
    private String categoryName;
    private String brandName;
    private String unitName;
}
