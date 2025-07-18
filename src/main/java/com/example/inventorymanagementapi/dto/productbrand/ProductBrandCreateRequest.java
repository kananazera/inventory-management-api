package com.example.inventorymanagementapi.dto.productbrand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBrandCreateRequest {
    private String name;
}
