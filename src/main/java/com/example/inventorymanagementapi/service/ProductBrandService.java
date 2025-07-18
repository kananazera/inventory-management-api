package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.productbrand.ProductBrandCreateRequest;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandFilterRequest;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandResponse;
import com.example.inventorymanagementapi.dto.productbrand.ProductBrandUpdateRequest;

import java.util.List;

public interface ProductBrandService {

    ProductBrandResponse createProductBrand(ProductBrandCreateRequest request);

    ProductBrandResponse updateProductBrand(Long id, ProductBrandUpdateRequest request);

    void deleteProductBrand(Long id);

    ProductBrandResponse getProductBrandById(Long id);

    List<ProductBrandResponse> getAllProductCategories();

    List<ProductBrandResponse> filterProductCategories(ProductBrandFilterRequest filterRequest);
}
