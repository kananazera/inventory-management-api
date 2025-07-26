package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.product.ProductFilterRequest;
import com.example.inventorymanagementapi.dto.product.ProductCreateRequest;
import com.example.inventorymanagementapi.dto.product.ProductResponse;
import com.example.inventorymanagementapi.dto.product.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest request, MultipartFile image) throws IOException;

    ProductResponse updateProduct(Long id, ProductUpdateRequest request, MultipartFile image) throws IOException;

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    List<ProductResponse> filterProducts(ProductFilterRequest filterRequest);
}