package az.inventory.inventorymanagementapi.service;

import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseCreateRequest;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseFilterRequest;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseResponse;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseUpdateRequest;

import java.util.List;

public interface WarehouseService {

    WarehouseResponse createWarehouse(WarehouseCreateRequest request);

    WarehouseResponse updateWarehouse(Long id, WarehouseUpdateRequest request);

    void deleteWarehouse(Long id);

    WarehouseResponse getWarehouseById(Long id);

    List<WarehouseResponse> getAllWarehouses();

    List<WarehouseResponse> filterWarehouses(WarehouseFilterRequest filterRequest);
}
