package com.example.inventorymanagementapi.dto.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductFilterRequest {

    private String name;
    private BigDecimal price;
    private String sku;
    private Long categoryId;
    private Long brandId;
    private Long unitId;
    private Boolean active;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
