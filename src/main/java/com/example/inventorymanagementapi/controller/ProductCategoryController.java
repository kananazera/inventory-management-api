package com.example.inventorymanagementapi.controller;

import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryCreateRequest;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryFilterRequest;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryResponse;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryUpdateRequest;
import com.example.inventorymanagementapi.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping
    public ResponseEntity<ProductCategoryResponse> create(@Valid @RequestBody(required = false) ProductCategoryCreateRequest request) {
        if (request == null) {
            throw new RuntimeException("Request body must not be empty");
        }
        ProductCategoryResponse response = productCategoryService.createProductCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> update(@PathVariable Long id, @RequestBody ProductCategoryUpdateRequest request) {
        ProductCategoryResponse response = productCategoryService.updateProductCategory(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getAll() {
        List<ProductCategoryResponse> list = productCategoryService.getAllProductCategories();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getById(@PathVariable Long id) {
        ProductCategoryResponse response = productCategoryService.getProductCategoryById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/filter")
    public ResponseEntity<List<ProductCategoryResponse>> filter(@RequestBody ProductCategoryFilterRequest filterRequest) {
        List<ProductCategoryResponse> list = productCategoryService.filterProductCategories(filterRequest);
        return ResponseEntity.ok(list);
    }
}
