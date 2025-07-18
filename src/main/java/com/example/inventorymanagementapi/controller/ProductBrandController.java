package com.example.inventorymanagementapi.controller;

import com.example.inventorymanagementapi.dto.productbrand.ProductBrandCreateRequest;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandFilterRequest;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandResponse;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandUpdateRequest;
import com.example.inventorymanagementapi.service.ProductBrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-brands")
@RequiredArgsConstructor
public class ProductBrandController {

    private final ProductBrandService productBrandService;

    @PostMapping
    public ResponseEntity<ProductBrandResponse> create(@Valid @RequestBody(required = false) ProductBrandCreateRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        ProductBrandResponse response = productBrandService.createProductBrand(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductBrandResponse> update(@PathVariable Long id, @RequestBody ProductBrandUpdateRequest request) {
        ProductBrandResponse response = productBrandService.updateProductBrand(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductBrandResponse>> getAll() {
        List<ProductBrandResponse> list = productBrandService.getAllProductCategories();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBrandResponse> getById(@PathVariable Long id) {
        ProductBrandResponse response = productBrandService.getProductBrandById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productBrandService.deleteProductBrand(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductBrandResponse>> filter(@RequestBody ProductBrandFilterRequest filterRequest) {
        List<ProductBrandResponse> list = productBrandService.filterProductCategories(filterRequest);
        return ResponseEntity.ok(list);
    }
}
