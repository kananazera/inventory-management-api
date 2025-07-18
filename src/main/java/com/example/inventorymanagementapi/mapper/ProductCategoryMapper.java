package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryCreateRequest;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryResponse;
import com.example.inventorymanagementapi.entity.ProductCategory;

public class ProductCategoryMapper {

    public static ProductCategory toEntity(ProductCategoryCreateRequest dto) {
        return ProductCategory.builder()
                .name(dto.getName())
                .build();
    }

    public static ProductCategoryResponse toResponse(ProductCategory entity) {
        return ProductCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
