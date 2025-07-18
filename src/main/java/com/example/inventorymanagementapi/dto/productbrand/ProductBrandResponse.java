package com.example.inventorymanagementapi.dto.productbrand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBrandResponse {
    private Long id;
    private String name;
}