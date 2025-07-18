package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.product.ProductCreateRequest;
import com.example.inventorymanagementapi.dto.product.ProductResponse;
import com.example.inventorymanagementapi.entity.*;

public class ProductMapper {

    public static Product toEntity(ProductCreateRequest dto,
                                   ProductCategory category,
                                   ProductBrand brand,
                                   ProductUnit unit) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .sku(dto.getSku())
                .category(category)
                .manufactureDate(dto.getManufactureDate())
                .expiryDate(dto.getExpiryDate())
                .active(dto.getActive())
                .brand(brand)
                .unit(unit)
                .build();
    }

    public static ProductResponse toResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .sku(entity.getSku())
                .manufactureDate(entity.getManufactureDate())
                .expiryDate(entity.getExpiryDate())
                .active(entity.getActive())
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .brandName(entity.getBrand() != null ? entity.getBrand().getName() : null)
                .unitName(entity.getUnit() != null ? entity.getUnit().getName() : null)
                .build();
    }
}
