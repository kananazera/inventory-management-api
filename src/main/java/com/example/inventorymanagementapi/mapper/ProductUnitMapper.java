package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.productunit.ProductUnitCreateRequest;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitResponse;
import com.example.inventorymanagementapi.entity.ProductUnit;

public class ProductUnitMapper {

    public static ProductUnit toEntity(ProductUnitCreateRequest dto) {
        return ProductUnit.builder()
                .name(dto.getName())
                .build();
    }

    public static ProductUnitResponse toResponse(ProductUnit entity) {
        return ProductUnitResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
