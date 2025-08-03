package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.role.RoleCreateRequest;
import az.inventory.inventorymanagementapi.dto.role.RoleResponse;
import az.inventory.inventorymanagementapi.entity.Role;

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
