package com.example.inventorymanagementapi.dto.currency;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyUpdateRequest {

    @NotNull(message = "Code is required")
    private String code;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Symbol is required")
    private Character symbol;
}
