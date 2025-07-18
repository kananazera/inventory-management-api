package com.example.inventorymanagementapi.controller;

import com.example.inventorymanagementapi.dto.productunit.ProductUnitCreateRequest;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitFilterRequest;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitResponse;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitUpdateRequest;
import com.example.inventorymanagementapi.service.ProductUnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-units")
@RequiredArgsConstructor
public class ProductUnitController {

    private final ProductUnitService productUnitService;

    @PostMapping
    public ResponseEntity<ProductUnitResponse> create(@Valid @RequestBody(required = false) ProductUnitCreateRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        ProductUnitResponse response = productUnitService.createProductUnit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUnitResponse> update(@PathVariable Long id, @RequestBody ProductUnitUpdateRequest request) {
        ProductUnitResponse response = productUnitService.updateProductUnit(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductUnitResponse>> getAll() {
        List<ProductUnitResponse> list = productUnitService.getAllProductCategories();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductUnitResponse> getById(@PathVariable Long id) {
        ProductUnitResponse response = productUnitService.getProductUnitById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productUnitService.deleteProductUnit(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductUnitResponse>> filter(@RequestBody ProductUnitFilterRequest filterRequest) {
        List<ProductUnitResponse> list = productUnitService.filterProductCategories(filterRequest);
        return ResponseEntity.ok(list);
    }
}
