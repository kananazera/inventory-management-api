package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.supplier.SupplierCreateRequest;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierResponse;
import az.inventory.inventorymanagementapi.entity.Supplier;

public class SupplierMapper {

    public static Supplier toEntity(SupplierCreateRequest dto) {
        return Supplier.builder()
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .active(dto.getActive() != null ? dto.getActive() : Boolean.TRUE)
                .contactType(dto.getContactType())
                .tin(dto.getTin())
                .build();
    }

    public static SupplierResponse toResponse(Supplier entity) {
        return SupplierResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .gender(entity.getGender())
                .birthDate(entity.getBirthDate())
                .active(entity.getActive())
                .contactType(entity.getContactType())
                .tin(entity.getTin())
                .build();
    }
}
