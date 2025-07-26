package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.product.ProductCreateRequest;
import com.example.inventorymanagementapi.dto.product.ProductResponse;
import com.example.inventorymanagementapi.entity.*;

public class ProductMapper {

    public static Product toEntity(ProductCreateRequest dto,
                                   ProductCategory category,
                                   ProductBrand brand,
                                   ProductUnit unit,
                                   String imageUrl) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .sku(dto.getSku())
                .category(category)
                .active(dto.getActive())
                .brand(brand)
                .unit(unit)
                .imageUrl(imageUrl)
                .build();
    }

    public static ProductResponse toResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .sku(entity.getSku())
                .active(entity.getActive())
                .category(entity.getCategory())
                .brand(entity.getBrand())
                .unit(entity.getUnit())
                .imageUrl(entity.getImageUrl())
                .build();
    }
}