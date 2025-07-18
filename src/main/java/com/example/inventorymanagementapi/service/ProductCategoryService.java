package com.example.inventorymanagementapi.service;

import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryCreateRequest;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryResponse;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryUpdateRequest;
import com.example.inventorymanagementapi.dto.productcategory.ProductCategoryFilterRequest;

import java.util.List;

public interface ProductCategoryService {

    ProductCategoryResponse createProductCategory(ProductCategoryCreateRequest request);

    ProductCategoryResponse updateProductCategory(Long id, ProductCategoryUpdateRequest request);

    void deleteProductCategory(Long id);

    ProductCategoryResponse getProductCategoryById(Long id);

    List<ProductCategoryResponse> getAllProductCategories();

    List<ProductCategoryResponse> filterProductCategories(ProductCategoryFilterRequest filterRequest);
}
