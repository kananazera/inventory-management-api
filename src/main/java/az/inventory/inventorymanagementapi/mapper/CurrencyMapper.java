package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.currency.CurrencyCreateRequest;
import az.inventory.inventorymanagementapi.dto.currency.CurrencyResponse;
import az.inventory.inventorymanagementapi.entity.Currency;

public class CurrencyMapper {

    public static Currency toEntity(CurrencyCreateRequest dto) {
        return Currency.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .symbol(dto.getSymbol())
                .build();
    }

    public static CurrencyResponse toResponse(Currency entity) {
        return CurrencyResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .symbol(entity.getSymbol())
                .build();
    }
}
