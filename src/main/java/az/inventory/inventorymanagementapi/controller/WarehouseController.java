package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseCreateRequest;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseFilterRequest;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseResponse;
import az.inventory.inventorymanagementapi.dto.warehouse.WarehouseUpdateRequest;
import az.inventory.inventorymanagementapi.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseResponse> create(@Valid @RequestBody WarehouseCreateRequest request) {
        WarehouseResponse response = warehouseService.createWarehouse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponse> update(@PathVariable Long id, @RequestBody WarehouseUpdateRequest request) {
        WarehouseResponse response = warehouseService.updateWarehouse(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAll() {
        List<WarehouseResponse> list = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getById(@PathVariable Long id) {
        WarehouseResponse response = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/filter")
    public ResponseEntity<List<WarehouseResponse>> filter(@RequestBody WarehouseFilterRequest filterRequest) {
        List<WarehouseResponse> list = warehouseService.filterWarehouses(filterRequest);
        return ResponseEntity.ok(list);
    }
}
