package com.example.inventorymanagementapi.dto.productcategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryCreateRequest {
    private String name;
}
