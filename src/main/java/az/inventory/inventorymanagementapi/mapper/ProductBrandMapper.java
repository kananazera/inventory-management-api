package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.productbrand.ProductBrandCreateRequest;
import az.inventory.inventorymanagementapi.dto.productbrand.ProductBrandResponse;
import az.inventory.inventorymanagementapi.entity.ProductBrand;

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
