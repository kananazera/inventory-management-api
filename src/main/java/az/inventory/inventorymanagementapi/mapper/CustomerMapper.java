package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.customer.CustomerCreateRequest;
import az.inventory.inventorymanagementapi.dto.customer.CustomerResponse;
import az.inventory.inventorymanagementapi.entity.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerCreateRequest dto) {
        return Customer.builder()
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

    public static CustomerResponse toResponse(Customer entity) {
        return CustomerResponse.builder()
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
