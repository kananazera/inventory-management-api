package az.inventory.inventorymanagementapi.mapper;

import az.inventory.inventorymanagementapi.dto.contract.*;
import az.inventory.inventorymanagementapi.entity.Contract;
import az.inventory.inventorymanagementapi.entity.ContractFile;
import az.inventory.inventorymanagementapi.entity.Customer;
import az.inventory.inventorymanagementapi.entity.Supplier;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContractMapper {

    public static Contract toEntity(ContractCreateRequest request, Supplier supplier, Customer customer) {
        return Contract.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .supplier(supplier)
                .customer(customer)
                .build();
    }

    public static ContractResponse toResponse(Contract contract) {
        return ContractResponse.builder()
                .id(contract.getId())
                .title(contract.getTitle())
                .description(contract.getDescription())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .supplierId(contract.getSupplier() != null ? contract.getSupplier().getId() : null)
                .supplierFullName(contract.getSupplier() != null ? contract.getSupplier().getFullName() : null)
                .customerId(contract.getCustomer() != null ? contract.getCustomer().getId() : null)
                .customerFullName(contract.getCustomer() != null ? contract.getCustomer().getFullName() : null)
                .files(toFileResponseList(contract.getContractFiles()))
                .build();
    }

    private static List<ContractFileResponse> toFileResponseList(List<ContractFile> files) {
        if (files == null) {
            return Collections.emptyList();
        }
        return files.stream()
                .map(file -> ContractFileResponse.builder()
                        .id(file.getId())
                        .fileName(file.getFileName())
                        .fileUrl(file.getFileUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
