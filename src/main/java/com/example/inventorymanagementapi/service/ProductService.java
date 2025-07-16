package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.ProductFilterRequest;
import com.example.inventorymanagementapi.dto.ProductCreateRequest;
import com.example.inventorymanagementapi.dto.ProductResponse;
import com.example.inventorymanagementapi.dto.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductCreateRequest request);

    ProductResponse updateProduct(Long id, ProductUpdateRequest request);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> filterProducts(ProductFilterRequest filterRequest);
}