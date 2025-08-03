package az.inventory.inventorymanagementapi.controller;

import az.inventory.inventorymanagementapi.dto.supplier.SupplierCreateRequest;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierResponse;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierUpdateRequest;
import az.inventory.inventorymanagementapi.dto.supplier.SupplierFilterRequest;
import az.inventory.inventorymanagementapi.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponse> create(@Valid @RequestBody SupplierCreateRequest request) {
        SupplierResponse response = supplierService.createSupplier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody SupplierUpdateRequest request) {
        SupplierResponse response = supplierService.updateSupplier(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAll() {
        List<SupplierResponse> list = supplierService.getAllSuppliers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getById(@PathVariable Long id) {
        SupplierResponse response = supplierService.getSupplierById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully");
    }

    @PostMapping("/filter")
    public ResponseEntity<List<SupplierResponse>> filter(@RequestBody SupplierFilterRequest filterRequest) {
        List<SupplierResponse> list = supplierService.filterSuppliers(filterRequest);
        return ResponseEntity.ok(list);
    }
}
