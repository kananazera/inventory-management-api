package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.productcategory.ProductCategoryCreateRequest;
import az.inventory.inventorymanagementapi.dto.productcategory.ProductCategoryResponse;
import az.inventory.inventorymanagementapi.entity.ProductCategory;

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
