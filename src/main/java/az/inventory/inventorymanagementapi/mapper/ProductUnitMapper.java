package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitCreateRequest;
import az.inventory.inventorymanagementapi.dto.productunit.ProductUnitResponse;
import az.inventory.inventorymanagementapi.entity.ProductUnit;

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
