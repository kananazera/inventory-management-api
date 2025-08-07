package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.tax.TaxCreateRequest;
import az.inventory.inventorymanagementapi.dto.tax.TaxResponse;
import az.inventory.inventorymanagementapi.entity.Tax;

public class TaxMapper {

    public static Tax toEntity(TaxCreateRequest dto) {
        return Tax.builder()
                .name(dto.getName())
                .rate(dto.getRate())
                .build();
    }

    public static TaxResponse toResponse(Tax entity) {
        return TaxResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rate(entity.getRate())
                .build();
    }
}
