package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.supplier.SupplierCreateRequest;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierFilterRequest;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierResponse;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierUpdateRequest;

import java.util.List;

public interface SupplierService {

    SupplierResponse createSupplier(SupplierCreateRequest request);

    SupplierResponse updateSupplier(Long id, SupplierUpdateRequest request);

    void deleteSupplier(Long id);

    SupplierResponse getSupplierById(Long id);

    List<SupplierResponse> getAllSuppliers();

    List<SupplierResponse> filterSuppliers(SupplierFilterRequest filterRequest);
}
