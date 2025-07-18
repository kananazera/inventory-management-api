package com.example.inventorymanagementapi.dto.product;

import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryFilterRequest;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class ProductFilterRequest {

    private String name;
    private BigDecimal price;
    private String sku;
    private ProductCategoryFilterRequest category;
    private Long brandId;
    private Long unitId;
    private Boolean active;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
