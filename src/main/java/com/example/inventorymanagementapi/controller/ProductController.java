package com.example.inventorymanagementapi.controller;

import com.example.inventorymanagementapi.dto.ProductFilterRequest;
import com.example.inventorymanagementapi.dto.ProductCreateRequest;
import com.example.inventorymanagementapi.dto.ProductResponse;
import com.example.inventorymanagementapi.dto.ProductUpdateRequest;
import com.example.inventorymanagementapi.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody(required = false) ProductCreateRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        List<ProductResponse> list = productService.getAllProducts();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        ResponseEntity.noContent().build();
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductResponse>> filter(@RequestBody ProductFilterRequest filterRequest) {
        List<ProductResponse> list = productService.filterProducts(filterRequest);
        return ResponseEntity.ok(list);
    }
}