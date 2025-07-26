package com.example.inventorymanagementapi.dto.currency;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyFilterRequest {

    private String code;
    private String name;
}
