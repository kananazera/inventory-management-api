package com.example.inventorymanagementapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    private Boolean active = true;

    private Long categoryId;
    private Long brandId;
    private Long unitId;
}