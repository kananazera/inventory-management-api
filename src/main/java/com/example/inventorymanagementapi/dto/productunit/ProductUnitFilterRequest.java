package com.example.inventorymanagementapi.dto.productunit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUnitFilterRequest {
    private String name;
}
