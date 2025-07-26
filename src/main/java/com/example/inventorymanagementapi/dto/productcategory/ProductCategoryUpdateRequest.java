package com.example.inventorymanagementapi.dto.productcategory;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductCategoryUpdateRequest {

    @NotNull(message = "Name is required")
    private String name;
}
