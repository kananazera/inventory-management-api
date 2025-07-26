package com.example.inventorymanagementapi.dto.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleFilterRequest {

    private String name;
}
