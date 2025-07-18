package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.productbrand.ProductBrandCreateRequest;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandResponse;
import com.example.inventorymanagementapi.entity.ProductBrand;

public class ProductBrandMapper {

    public static ProductBrand toEntity(ProductBrandCreateRequest dto) {
        return ProductBrand.builder()
                .name(dto.getName())
                .build();
    }

    public static ProductBrandResponse toResponse(ProductBrand entity) {
        return ProductBrandResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
