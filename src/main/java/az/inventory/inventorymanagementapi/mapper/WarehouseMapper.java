package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseCreateRequest;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseResponse;
import az.inventory.inventorymanagementapi.entity.Warehouse;

public class WarehouseMapper {

    public static Warehouse toEntity(WarehouseCreateRequest dto) {
        return Warehouse.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .build();
    }

    public static WarehouseResponse toResponse(Warehouse entity) {
        return WarehouseResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .build();
    }
}
