package com.example.inventorymanagementapi.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ProductCreateRequest {
    @NotNull(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    private String sku;
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private Boolean active;
    private Long categoryId;
    private Long brandId;
    private Long unitId;
}