package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.productunit.ProductUnitCreateRequest;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitFilterRequest;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitResponse;
import com.example.inventorymanagementapi.dto.productunit.ProductUnitUpdateRequest;

import java.util.List;

public interface ProductUnitService {

    ProductUnitResponse createProductUnit(ProductUnitCreateRequest request);

    ProductUnitResponse updateProductUnit(Long id, ProductUnitUpdateRequest request);

    void deleteProductUnit(Long id);

    ProductUnitResponse getProductUnitById(Long id);

    List<ProductUnitResponse> getAllProductCategories();

    List<ProductUnitResponse> filterProductCategories(ProductUnitFilterRequest filterRequest);
}
