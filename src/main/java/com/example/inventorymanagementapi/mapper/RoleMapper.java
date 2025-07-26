package com.example.inventorymanagementapi.mapper;

import com.example.inventorymanagementapi.dto.role.RoleCreateRequest;
import com.example.inventorymanagementapi.dto.role.RoleResponse;
import com.example.inventorymanagementapi.entity.Role;

public class RoleMapper {

    public static Role toEntity(RoleCreateRequest dto) {
        return Role.builder()
                .name(dto.getName())
                .build();
    }

    public static RoleResponse toResponse(Role entity) {
        return RoleResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
