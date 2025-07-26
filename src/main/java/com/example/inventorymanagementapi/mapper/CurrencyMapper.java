package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.currency.CurrencyCreateRequest;
import com.example.inventorymanagementapi.dto.currency.CurrencyResponse;
import com.example.inventorymanagementapi.entity.Currency;

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
